class Config :
    APP_NAME = 'health_app'
    ADMIN_NAME = '윤예찬'

class DevelopmentConfig(Config):
    DEBUG = True

    DATABASE_URL='mssql+pymssql://yyc:9893@172.30.1.69:1433/HealthApp'

    #jwt
    JWT_algorithm ='HS256'
    ACCESS_TOKEN_TIME  = 60*15       # 유효기간 15분
    REFRESH_TOKEN_TIME = 60*60*24*15 # 유효기간 15일

    # Redis
    REDIS_IP='127.0.0.1'
    REDIS_PORT=6379
    REDIS_DB=1