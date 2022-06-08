package com.example.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import com.example.project.Models.Section;
import com.example.project.Models.Task;
import com.example.project.Models.User;
import com.example.project.SharedPreferences.SharedPrefManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE USER (EMAIL TEXT PRIMARY KEY," +
                "FIRST_NAME TEXT," +
                "LAST_NAME TEXT," +
                "PASSWORD TEXT)";
        sqLiteDatabase.execSQL(query);

        String TasksQuery = "CREATE TABLE TASK (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER," +
                "email TEXT," +
                "complete INTEGER)";
        sqLiteDatabase.execSQL(TasksQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertCustomer(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRST_NAME", user.getFirstName());
        contentValues.put("LAST_NAME", user.getLastName());
        contentValues.put("PASSWORD", user.getPassword());
        sqLiteDatabase.insert("USER", null, contentValues);
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }
    public User getUser(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER where email = '" + email + "'", null);

        while(cursor.moveToNext()){
            String myemail = cursor.getString(0);
            String FirstName =cursor.getString(1);
            String LastName = cursor.getString(2);
            String Password =cursor.getString(3);
            return new User(myemail,FirstName,LastName,Password);
        }
        return  null;
    }

    public boolean checkAlreadyExist(String email)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "Select EMAIL From USER Where EMAIL = '"+email  +"'";
        System.out.println(query);
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if (cursor.getCount() == 0)
        {
            return false;
        }
        else
            return true;
    }
    public boolean ValidateLogIn(String email,String password)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "Select EMAIL From USER Where EMAIL = '"+email  +"' And PASSWORD = '" +password+"'";
        System.out.println(query);
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if (cursor.getCount() == 0)
        {
            return false;
        }
        else
            return true;
    }

    public void insertTask(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", task.getDesc());
        contentValues.put("year", task.getYear());
        contentValues.put("month", task.getMonth());
        contentValues.put("day", task.getDay());
        contentValues.put("email", task.getEmail());
        contentValues.put("complete", 0);
        sqLiteDatabase.insert("TASK", null, contentValues);


    }


    public Cursor getAllTasks(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM TASK where email = '"+email+"'", null);
    }

    public List<Section> getTasksBetween(String email,int sday,int smonth, int syear,int eday, int emonth, int eyear){
        Cursor All = getAllTasks( email);
        HashMap<String, Section> allTasks = new HashMap<String, Section>();

        while (All.moveToNext()){
            Task task = new Task();
            task.setId(All.getInt(0));
            task.setDesc(All.getString(1));
            task.setYear(All.getInt(2));
            task.setMonth(All.getInt(3));
            task.setDay(All.getInt(4));
            task.setEmail(All.getString(5));
            task.setComplete(All.getInt(6));
            int day = All.getInt(4);
            int month = All.getInt(3);
            int year = All.getInt(2);

            String header = day+"/"+month+"/"+year;
            if(allTasks.containsKey(header)){
                allTasks.get(header).getSectionItems().add(task);
            }else{
                List<Task> sectionItems = new ArrayList<>();
                sectionItems.add(task);
                Section s = new Section(header,sectionItems);
                allTasks.put(header,s);
            }
        }

        List<Section> filtered = new ArrayList<>();


        int day = sday;
        int month = smonth;
        int year = syear;
        System.out.print("CURRENT ="+day+"/"+month+"/"+year);
        System.out.print("end ="+eday+"/"+emonth+"/"+eyear);
        while(1 == 1){
            String SearchDate = day+"/"+month+"/"+year;
            if(allTasks.containsKey(SearchDate)){
                    filtered.add(allTasks.get(SearchDate));
            }
            if(day == eday && year == eyear && month == emonth) {
                break;
            }
            if (month == 1 && day == 31){
                month = 2;
                day = 1;
            }else if(month == 2 && day == 28){
                month++;
                day =1;
            }else if(month == 3 && day == 31){
                month++;
                day =1;
            }else if(month == 4 && day == 30){
                month++;
                day =1;
            }else if(month == 5 && day == 31){
                month++;
                day =1;
            }else if(month == 6 && day == 30){
                month++;
                day =1;
            }else if(month == 7 && day == 31){
                month++;
                day =1;
            }else if(month == 8 && day == 31){
                month++;
                day =1;
            }else if(month == 9 && day == 30){
                month++;
                day =1;
            }else if(month == 10 && day == 31){
                month++;
                day =1;
            }else if(month == 11 && day == 30){
                month++;
                day =1;
            }else if(month == 12 && day == 31){
                year++;
                month = 1;
                day =1;
            }else{
                day++;
            }

        }
        return filtered;
    }

    public HashMap<String,Section> getAllTasksHashMap(String email) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();


        Cursor All =  sqLiteDatabase.rawQuery("SELECT * FROM TASK where email ='"+email+"'", null);

        HashMap<String, Section> allTasks = new HashMap<String, Section>();

        while (All.moveToNext()){
            Task task = new Task();
            task.setId(All.getInt(0));
            task.setDesc(All.getString(1));
            task.setYear(All.getInt(2));
            task.setMonth(All.getInt(3));
            task.setDay(All.getInt(4));
            task.setEmail(All.getString(5));
            task.setComplete(All.getInt(6));
            int day = All.getInt(4);
            int month = All.getInt(3);
            int year = All.getInt(2);

            String header = day+"/"+month+"/"+year;
            if(allTasks.containsKey(header)){
                allTasks.get(header).getSectionItems().add(task);
            }else{
                List<Task> sectionItems = new ArrayList<>();
                sectionItems.add(task);
                Section s = new Section(header,sectionItems);
                allTasks.put(header,s);
            }
        }

        return  allTasks;
    }

    public Cursor getTodayTasks(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();

        int year = date.getYear();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        return sqLiteDatabase.rawQuery("SELECT * FROM TASK WHERE " +
                        "email = '"+email+"' and " +
                "year ="+year+" " +
                        "and month ="+month+ " and day="+day
                , null);
    }

    public void MarkTodayTaskAsDone(int id,int newvalue) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();
        ContentValues data=new ContentValues();
        data.put("complete",newvalue);

        sqLiteDatabase.update("TASk", data, "id=" + id, null);
        System.out.println("COMPLETE");

    }
    public void updateProfile(String email,String FirstName, String Lastname , String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues data=new ContentValues();
        data.put("EMAIL",email);
        data.put("FIRST_NAME",FirstName);
        data.put("LAST_NAME",Lastname);
        data.put("PASSWORD",password);
        System.out.println("new email = "+email);
        sqLiteDatabase.update("USER", data, "EMAIL = '" + email+"'", null);
    }

    public List<Section> getWeek(String email){
        List<Section> ss = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();

        int year = date.getYear();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        for(int i = 0; i < 7 ; i++){
            int current_day = day + i;
            Cursor c =sqLiteDatabase.rawQuery("SELECT * FROM TASK WHERE " +
                            "email = '"+email+"' and " +
                            "year ="+year+" " +
                            "and month ="+month+ " and day="+current_day
                    , null);
            String SectionName = current_day+"/"+month+"/"+year;
            List<Task> currentTasks = new ArrayList<>();
            while (c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(0));
                task.setDesc(c.getString(1));
                task.setYear(c.getInt(2));
                task.setMonth(c.getInt(3));
                task.setDay(c.getInt(4));
                task.setEmail(c.getString(5));
                task.setComplete(c.getInt(6));

                currentTasks.add(task);
            }
            Section section = new Section(SectionName,currentTasks);
            ss.add(section);
        }
        return ss;
    }


    public void changeTextOfTask(int id, String text) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();
        ContentValues data=new ContentValues();
        data.put("description",text);

        sqLiteDatabase.update("TASK", data, "id=" + id, null);
    }

    public void deleteTask(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        sqLiteDatabase.delete("TASK", "id=" + id, null);


    }
}
