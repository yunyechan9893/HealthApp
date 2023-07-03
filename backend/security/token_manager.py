import jwt
from config import DevelopmentConfig as conf
from flask import current_app, Response, jsonify
from datetime import datetime, timedelta
import uuid

__algorithm = conf.JWT_algorithm


def create_access_token( id, position, nickname ):
    global __algorithm
    sceret_ket = current_app.config['JWT_SECRET_KEY']
    pay_load = {
        "user_info":{
            "id"       : id,
            "position" : position,
            "nickname" : nickname
        },
        # 유효기간 설정
        "exp": datetime.utcnow() + timedelta( seconds=conf.ACCESS_TOKEN_TIME )
    }
    
    return jwt.encode( pay_load, sceret_ket, __algorithm )

def create_refresh_token( ):
    global __algorithm
    sceret_ket = current_app.config['JWT_SECRET_KEY']
    pay_load = {
        "key":str(uuid.uuid4()),
        # 유효기간 설정
        "exp": datetime.utcnow() + timedelta( seconds=conf.REFRESH_TOKEN_TIME )
    }

    return jwt.encode( pay_load, sceret_ket, __algorithm )

def check_refresh_token( refresh_token ):
    global __algorithm
    # access 토큰을 payload 디코딩하면 딕셔너리를 반환함
    sceret_key = current_app.config['JWT_SECRET_KEY']
    try:
        print("아직 안만료")
        return {
            'code'    : 200,
            'message' : jwt.decode(refresh_token, sceret_key, __algorithm )
        }
    
    except jwt.ExpiredSignatureError:
        return {
            'code'    : 401,
            'message' : 'ExpiredSignatureError'
        }
    
    except jwt.InvalidTokenError:
        return {
            'code'    : 400,
            'message' : 'InvalidTokenError'
        }
 

def check_access_token( access_token ):
    global __algorithm
    # access 토큰을 payload 디코딩하면 딕셔너리를 반환함
    sceret_key = current_app.config['JWT_SECRET_KEY']
    try:
        print("아직 안만료")
        return {
            'code'    : 200,
            'message' : jwt.decode(access_token, sceret_key, __algorithm )
        }
    
    except jwt.ExpiredSignatureError:
        return {
            'code'    : 401,
            'message' : 'ExpiredSignatureError'
        }
    
    except jwt.InvalidTokenError:
        return {
            'code'    : 400,
            'message' : 'InvalidTokenError'
        }
 


