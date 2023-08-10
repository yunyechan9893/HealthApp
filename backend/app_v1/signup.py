from app_v1 import api
from flask import request, jsonify
import models

@api.route('/signup', methods= ['POST'])
def signup():
    params = request.get_json()
    user_id = params.get('user_id')
    user_pwd = params.get('user_pwd')
    user_name = params.get('user_name')
    user_phone = params.get('user_phone')
    user_nickname = params.get('user_nickname')
    user_code = params.get('user_code')

    print(user_code)

    user_position = 1 if not user_code else 4
    
    
    print(user_id, user_pwd, user_name, user_phone, user_nickname, user_position)

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
def duplicate_check_nickname(nickname):
    msg = models.get_nickname(nickname)
    success = False if msg else True
    return jsonify({"success": success, "message": 200})

