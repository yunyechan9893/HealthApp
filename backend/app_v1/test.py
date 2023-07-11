from app_v1 import api
from flask import request
import models

@api.route('/test', methods= ['POST', 'GET'])
def test():
    diet = models.get_diet('test0000')
    return diet

        