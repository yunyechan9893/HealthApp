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
        "success": "T",
        "message": 200,
        "data": diet_list
    }

    return jsonify(response_data)

        