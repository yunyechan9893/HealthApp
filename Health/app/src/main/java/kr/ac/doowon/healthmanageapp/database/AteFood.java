package kr.ac.doowon.healthmanageapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ate_food",
        foreignKeys =
                @ForeignKey(
                        entity = Diet.class,
                        parentColumns = "diet_Id",
                        childColumns = "diet_no",
                        onDelete=ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )

)
public class AteFood{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no")
    @NonNull
    public int no;

    @ColumnInfo(name = "diet_no")
    public int dietNo;

    @ColumnInfo(name = "serial_number")
    public int serialNumber;

    @ColumnInfo(name = "food_name")
    public String foodName;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "kcal")
    public int kcal;

    @ColumnInfo(name = "carbohydrate")
    public int carbohydrate;

    @ColumnInfo(name = "protein")
    public int protein;

    @ColumnInfo(name = "fat")
    public int fat;

    @ColumnInfo(name = "sodium")
    public int sodium;

    public void setNo(int no) {
        this.no = no;
    }

    public AteFood setDietNo(int dietNo) {
        this.dietNo = dietNo;
        return this;
    }

    public AteFood setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public AteFood setFoodName(String food_name) {
        this.foodName = food_name;
        return this;
    }

    public AteFood setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public AteFood setKcal(int kcal) {
        this.kcal = kcal;
        return this;
    }

    public AteFood setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }

    public AteFood setProtein(int protein) {
        this.protein = protein;
        return this;
    }

    public AteFood setFat(int fat) {
        this.fat = fat;
        return this;
    }

    public AteFood setSodium(int sodium) {
        this.sodium = sodium;
        return this;
    }
}