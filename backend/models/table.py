from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
import json
Base = declarative_base()

class Position(Base):
    __tablename__='position'
    no = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(20))

class User(Base):
    __tablename__='members'
    id       = Column(String(20), primary_key=True)
    password = Column(String(64))
    name     = Column(String(12))
    phone    = Column(String(11))
    nickname = Column(String(15))
    position = Column(Integer, ForeignKey('position.no'))

    def get_id(self):
        return self.id
    
    def get_name(self):
        return self.name
    
    def get_nickname(self):
        return self.nickname
    
    def get_position(self):
        return self.position
    

class Diet(Base):
    __tablename__='diet'
    no           = Column(Integer, primary_key=True, autoincrement=True)
    member_id    = Column(String(20), ForeignKey('members.id'))
    type_of_meal = Column(String(10))
    meal_time    = Column(String(8))
    comment      = Column(String(500))
    date         = Column(String(10))
    share        = Column(Integer)
    url          = Column(String(500))

    def get_no(self):
        return self.no

    def get_type_of_meal(self):
        return self.type_of_meal.strip()
    
    def get_meal_time(self):
        return self.meal_time.strip()
    
    def get_comment(self):
        return self.comment.strip()
    
    def get_date(self):
        return self.date.strip()
    
    def get_url(self):
        return self.url.strip() if self.url else None
    

class AteFood(Base):
    __tablename__='ate_food'
    no            = Column(Integer, primary_key=True, autoincrement=True)
    diet_no       = Column(Integer, ForeignKey('diet.no'))
    name          = Column(String(30))
    amount        = Column(Integer)
    kcal          = Column(Integer)
    carbohydrate  = Column(Integer)
    protein       = Column(Integer)
    fat           = Column(Integer)
    sugars        = Column(Integer)
    sodium        = Column(Integer)
    cholesterol   = Column(Integer)
    saturated_fat = Column(Integer)
    trans_fat     = Column(Integer)


    def get_ate_food_no(self):
        return self.no

    def get_diet_no(self):
        return self.diet_no
    
    def get_food_name(self):
        return self.name.strip()
    
    def get_amount(self):
        return self.amount
    
    def get_kcal(self):
        return self.kcal
    
    def get_carbohydrate(self):
        return self.carbohydrate

    def get_protein(self):
        return self.protein
    
    def get_fat(self):
        return self.fat
    
    def get_sugars(self):
        return self.sugars
    
    def get_sodium(self):
        return self.sodium
    
    def get_cholesterol(self):
        return self.cholesterol
    
    def get_saturated_fat(self):
        return self.saturated_fat
    
    def get_trans_fat(self):
        return self.trans_fat
    
class TargetKcal(Base):
    __tablename__='target_kcal'
    no           = Column(Integer, primary_key=True, autoincrement=True)
    id           = Column(String(20), ForeignKey('members.id'))
    date         = Column(String(10))
    kcal         = Column(Integer)

    def get_no(self):
        return self.no

    def get_id(self):
        return self.id.strip()
    
    def get_date(self):
        return self.date.strip()
    
    def get_target_kcal(self):
        return self.target_kcal.strip()
    
    def get_all_data(self):
        data_json = json.dumps({
            "date":self.date,
            "target_kcal":self.target_kcal
        })
        return data_json
    