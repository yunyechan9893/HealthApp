import redis
import config

def __connect():
    return redis.StrictRedis(host='127.0.0.1', port=6379, db=1)

def hset( key, category, value, time= 60*60*24*30 ):
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