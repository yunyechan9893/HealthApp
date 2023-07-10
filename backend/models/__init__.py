from flask import jsonify
from .table import Position, User, Diet, AteFood
from .connection import SessionContext
from .manager import create, get_one, get

from sqlalchemy import create_engine, and_, or_
from sqlalchemy.orm import sessionmaker
from config import DevelopmentConfig as config


engine = create_engine(config.DATABASE_URL, pool_size=20, pool_recycle=30, max_overflow=30)
Session = sessionmaker(bind=engine)
session = Session()

def login_defualt(id, pwd):
    try:
        with SessionContext(session) as se:
            return get_one( session=se ,table=User, filter= and_(User.id==id, User.passward==pwd) )
    except Exception as e:
        raise e
    
def login_token(id, pwd):
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
        return False

def get_id(id):
    try:
        with SessionContext(session) as se:
            return get_one( session=se ,table=User, filter= and_(User.id==id) )
    except Exception as e:
        return False
    
def get_nickname(nickname):
    try:
        with SessionContext(session) as se:
            return get_one( session=se ,table=User, filter= and_(User.nickname==nickname) )
    except Exception as e:
        return False
    
def get_diet(id):
    try:
        with SessionContext(session) as se:
            table  = Diet
            filter = and_(Diet.member_id == id)
            
            return get(session=se, table=table, filter=filter)
    except Exception as e:
        return False
    
def get_ate_food(*noes):
    try:
        with SessionContext(session) as se:
            table  = AteFood
            filter = or_(AteFood.diet_no == no for no in noes)

            return get(session=se, table=table, filter=filter)
    except Exception as e:
        return False



