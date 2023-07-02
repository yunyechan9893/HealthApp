from app_v1 import api
from flask import request, jsonify
import models
from security.token_manager import post_access_token

@api.route('/login', methods= ['POST', 'GET'])
def login():
    if request.method == 'POST':
        params = request.get_json()
        user_id  = params['userId']
        user_pwd = params['userPwd']

        result = models.login(user_id, user_pwd)

        if result :
            return jsonify({
                    "success":"T",
                    "message":200,
                    "token":post_access_token( result )
                }
            )
    
    return jsonify({
            "success":"F", 
            "message":100, 
            "token":""
        })

        