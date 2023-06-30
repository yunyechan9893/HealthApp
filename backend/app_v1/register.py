from app_v1 import api
from flask import request
import models

@api.route('/register', methods= ['POST', 'GET'])
def register():
    if request.method == 'POST':

        models.register( )      
        
        return '성공'
    return '실패'

        