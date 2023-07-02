from flask import Blueprint, request
from security.token_manager import check_access_token as check_token

api = Blueprint('api',  # 별칭, 해당 블루프린트 밑에서 정의된 
                    __name__,   # 고정
                    url_prefix='/api',             # 모든 URL 앞에 /main이 추가된다
    )

@api.before_request
def check_access_token():
    # 액세스 토큰 검사
    if request.endpoint != 'api.login':
        access_token = request.headers.get('Authorization')
        check_token( access_token )


    # 만료됐다면 리프레시 토큰을 확인 후 액세스 토큰 재발급
     
    # 유효하지 않은 토큰일 시 401리턴


from . import login
from . import signup
from . import test

