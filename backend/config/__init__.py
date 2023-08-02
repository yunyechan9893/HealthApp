class Config :
    APP_NAME = 'health_app'
    ADMIN_NAME = '윤예찬'

    FOODSAFETY_API_KEY = "b7261cee5f2f462086d9"

class DevelopmentConfig(Config):
    DEBUG = True
    __DATABASE_IP='127.0.0.1'
    DATABASE_URL=f'mssql+pymssql://yyc:9893@{__DATABASE_IP}:1433/HealthApp'

    #jwt
    JWT_algorithm ='HS256'
    ACCESS_TOKEN_TIME  = 60*15       # 유효기간 15분
    REFRESH_TOKEN_TIME = 60*60*24*15 # 유효기간 15일

    # Redis
    REDIS_IP='127.0.0.1'
    REDIS_PORT=6379
    REDIS_DB=1

    if DEBUG :
        import socket

        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect(("pwnbit.kr", 443))
        __DATABASE_IP=sock.getsockname()[0]
        DATABASE_URL=f'mssql+pymssql://yyc:9893@{__DATABASE_IP}:1433/HealthApp'
        print("내부 IP: ", DATABASE_URL)