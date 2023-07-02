from flask import Flask
from config import secret_key, DevelopmentConfig as config
from config.secret_key import get_app_key

def create_app():
    app = Flask(__name__)
    init_env( app )
    init_blueprint( app )

    return app

def init_env( app ):
    app.config['JWT_SECRET_KEY'] = get_app_key()

def init_blueprint( app ):
    from app_v1 import api
    app.register_blueprint(api)


if __name__=='__main__':
    if config.DEBUG :
        app = create_app()
        app.run(host='0.0.0.0', debug=True)
