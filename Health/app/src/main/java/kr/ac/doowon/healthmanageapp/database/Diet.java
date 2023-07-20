package kr.ac.doowon.healthmanageapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    public Diet setDietId(int dietId) {
        this.dietId = dietId;
        return this;
    }

    public Diet setTypeOfMeal(String typeOfMeal) {
        this.typeOfMeal = typeOfMeal;
        return this;
    }

    public Diet setMealTime(String mealTime) {
        this.mealTime = mealTime;
        return this;
    }

    public Diet setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Diet setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Diet setUrl(String url) {
        this.url = url;
        return this;
    }
}

