from flask import Blueprint, request, jsonify
from security.token_manager import check_access_token as check_token

api = Blueprint('api',  # 별칭, 해당 블루프린트 밑에서 정의된 
                    __name__,   # 고정
                    url_prefix='/api',             # 모든 URL 앞에 /main이 추가된다
    )

auth = Blueprint('auth',  # 별칭, 해당 블루프린트 밑에서 정의된 
                    __name__,   # 고정
                    url_prefix='/auth',             # 모든 URL 앞에 /main이 추가된다
    )

test = Blueprint('test',  # 별칭, 해당 블루프린트 밑에서 정의된 
                    __name__,   # 고정
                    url_prefix='/test',             # 모든 URL 앞에 /main이 추가된다
    )

''' 동작되는것을 확인했으니 테스트 후 수정하자
@api.before_request
def check_access_token():
    # 액세스 토큰 검사
    user_info = dict()
    print(request.method)
    if request.endpoint != 'api.login' and request.endpoint != 'api.signup'and request.endpoint != 'api.test' and request.endpoint != 'api.target_kcalsand' and request.method != 'GET':
        json_data = request.json
        access_token = json_data['access_token']
        
        token_info = check_token( access_token )
        token_code = token_info['code']
        

        if token_code==200:
            user_info     = token_info['message']
        elif token_code==401:
            # 리프래시 토큰 요구
            return jsonify({
                    "success":"F",
                    "message":401,
                }
            )
        else :
            # 침입 판단
            return jsonify({
                    "success":"F",
                    "message":400,
                }
            )




    # 만료됐다면 리프레시 토큰을 확인 후 액세스 토큰 재발급
     
    # 유효하지 않은 토큰일 시 401리턴
'''

from . import login
from . import signup
from . import food_api
from . import test

