class Config :
    APP_NAME = 'health_app'
    ADMIN_NAME = '윤예찬'

class DevelopmentConfig(Config):
    DEBUG = True

    DATABASE_URL='mssql+pymssql://yyc:9893@172.30.107.5:1433/HealthApp'