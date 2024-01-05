package com.example.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "EmployeeDB";
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_EMPLOYEE_ID = "employeeID";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_POSITION = "position";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMPLOYEE_ID + " TEXT UNIQUE,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_POSITION + " TEXT" + ")";
        DB.execSQL(CREATE_EMPLOYEES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(DB);
    }

    //This function allows us to add a new employee
    public void addEmployee(Employee e){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_ID, e.employeeId);
        values.put(COLUMN_FIRST_NAME, e.firstName);
        values.put(COLUMN_LAST_NAME, e.lastName);
        values.put(COLUMN_PHONE_NUMBER, e.phoneNumber);
        values.put(COLUMN_EMAIL, e.email);
        values.put(COLUMN_POSITION, e.position);
        DB.insert(TABLE_NAME, null, values);
        DB.close();
    }

    //This function allows us to delete and employee
    public void deleteEmployee(String s){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete(TABLE_NAME,COLUMN_EMPLOYEE_ID+"=?",new String[]{s});
    }

    //This function allows us to modify an employee's information
    public void modifyEmployee(String employeeId, String newFirstName, String newLastName, String newPhoneNumber, String newEmail, String newPosition) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, newFirstName);
        values.put(COLUMN_LAST_NAME, newLastName);
        values.put(COLUMN_PHONE_NUMBER, newPhoneNumber);
        values.put(COLUMN_EMAIL, newEmail);
        values.put(COLUMN_POSITION, newPosition);

        DB.update(TABLE_NAME, values, COLUMN_EMPLOYEE_ID + " = ?", new String[]{employeeId});
        DB.close();
    }

    //This function allows us to get an employee from the database
    public Employee getEmployee(String employeeId){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMPLOYEE_ID + "=?", new String[]{employeeId});
        Employee employee = null;

        if(cursor.moveToFirst()) {
            employee = new Employee(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        }

        cursor.close(); // Close the cursor after retrieving data
        DB.close(); // Close the database connection

        return employee;
    }

    //This function allows us to get the list of all employees
    public Cursor getAllEmployees() {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
