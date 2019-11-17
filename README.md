# AutoHuman

Our aim is to remove the human element from human to human conversation. Have you ever wanted to talk to someone, without any of the associated risks. Ask out your crush without running the risk of them actually rejecting you with 

If you have any problems using our service, it's not our bot being dumb, it's you.

## Dev Process

It's unclear whether our inspiration was Tom Scott, Silicon Valley, or Black Mirror. In any case not really knowing each-other our team were all wondering how we could talk to anyone else. The WiFi was too slow to talk to our friends. Why not simulate them?

It quickly became apparent that you can just download your data from facebook. The issue with using this data is that there was a lot of useless data stored that would be too much effort to handle like emojis, files, photos, audio, and stickers.
The other problem is that it may include multiple people which would break our planned modelling of conversation.

Eventually we settled on concatenating subsequent messages from the same conversational participant, keeping the would be human's messages as inputs and the would be robot's as outputs. This was used as our training data

... AI
We train the data using supervised learning algorithms, in particular using shallow learning with word2vec models. Word2vec are two layer neural networks designed to understand linguistic contexts of languages. We process the data concurrently to optimise the time it takes to train our model, and train our machine learning model by updating the weightings of the neural network with word2vec models.  When a response from our model is needed, we simply retrieve the most relevant response from our model.

... App


## Use
To create the interface of your texting profile, we'll ask you to download your messaging history. We then process and clean up this data in Kotlin, and pass this as a JSON file to Python. We then use machine learning algorithms on this data to 'intelligently' build a model that represents you!

Our front-end was written in Android Studio featuring sleek chatbot design. todo:
Once we're set up, you can now talk to our bot! This query is sent via HTTP get methods, to our Kotlin interface that retrieves the most relevant response from our model in Python through Flask.

We initially had problems with attempting to an external Python script in Kotlin, however after attempting for way too long to use Kotlin/Java process implementations we just w/e and used flask todo:
todo: azure ??
todo: say something about ai
