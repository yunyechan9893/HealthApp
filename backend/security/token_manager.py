import jwt
from flask import current_app
from datetime import datetime, timedelta

__algorithm = 'HS256'


def post_access_token( User ):
    global __algorithm
    sceret_ket = current_app.config['JWT_SECRET_KEY']
    pay_load = {
                "user_info":[{
                    "id"       : User.get_id(),
                    "auth"     : User.get_position()
                }],
                # 유효기간 설정
                "exp": datetime.utcnow() + timedelta( seconds= 10 )
            }
    
    return jwt.encode( pay_load, sceret_ket, __algorithm )

def check_access_token( access_token ):
    global __algorithm
    # access 토큰을 payload 디코딩하면 딕셔너리를 반환함
    sceret_ket = current_app.config['JWT_SECRET_KEY']
    try:
        payload = jwt.decode(access_token, sceret_ket, __algorithm )
        print("아직 안만료")
    except Exception as e:
        # 리프레시 토큰을 확인하여 토큰 재발급
        print("기간만료")
        print(e)
        return     


