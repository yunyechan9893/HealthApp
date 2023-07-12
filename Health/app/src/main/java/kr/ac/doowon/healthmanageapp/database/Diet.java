package kr.ac.doowon.healthmanageapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity(tableName = "diet")
public class Diet{
    @PrimaryKey
    @ColumnInfo(name = "diet_Id")
    public int dietId;

    @ColumnInfo(name = "type_of_Meal")
    public String typeOfMeal;

    @ColumnInfo(name = "meal_time")
    public String mealTime;

    @ColumnInfo(name = "comment")
    public String comment;

    @ColumnInfo(name = "date_time")
    public String dateTime;

    @ColumnInfo(name = "url")
    public String url;

    public void setDietId(int dietId) {
        this.dietId = dietId;
    }

    public void setTypeOfMeal(String typeOfMeal) {
        this.typeOfMeal = typeOfMeal;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

