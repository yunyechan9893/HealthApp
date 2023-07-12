from app_v1 import api
from flask import request, jsonify, current_app
import logging
import models

def tool(msg, form=''):
    ###
    # 로깅 레벨 설정
    current_app.logger.setLevel(logging.DEBUG)

    # 로깅 핸들러 추가
    stream_handler = logging.StreamHandler()

    current_app.logger.info("======================")
    current_app.logger.info(form)
    current_app.logger.info(msg)

@api.route('/test', methods= ['POST', 'GET'])
def test():
    diets = models.get_diet('test0000')
    diet_numbers = [diet.get_no() for diet in diets]
    diet_numbers_dict = {diet_numbers[i]:i for i in range(len(diet_numbers))}
    ate_food = models.get_ate_food(diet_numbers)

    if diets:
        diet_info = [{
            "no": diet_numbers_dict.get(diet.get_no()) ,
            "type_of_meal": diet.get_type_of_meal(),
            "meal_time": diet.get_meal_time(),
            "comment": diet.get_comment(),
            "date": diet.get_date(),
            "url": diet.get_url()} for diet in diets] 

        food_list = [
            {
                "diet_no": diet_numbers_dict.get(food.get_diet_no()),
                "name": food.get_food_name(),
                "amount": food.get_amount(),
                "kcal": food.get_kcal(),
                "carbohydrate": food.get_carbohydrate(),
                "protein": food.get_protein(),
                "fat": food.get_fat(),
                "sodium": food.get_sodium()
            }
            for food in ate_food
        ]

        response_data = {
                "success":"T",
                "message":200,
                "diet_info":diet_info,
                "food_list":food_list
        }

    return jsonify(response_data)


        