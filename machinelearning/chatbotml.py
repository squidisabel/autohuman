import gensim
from gensim.models.doc2vec import Doc2Vec, TaggedDocument
from gensim.utils import simple_preprocess
import multiprocessing
import os
import json
import pandas

#dylans
def load_file (input_path, train_data):
    file = open(input_path)
    arr = json.load(file)
    for i in arr:
        data = (i['input'], i['response'])
        train_data.append(data)

class MyTexts:
    def __init__(self, train_data):
        self.train_data = train_data
    def __iter__(self):
        for i in range(len(self.train_data)):
            yield TaggedDocument(words=simple_preprocess(self.train_data[i][0]), tags=[i])

def create_model(output_path):
    train_data = []
    load_file("machinelearning/test.json", train_data)
    assert gensim.models.doc2vec.FAST_VERSION > - 1

    texts = MyTexts(train_data)
    cores = multiprocessing.cpu_count()
    doc2vec_model = Doc2Vec(vector_size = 200, workers = cores)
    doc2vec_model.build_vocab(texts)

    doc2vec_model.train(texts, total_examples=doc2vec_model.corpus_count, epochs=15)

    if not os.path.exists('machinelearning/models'):
        os.makedirs('machinelearning/models')
    doc2vec_model.save('machinelearning/models/' + output_path)

def chatbot(input_model):
    train_data = []
    load_file("machinelearning/test.json", train_data)
    
    doc2vec_model = Doc2Vec.load('machinelearning/models/' + input_model)

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

def getResponse(input, input_model):
    train_data = []
    load_file("machinelearning/test.json", train_data)
        
    doc2vec_model = Doc2Vec.load('machinelearning/models/' + input_model)

    tokens = input.split()
    new_vector = doc2vec_model.infer_vector(tokens)
    index = doc2vec_model.docvecs.most_similar([new_vector], topn = 10)
    return train_data[index[0][0]][1]

create_model("doc2vec.model") 
print(getResponse("hello my name is kerry", "doc2vec.model"))
#chatbot("doc2vec.model")