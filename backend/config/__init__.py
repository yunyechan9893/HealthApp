class Config :
    APP_NAME = 'health_app'
    ADMIN_NAME = '윤예찬'
    SECRET_KEY = '12341234'

class DevelopmentConfig(Config):
    DEBUG = True

    DATABASE_URL='mssql+pymssql://yyc:9893@172.30.1.80:1433/HealthApp'