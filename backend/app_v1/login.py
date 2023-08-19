from app_v1 import api, test
from flask import request, jsonify, json, current_app
import models
from security import token_manager 
from storage import redis

@api.route('/login', methods= ['POST'])
def login():
    params = request.get_json()
    user_id  = params['user_id']
    user_pwd = params['user_pwd']

    resp = {
            "success":"F", 
            "message":100, 
    }

    result = models.login_default(user_id, user_pwd)
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

        resp = {
                    "success":"T",
                    "message":200,
                    "refresh_token":refresh_token,
                    "access_token":access_token,
        }

    return jsonify(resp)


@api.route('/login/diet/<id>', methods=['GET'])
def get_login_diet(id):
    diets = models.get_diet(id)
    print(id, diets)

    resp = {
        "success":"F", 
        "message":100,
        "diet_info":[],
        "food_list":[],
    }
        
    if diets:
        diet_numbers = [diet.get_no() for diet in diets]
        print(diet_numbers)
        ate_foods = models.get_ate_food(diet_numbers)
  
        diet_info = [ {
            "no": diet.get_no(),
            "type_of_meal": diet.get_type_of_meal(),
            "meal_time": diet.get_meal_time(),
            "comment": diet.get_comment(),
            "date": diet.get_date(),
            "url": diet.get_url()} for diet in diets] 

        food_list = [
            {
                "ate_food_no": food.get_ate_food_no(),
                "diet_no": food.get_diet_no(),
                "name": food.get_food_name(),
                "amount": food.get_amount(),
                "kcal": food.get_kcal(),
                "carbohydrate": food.get_carbohydrate(),
                "protein": food.get_protein(),
                "fat": food.get_fat(),
                "sugars": food.get_sugars(),
                "sodium": food.get_sodium(),
                "cholesterol": food.get_cholesterol(),
                "saturated_fat": food.get_saturated_fat(),
                "trans_fat": food.get_trans_fat(),
            }
            for food in ate_foods
        ]
        resp = {
                "success":"T",
                "message":200,
                "diet_info":diet_info,
                "food_list":food_list,
        }
    
    print(resp["diet_info"])
    print(resp["food_list"])
    
    return jsonify(resp)

@api.route('/login/target-kcal/<id>', methods=['GET'])
def get_login_target_kcal(id):
    resp = {
        "success":"F", 
        "message":100,
        "target_kcal":[]
    }

    target_kcals = models.get_target_kcal(id)
    if target_kcals:
        target_kcal_list = [json.loads(target_kcal.get_all_data()) for target_kcal in target_kcals]

        resp = {
                "success":"T",
                "message":200,
                "target_kcal":target_kcal_list,
        }
    
    print(resp["target_kcal"])

    return jsonify(resp)



@api.route('/login/token', methods= ['POST'])
def login_token():
    # user_info = request.user_info

    return jsonify({
                "success":"T",
                "message":200
    })
