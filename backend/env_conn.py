print(__name__)

with open('enviroment.env', 'r') as f:
    for val in f.readline():
        print(val)
