from flask import Flask
from config import secret_key as key, DevelopmentConfig as config


def create_app():
    app = Flask(__name__)
    init_env( app )
    init_blueprint( app )

    return app

def init_env( app ):
    app.config['JWT_SECRET_KEY'] = key.get_app_key()
    app.config['DEBUG_MODE'] = config.DEBUG

def init_blueprint( app ):
    from app_v1 import api
    from app_v1 import auth
    from app_v1 import test

    app.register_blueprint(api)
    app.register_blueprint(auth)
    app.register_blueprint(test)


if __name__=='__main__':
    if config.DEBUG :
        app = create_app()
        app.run(host='0.0.0.0', debug=True)