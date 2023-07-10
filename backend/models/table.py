from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.ext.declarative import declarative_base

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
    no         = Column(int, primary_key=True, autoincrement=True)
    member_id   = Column(String(64), ForeignKey('Members.id'))
    type_of_meal = Column(String(10))
    meal_time   = Column(String(8))
    comment    = Column(String(500))
    date       = Column(String(10))
    share      = Column(int)
    url        = Column(String(500))

    def get_type_of_meal(self):
        return self.typeOfMeal
    
    def get_meal_time(self):
        return self.meal_time
    
    def get_comment(self):
        return self.comment
    
    def get_date(self):
        return self.date
    
    def get_url(self):
        return self.url
    

class AteFood(Base):
    __tablename__='AteFood'
    no            = Column(int, primary_key=True, autoincrement=True)
    diet_no       = Column(int, ForeignKey('Diet.no'))
    serial_number = Column(int)
    food_Name     = Column(String(100))
    amount        = Column(int)
    kcal          = Column(int)
    carbohydrate  = Column(int)
    protein       = Column(int)
    fat           = Column(int)
    sodium        = Column(int)

    def get_diet_no(self):
        return self.diet_no

    def get_serial_number(self):
        return self.typeOfMeal
    
    def get_food_Name(self):
        return self.food_Name
    
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
    
    def get_sodium(self):
        return self.sodium

    