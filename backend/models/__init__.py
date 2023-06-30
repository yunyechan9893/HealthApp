from .table import Position, User
from .connection import SessionContext
from .manager import create, get

from sqlalchemy import create_engine, and_
from sqlalchemy.orm import sessionmaker
from config import DevelopmentConfig as config


engine = create_engine(config.DATABASE_URL, pool_size=20, pool_recycle=30, max_overflow=30)
Session = sessionmaker(bind=engine)
session = Session()

def login(id, pwd):
    with SessionContext(session) as se:
        print(get( session=se ,table=User, filter= and_(User.id==id, User.passward==pwd)))

def register():
    with SessionContext(session) as se:
        create( 
            session=se ,
            table=User( 
                id='test', 
                passward='0000',
                name='test',
                phone='01023299893',
                nickname='test',
                position=1
            ) 
        )





