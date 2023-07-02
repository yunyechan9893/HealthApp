from app_v1 import api
from flask import request, jsonify, current_app
import models
import jwt

@api.route('/login', methods= ['POST', 'GET'])
def login():
    if request.method == 'POST':
        params = request.get_json()
        user_id  = params['userId']
        user_pwd = params['userPwd']

        result = models.login(user_id, user_pwd)
        if result :
            algorithm = 'HS256'
            sceret_ket = current_app.config['JWT_SECRET_KEY']
            enc_data = {
                "id"       : result.get_id(),
                "name"     : result.get_name(),
                "nickname" : result.get_nickname(),
                "position" : result.get_position()
            }

            jwt_token = jwt.encode( enc_data, sceret_ket, algorithm )

            return jsonify({
                    "success":"T",
                    "message":200,
                    "token":jwt_token
                }
            )
    
    return jsonify({
            "success":"F", 
            "message":100, 
            "token":""
        })

        