from flask import Flask
from config import DevelopmentConfig as config

def create_app():
    app = Flask(__name__)
    init_blueprint( app )

    return app


def init_blueprint( app ):
    from app_v1 import api
    app.register_blueprint(api)


if __name__=='__main__':
    if config.DEBUG :
        app = create_app()
        app.run(host='127.0.0.1', debug=True)
