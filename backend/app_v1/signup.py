from app_v1 import api
from flask import request, jsonify
import models

@api.route('/signup', methods= ['POST'])
def signup():
    params = request.get_json()
    user_id = params.get('userId')
    user_pwd = params.get('userPwd')
    user_name = params.get('userName')
    user_phone = params.get('userPhone')
    user_nickname = params.get('userNickname')
    user_position = params.get('userPosition')

    msg = models.register( 
        user_id, 
        user_pwd, 
        user_name,
        user_phone,
        user_nickname, 
        user_position 
    )
    
    success = True if msg else False

    return jsonify({"success": success, "message": 200})

@api.route('/duplicate-check/id/<id>', methods=['GET'])
def duplicate_check_id(id):
    
    msg = models.get_id(id)
    success = False if msg else True
    return jsonify({"success": success, "message": 200})

@api.route('/duplicate-check/nickname/<nickname>', methods=['GET'])
def duplicate_check_nickname(user_nickname):
    msg = models.get_nickname(user_nickname)
    success = False if msg else True
    return jsonify({"success": success, "message": 200})

