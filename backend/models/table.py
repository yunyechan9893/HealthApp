from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
import json
Base = declarative_base()

class Position(Base):
    __tablename__='position'
    no = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(20))

class User(Base):
    __tablename__='Members'
    id       = Column(String(20), primary_key=True)
    passward = Column(String(64))
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
    __tablename__='Diet'
    no           = Column(Integer, primary_key=True, autoincrement=True)
    member_id    = Column(String(20), ForeignKey('Members.id'))
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
        return self.url.strip()
    

class AteFood(Base):
    __tablename__='AteFood'
    no            = Column(Integer, primary_key=True, autoincrement=True)
    diet_no       = Column(Integer, ForeignKey('Diet.no'))
    serial_number = Column(Integer)
    food_name     = Column(String(100))
    amount        = Column(String(20))
    kcal          = Column(Integer)
    carbohydrate  = Column(Integer)
    protein       = Column(Integer)
    fat           = Column(Integer)
    sodium        = Column(Integer)

    def get_diet_no(self):
        return self.diet_no

    def get_serial_number(self):
        return self.serial_number
    
    def get_food_name(self):
        return self.food_name.strip()
    
    def get_amount(self):
        return self.amount.strip()
    
    def get_kcal(self):
        return self.kcal
    
    def get_carbohydrate(self):
        return self.carbohydrate

    def get_protein(self):
        return self.protein
    
    def get_fat(self):
        return self.fat
    
    def get_sodium(self):
        return self.sodium
    
class DietTargetKcal(Base):
    __tablename__='DietTargetKcal'
    no           = Column(Integer, primary_key=True, autoincrement=True)
    id           = Column(String(20), ForeignKey('Members.id'))
    date         = Column(String(10))
    target_kcal  = Column(Integer)

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
    