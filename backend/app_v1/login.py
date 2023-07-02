from app_v1 import api
from flask import request, jsonify
import models
from security import token_manager 
from storage import redis

@api.route('/login', methods= ['POST', 'GET'])
def login():
    if request.method == 'POST':
        params = request.get_json()
        user_id  = params['userId']
        user_pwd = params['userPwd']

        result = models.login(user_id, user_pwd)
        refresh_token = token_manager.post_refresh_token( )
        access_token  = token_manager.post_access_token( result )

        redis.hset(user_id, 'refresh_token', refresh_token)
        redis.hset(user_id, 'access_token', access_token)

        if result :
            return jsonify({
                    "success":"T",
                    "message":200,
                    "refresh_token":refresh_token,
                    "access_token":access_token
                }
            )
    
    return jsonify({
            "success":"F", 
            "message":100, 
            "token":""
        })

        