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
    print(user_pwd)

    response_data = {
            "success":"F", 
            "message":100, 
            "token":""
    }

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
        
        if diets:
            diet_numbers = [diet.get_no() for diet in diets]
            diet_numbers_dict = {diet_numbers[i]:i for i in range(len(diet_numbers))}
            ate_foods = models.get_ate_food(diet_numbers)
            target_kcals = models.get_target_kcal(user_id)

            target_kcal_list = [json.loads(target_kcal.get_all_data()) for target_kcal in target_kcals]

            diet_info = [ {
                "no": diet_numbers_dict.get(diet.get_no()) + 1,
                "type_of_meal": diet.get_type_of_meal(),
                "meal_time": diet.get_meal_time(),
                "comment": diet.get_comment(),
                "date": diet.get_date(),
                "url": diet.get_url()} for diet in diets] 

            food_list = [
                {
                    "diet_no": diet_numbers_dict.get(food.get_diet_no()) + 1,
                    "name": food.get_food_name(),
                    "amount": food.get_amount(),
                    "kcal": food.get_kcal(),
                    "carbohydrate": food.get_carbohydrate(),
                    "protein": food.get_protein(),
                    "fat": food.get_fat(),
                    "sodium": food.get_sodium()
                }
                for food in ate_foods
            ]

            response_data = {
                    "success":"T",
                    "message":200,
                    "refresh_token":refresh_token,
                    "access_token":access_token,
                    "diet_info":diet_info,
                    "food_list":food_list,
                    "target_kcal":target_kcal_list,
            }

    return jsonify(response_data)

@api.route('/login/token', methods= ['POST'])
def login_token():
    # user_info = request.user_info

    return jsonify({
                "success":"T",
                "message":200
    })


## test code ## 

@test.route('/target_kcals', methods= ['POST'])
def get_diet_target_kcals():
    if current_app.config['DEBUG_MODE'] :
        test_id = 'test0000'
        target_kcals = models.get_target_kcal(test_id)

        target_kcal_list = [target_kcal.get_all_data() for target_kcal in target_kcals]

        respone_data = {
                    "success":"T",
                    "message":200,
                    "target_kcal":target_kcal_list,
            }
        
        return respone_data

    
    raise Exception