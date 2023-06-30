from app_v1 import api
from flask import request
import models

@api.route('/login', methods= ['POST', 'GET'])
def login():
    if request.method == 'POST':
        user_id = request.form.get('userId')
        user_pwd = request.form.get('userPwd')

        models.login(user_id, user_pwd)      
        
        return '성공'
    return '실패'

        