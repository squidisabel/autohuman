import gensim
from gensim.models.doc2vec import Doc2Vec, TaggedDocument
from gensim.utils import simple_preprocess
import multiprocessing
import os
import json
import pandas

file = open('test.json')
arr = json.load(file)
train_data = []
for i in arr:
    data = (i['input'], i['response'])
    #print(data['input'])
    train_data.append(data)

#print(train_data)

class MyTexts(object):
    def __iter__(self):
        for i in range(len(train_data)):
            yield TaggedDocument(words=simple_preprocess(train_data[i][0]), tags=[i])

assert gensim.models.doc2vec.FAST_VERSION > - 1

cores = multiprocessing.cpu_count()

texts = MyTexts()
doc2vec_model = Doc2Vec(vector_size = 200, workers = cores)
doc2vec_model.build_vocab(texts)

doc2vec_model.train(texts, total_examples=doc2vec_model.corpus_count, epochs=15)

if not os.path.exists('models'):
    os.makedirs('models')
doc2vec_model.save('models/doc2vec.model')

def chatbot():
    recent = []
    quit = False
    while quit == False:
        text = str(input('Me: '))
        if text == 'quit()' or text == ':q':
            quit = True
        else:
            tokens = text.split()
            if len(recent) >= 1:
                recent.pop(0)
            t = tokens
            for i in recent:
                t = t + i
            new_vector = doc2vec_model.infer_vector(t)
            index = doc2vec_model.docvecs.most_similar([new_vector], topn = 3)

            recent.append(tokens)
            print('Chatbot: ' + train_data[index[0][0]][1])

chatbot()