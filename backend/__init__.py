from flask import Flask
from sqlalchemy import create_engine
from config import DevelopmentConfig as config


app = Flask(__name__)

db = create_engine(config.DATABASE_URL)


