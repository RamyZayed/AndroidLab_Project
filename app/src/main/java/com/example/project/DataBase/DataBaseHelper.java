package com.example.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Models.Task;
import com.example.project.Models.User;

import java.time.LocalDate;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DURATION_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + TRACKS_COL + " TEXT)";
        */
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

    public Cursor getAllTasks() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM TASK", null);
    }
    public Cursor getTodayTasks() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();

        int year = date.getYear();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        return sqLiteDatabase.rawQuery("SELECT * FROM TASK WHERE " +
                "year ="+year+" " +
                        "and month ="+month+ " and day="+day
                , null);
    }

    public void MarkTodayTaskAsDone(int position) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        LocalDate date = LocalDate.now();

        int year = date.getYear();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();


        Cursor c =sqLiteDatabase.rawQuery("SELECT * FROM TASK WHERE " +
                        "year ="+year+" " +
                        "and month ="+month+ " and day="+day
                , null);


            int i =0;
             while (c.moveToNext()){
                 c.getString(0);
                 if(i == position) break;
                i++;
             }

        int id = c.getInt(0);
             String exec = "UPDATE TASK SET complete = 1 WHERE Id = "+id;
             System.out.println(exec);
        ContentValues data=new ContentValues();
        data.put("complete",1);

        sqLiteDatabase.update("TASk", data, "id=" + id, null);
        System.out.println("COMPLETE");


    }


}
