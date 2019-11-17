FROM python:3.7

ENV LISTEN_PORT=5000
EXPOSE 5000

RUN pip install --upgrade pip


WORKDIR /code

COPY . .

ENV FLASK_APP chatbotml.py
ENV FLASK_RUN_HOST 0.0.0.0

WORKDIR /code/machinelearning

RUN pip install gensim
RUN pip install pandas
RUN pip install gunicorn
RUN pip install numpy
RUN pip install flask

# COPY chatbotml.py .

CMD ["gunicorn", "--bind", "0.0.0.0:5000", "wsgi:app"]

# RUN mkdir /code
# WORKDIR /code
# ADD . /code/
