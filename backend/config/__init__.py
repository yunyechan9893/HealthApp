class Config :
    APP_NAME = 'health_app'
    ADMIN_NAME = '윤예찬'

class DevelopmentConfig(Config):
    DEBUG = True

    DATABASE_URL='mssql+pymssql://yyc:9893@192.168.0.15:1433/HealthApp'

    # Redis
    REDIS_IP='127.0.0.1'
    REDIS_PORT=6379
    REDIS_DB=1