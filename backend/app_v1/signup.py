from app_v1 import api
from flask import request
import models

@api.route('/signup', methods= ['POST', 'GET'])
def signup():
    if request.method == 'POST':
        params = request.get_json()
        user_id        = params['userId']
        user_pwd       = params['userPwd'] 
        user_name      = params['userName']
        user_phone     = params['userPhone']
        user_nickname  = params['userNickname']
        user_position  = params['userPosition']

        msg = models.register( 
            user_id, 
            user_pwd, 
            user_name,
            user_phone,
            user_nickname, 
            user_position 
        )
        
        return 'True' if msg==True else 'False'
    return 'False'

        