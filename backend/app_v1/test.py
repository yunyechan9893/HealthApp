from app_v1 import api
from flask import request

@api.route('/test', methods= ['POST', 'GET'])
def test():
    if request.method == 'POST':
        return '성공'

        