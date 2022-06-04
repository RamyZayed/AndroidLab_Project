package com.example.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Models.Task;
import com.example.project.Models.User;

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


}
