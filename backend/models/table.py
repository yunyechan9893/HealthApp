from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Position(Base):
    __tablename__='position'
    no = Column(Integer, primary_key=True)
    name = Column(String(20))

class User(Base):
    __tablename__='Members'
    id       = Column(String(20), primary_key=True)
    passward      = Column(String(64))
    name     = Column(String(12))
    phone    = Column(String(11))
    nickname = Column(String(15))
    position = Column(Integer, ForeignKey('position.no'))
    