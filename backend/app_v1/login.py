from app_v1 import api
from flask import request, jsonify, json
import models
from security import token_manager 
from storage import redis

@api.route('/login', methods= ['POST'])
def login():
    params = request.get_json()
    user_id  = params['userId']
    user_pwd = params['userPwd']

    result = models.login_defualt(user_id, user_pwd)
    if result :
        user_id = result.get_id()
        user_position = result.get_position()
        user_nickname = result.get_nickname()

        refresh_token = token_manager.create_refresh_token()
        access_token  = token_manager.create_access_token( user_id, user_position, user_position )

        refresh_token_info = {
            "id" : user_id,
            "position"    : user_position,
            "access_token"  : access_token
        }
        
        redis.set( refresh_token, json.dumps(refresh_token_info, ensure_ascii=False).encode('utf-8') )

    
        return jsonify({
                "success":"T",
                "message":200,
                "refresh_token":refresh_token,
                "access_token":access_token,
            }
        )
    
    return jsonify({
            "success":"F", 
            "message":100, 
            "token":""
        })

@api.route('/login/token', methods= ['POST'])
def login_token():
    # user_info = request.user_info

    return jsonify({
                "success":"T",
                "message":200
    })