package kr.ac.doowon.healthmanageapp;

public class JavaBean_Management_Diet_AllAteFood {
    int no;
    String id;
    String typeOfMeal;
    String mealTime;
    String comment;
    String date;
    int share;
    String foodName;
    int foodAmount, kcal, carbohydrate, protein, saturatedFat, polyunsaturatedFat, unsaturatedFat, cholesterol, dietaryFiber, sodium;

    public int getNo() {
        return no;
    }

    public String getId() {
        return id;
    }

    public String getTypeOfMeal() {
        return typeOfMeal;
    }

    public String getMealTime() {
        return mealTime;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getShare() {
        return share;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public int getKcal() {
        return kcal;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public int getSaturatedFat() {
        return saturatedFat;
    }

    public int getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public int getUnsaturatedFat() {
        return unsaturatedFat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public int getDietaryFiber() {
        return dietaryFiber;
    }

    public int getSodium() {
        return sodium;
    }

    public JavaBean_Management_Diet_AllAteFood(String typeOfMeal, int kcal, int protein, String comment){
        this.typeOfMeal = typeOfMeal;
        this.kcal = kcal;
        this.protein = protein;
        this.comment = comment;
    }

    public JavaBean_Management_Diet_AllAteFood(String foodName,int foodAmount ,int kcal){
        this.foodName = foodName;
        this.foodAmount = foodAmount;
        this.kcal = kcal;
    }


    public JavaBean_Management_Diet_AllAteFood(int no, String id,String typeOfMeal,String mealTime,String comment,String date,int share,String FoodName
            ,int FoodAmount,int kcal,int carbohydrate,int protein,int saturatedFat,int polyunsaturatedFat, int unsaturatedFat,int cholesterol,int dietaryFiber,int sodium){

        this.no = no;
        this.id = id;
        this.typeOfMeal = typeOfMeal;
        this.mealTime = mealTime;
        this.comment = comment;
        this.date = date;
        this.share = share;
        this.foodName = FoodName;
        this.foodAmount = FoodAmount;
        this.kcal = kcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.saturatedFat = saturatedFat;
        this.polyunsaturatedFat = polyunsaturatedFat;
        this.unsaturatedFat = unsaturatedFat;
        this.cholesterol = cholesterol;
        this.dietaryFiber = dietaryFiber;
        this.sodium = sodium;
    }



}
