from flask import jsonify
from .table import Position, User
from .connection import SessionContext
from .manager import create, get_one

from sqlalchemy import create_engine, and_
from sqlalchemy.orm import sessionmaker
from config import DevelopmentConfig as config


engine = create_engine(config.DATABASE_URL, pool_size=20, pool_recycle=30, max_overflow=30)
Session = sessionmaker(bind=engine)
session = Session()

def login(id, pwd):
    try:
        with SessionContext(session) as se:
            return get_one( session=se ,table=User, filter= and_(User.id==id, User.passward==pwd) )
    except Exception as e:
        raise e
    

def register( id, passward, name, phone, nickname, position ):
    try:
        with SessionContext(session) as se:
            create( 
                session=se ,
                table=User( 
                    id=id, 
                    passward=passward,
                    name=name,
                    phone=phone,
                    nickname=nickname,
                    position=position
                ) 
            )
        return True
    except Exception as e:
        raise e 





