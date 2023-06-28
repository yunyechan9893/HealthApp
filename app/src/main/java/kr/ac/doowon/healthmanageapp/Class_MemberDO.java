package kr.ac.doowon.healthmanageapp;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kr.ac.doowon.healthmanageapp.Class_Tool.SHA256;

public class Class_MemberDO {

    //데이터 베이스와 연결하기 위한 정보를 미리 변수에 담아두었다.
    private static String ip = "172.30.1.4";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "HealthApp";
    private static String username = "yyc";
    private static String passward = "9893";
    private static String url =  "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private static Connection connection = null;
    private static boolean permission;

    //데이터베이스 연결
    public void Connect(){
        try{
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, passward);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //데이터베이스 연결 해제
    public void Close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //로그인
    public boolean Login(String id, String pwd){
        //데이터베이스 연결
        Connect();

        //저장프로시저에 보내는 쿼리문 작성
        String sqlLogin = "{Call Proc_Login (?,?)}";
        if(id =="" || pwd =="") return false;

        try {
            //비밀번호를 암호화 시킨다.
            String shaPwd = SHA256.encrypt(pwd);
            //CallableStatement는 저장프로시저를 이용할 때 사용한다.
            CallableStatement callableStatement = connection.prepareCall(sqlLogin);
            //클라이언트가 작성한 아이디와 비밀번호를 보낸다.
            callableStatement.setString(1,id);
            callableStatement.setString(2,shaPwd);

            //결과값을 받아온다.
            ResultSet rs = callableStatement.executeQuery();
            //결과가 있다면 true, 없다면 false
            boolean bLogin = rs.next();

            //CallableStatement 해제
            callableStatement.close();
            //데이터베이스 해제
            Close();

            //성공 시 true 리턴
            if(bLogin) return true;

        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //실패 시 false 리턴
        return false;
    }

    //랜덤로그인 코드 생성
    public String RandomCodeCreate(){

        Random rnd = new Random();
        String randomCode = "";

        for(int i =0; i < 6; i++){
            final String random_name_2 = String.valueOf((char) ((int) (rnd.nextInt(26))+65));
            randomCode += random_name_2;
        }

        return randomCode;
    }

    //자동로그인시 코드저장
    public String AutoLoginCodeSave(String id){
        String sqlProc = "{Call Proc_AutoLoginCodeSave (?,?,?)}";

        while (true){
            String code = RandomCodeCreate();
            Connect();
            try {
                CallableStatement callableStatement = connection.prepareCall(sqlProc);
                callableStatement.setString(1,code);
                callableStatement.setString(2,id);
                callableStatement.registerOutParameter(3, Types.VARCHAR);
                callableStatement.execute();
                System.out.println(code);
                System.out.println(callableStatement.getString(3));
                if(callableStatement.getString(3).equals("S")) {
                    callableStatement.close();
                    Close();

                    return code;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean AutoLogin(String id, String code){
        String sqlProc = "{Call Proc_AutoLogin(?,?)}";
        boolean result = false;
        Connect();

        try {
            CallableStatement callableStatement = connection.prepareCall(sqlProc);
            callableStatement.setString(1,code);
            callableStatement.setString(2,id);

            ResultSet rs =callableStatement.executeQuery();
            result = rs.next();

            callableStatement.close();
            Close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    //아이디 중복확인
    public boolean DoubleCheckId(String id){
        String sqlDoubleCheck = "select id from Members where id = ?";
        boolean success = false;
        Connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDoubleCheck);
            preparedStatement.setString(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            success = rs.next();
            preparedStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    //닉네임 중복확인
    public boolean DoubleCheckNickName(String NickName){
        String sqlDoubleCheck = "select nickname from Members where nickname = ?";
        boolean success = false;
        Connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDoubleCheck);
            preparedStatement.setString(1,NickName);
            ResultSet rs = preparedStatement.executeQuery();
            success = rs.next();
            preparedStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    //회원가입
    //회원가입을 누르면 저장한다.
    public boolean Regist(String id, String pwd, String name,String phone, String nickname){
        String sqlRegist = "{CALL Proc_MemberRegist(?,?,?,?,?,?)}";
        boolean Success = false;
        Connect();

        try {
            String shaPw = SHA256.encrypt(pwd);
            CallableStatement callableStatement = connection.prepareCall(sqlRegist);
            callableStatement.setString(1,id);
            callableStatement.setString(2,shaPw);
            callableStatement.setString(3,name);
            callableStatement.setString(4,phone);
            callableStatement.setString(5,nickname);
            callableStatement.registerOutParameter(6, Types.VARCHAR);
            callableStatement.execute();

            if(callableStatement.getString(6).equals("S")) Success = true;
            callableStatement.close();
            Close();

        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Success;
    }

    public void AutoLogin_Delete(String id, String code){
        String sqlProc = "{Call Proc_AutoLogin_Delete(?,?,?)}";
        Connect();

        try {
            CallableStatement callableStatement = connection.prepareCall(sqlProc);
            callableStatement.setString(1,code);
            callableStatement.setString(2,id);
            callableStatement.registerOutParameter(3,Types.VARCHAR);
            callableStatement.execute();
            callableStatement.close();
            Close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String HomeBBSelect(int categoryNumber){
        String sql = "{Call Proc_HomeActivityBBTitle (?)}";
        Connect();

        try {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, categoryNumber);

            ResultSet rs = callableStatement.executeQuery();
            rs.next();
            System.out.println(rs.getString(1));
            String result = rs.getString(1);

            callableStatement.close();
            Close();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "E";
    }

    // 식단정보 가져오기
    // 다이어트 ResultSet을 List로 변환하여 반환한다.
    // null이 가능하므로 액티비티에서 잘 처리해줘야한다.
    public List<JavaBean_Management_Diet_AllAteFood> GetDietResultSet(String id){
        String sqlProc = "{Call Proc_Diet(?)}";
        Connect();

        try {
            CallableStatement callableStatement = connection.prepareCall(sqlProc);
            callableStatement.setString(1,id);
            ResultSet rs = callableStatement.executeQuery();

            List<JavaBean_Management_Diet_AllAteFood> list = ConvertResultSetTOList(rs);
            callableStatement.close();
            Close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //위에 ResultSet 하나하나의 값을 JavaBean_Management_Diet_AllAteFood에 압축하여 List에 넣는다.
    private List ConvertResultSetTOList(ResultSet rs){
        List<JavaBean_Management_Diet_AllAteFood> list = new ArrayList<>();
        try {
        while (rs.next()){
            int no = rs.getInt(1);
            String id = rs.getString(2);
            String typeOfMeal = rs.getString(3);
            String mealTime = rs.getString(4);
            String comment = rs.getString(5);
            String date = rs.getString(6);
            int share  = rs.getInt(7);
            String mealName  = rs.getString(8);
            int mealAmount = rs.getInt(9);
            int kcal = rs.getInt(10);
            int carbohydrate = rs.getInt(11);
            int protein = rs.getInt(12);
            int saturatedFat = rs.getInt(13);
            int polyunsaturatedFat = rs.getInt(14);
            int unsaturatedFat = rs.getInt(15);
            int cholesterol = rs.getInt(16);
            int dietaryFiber = rs.getInt(17);
            int sodium = rs.getInt(18);

            System.out.println(mealName);

            list.add(new JavaBean_Management_Diet_AllAteFood(no, id,typeOfMeal,mealTime,comment,date,share,mealName
            ,mealAmount,kcal,carbohydrate,protein,saturatedFat,polyunsaturatedFat,unsaturatedFat,cholesterol,dietaryFiber,sodium));

        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // diet  페이지에서 요약된 정보를 출력해주기 위해 사용
    // 먹은 식단의 칼로리, 음식의 양, 칼로리를 가져온다.
    public List getFoodList(String foodName){
        String sqlFoodSelet = "{Call Diet_Food_select(?)}";
        List<JavaBean_Management_Diet_AllAteFood> list = new ArrayList<>();
        Connect();

        try {
            CallableStatement callableStatement = connection.prepareCall(sqlFoodSelet);
            callableStatement.setString(1,foodName);
            ResultSet rs = callableStatement.executeQuery();

            if(rs.next()){
                String _foodName = rs.getString(1);
                int _amount = rs.getInt(2);
                int _kcal = rs.getInt(3);

                list.add(new JavaBean_Management_Diet_AllAteFood(_foodName,_amount,_kcal));
            }
            callableStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 먹은 식단을 등록한다.
    // Diet 테이블에 저장된다.
    public int RegistDiet(String id, String typeOfMeal, String mealTime, String comment, String date, int shere){
        String sql = "{Call Proc_Diet_insert (?,?,?,?,?,?)}";
        Connect();
        int result = 0;

        try {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, id);
            callableStatement.setString(2, typeOfMeal);
            callableStatement.setString(3, mealTime);
            callableStatement.setString(4, comment);
            callableStatement.setString(5, date);
            callableStatement.setInt(6, shere);
            ResultSet rsDietNo = callableStatement.executeQuery();
            rsDietNo.next();
            result = rsDietNo.getInt(1);

            callableStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 위와 마찬가지로 식단등록할 때 같이 사용된다.
    // Diet_Ate_Food테이블에 저장된다.
    public String RegistAteFood(int DietNo, String foodName){
        String sql = "{Call Proc_Diet_AteFood_insert (?,?,?)}";
        Connect();
        String result = "";

        try {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, DietNo);
            callableStatement.setString(2, foodName);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();

            result = callableStatement.getString(3);
            callableStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 목표칼로리를 저장한다.
    public int TargetKcalInsert(String id, String date, int targetKcal){
        String sql = "{Call Proc_Diet_TargetKcalInsert (?,?,?,?)}";
        Connect();
        int RESULT = 0;

        try {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1,id);
            callableStatement.setString(2, date);
            callableStatement.setInt(3, targetKcal);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.execute();

            RESULT = callableStatement.getInt(4);
            callableStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return RESULT;
    }

    //목표칼로리를 검색한다.
    public int TargetKcalSelect(String id, String date){
        String sSQL = "select targetKcal from Diet_TargetKcal where id = ? and [date] = ?";
        int targetKcal = 0;
        Connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sSQL);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,date);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) targetKcal = rs.getInt(1);
            preparedStatement.close();
            Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return targetKcal;
    }
}
