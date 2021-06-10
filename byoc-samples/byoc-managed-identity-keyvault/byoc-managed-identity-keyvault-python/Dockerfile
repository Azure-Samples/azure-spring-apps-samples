FROM python:3.8

WORKDIR /code

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

CMD [ "gunicorn", "--bind", "0.0.0.0:1025", "server:app" ]