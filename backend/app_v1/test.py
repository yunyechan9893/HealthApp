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
    diet = models.get_diet('test0000')
    tool(diet)
    return jsonify({
                "success":"T",
                "message":200,
                "data":diet,
            }
        )

        