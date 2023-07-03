from app_v1 import auth
from flask import request, jsonify, json
from security import token_manager
from storage import redis

@auth.route('refresh_token')
def refresh_token():
    json_data = request.json
    refresh_token = json_data[ 'refresh_token' ]
    access_token  = json_data[ 'access_token'  ]

    checking_token = token_manager.check_refresh_token(refresh_token)
    token_info = redis.get_json(refresh_token)['value']
    
    if checking_token['code']==200 and token_info['access_token']==access_token:
        user_id       = token_info['id']
        user_position = token_info['position']

        refresh_token_info = {
                "id"            : user_id,
                "position"      : user_position,
                "access_token"  : access_token
            }
            
        redis.set( refresh_token, json.dumps(refresh_token_info, ensure_ascii=False).encode('utf-8') )
        
        return jsonify({
            "success":"T",
            "message":200,
            "access_token":token_manager.create_access_token(user_id, user_position)
        })
    