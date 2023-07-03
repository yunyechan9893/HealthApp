import redis
from config import DevelopmentConfig as conf
import json

def __connect():
    return redis.StrictRedis(
        host=conf.REDIS_IP, 
        port=conf.REDIS_PORT, 
        db=conf.REDIS_DB
    )
def set( key, value, time=conf.REFRESH_TOKEN_TIME ):
    with __connect() as conn :
        conn.set(key, value)
        conn.expire(key, time)

def get( key ):
    with __connect() as conn :
        return {
            'value' : conn.get( key ).decode('utf-8'),
            'time'  : conn.ttl( key )
        }
    
def get_json( key ):
    with __connect() as conn :
        data = conn.get( key ).decode('utf-8')
        dict_data = dict( json.loads(data) )
        return {
            'value' : dict_data,
            'time'  : conn.ttl( key )
        }

def hset( key, category, value, time=conf.REFRESH_TOKEN_TIME ):
    with __connect() as conn :
        conn.hset(key, category, value)
        conn.expire(key, time)

def hget( key, category ):
    with __connect() as conn :
        return {
            'value' : conn.hget( key, category ),
            'time'  : conn.ttl( key )
        }
    
def delete( key, category=None ) :
    with __connect() as conn :
        if category :
            conn.delete(key, category)
            return
        
        conn.delete(key)