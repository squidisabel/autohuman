# ML Chatbot
Not bothered to respond to your friends? Wondering what it would be like to have a conversation with yourself? Want to simulate a conversation with a friend?

Then this is the app for you! Introducing the ML Chatbot where it's responses are trained on your own messages.

todo: paste everything from devpost
We train the data using supervised learning algorithms, in particular using shallow learning with word2vec models. Word2vec are two layer neural networks designed to understand linguistic contexts of languages.

We process the data concurrently to optimise the time it takes to train our model, and train our machine learning model by updating the weightings of the neural network with word2vec models. 

When a response from our model is needed, we simply retrieve the most relevant response from our model.

