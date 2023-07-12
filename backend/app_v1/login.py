from app_v1 import api
from flask import request, jsonify, json
import models
from security import token_manager 
from storage import redis

@api.route('/login', methods= ['POST'])
def login():
    params = request.get_json()
    user_id  = params['user_id']
    user_pwd = params['user_pwd']

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

        diets = models.get_diet(user_id)
        diet_numbers = [diet.get_no() for diet in diets]
        ate_food = models.get_ate_food(diet_numbers)

        diet_list = []
        for idx, diet in enumerate(diets):
            food_list = [
                {
                    "name": food.get_food_name(),
                    "amount": food.get_amount(),
                    "kcal": food.get_kcal(),
                    "carbohydrate": food.get_carbohydrate(),
                    "protein": food.get_protein(),
                    "fat": food.get_fat(),
                    "sodium": food.get_sodium()
                }
                for food in ate_food
                if diet.get_no() == food.get_diet_no()
            ]

            diet_info = {
                "no": idx + 1,
                "type_of_meal": diet.get_type_of_meal(),
                "meal_time": diet.get_meal_time(),
                "comment": diet.get_comment(),
                "date": diet.get_date(),
                "url": diet.get_url(),
                "food": food_list
            }
            
            diet_list.append(diet_info)

        response_data = {
                "success":"T",
                "message":200,
                "refresh_token":refresh_token,
                "access_token":access_token,
                "diet_data":diet_list
        }


        return jsonify(response_data)
    
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