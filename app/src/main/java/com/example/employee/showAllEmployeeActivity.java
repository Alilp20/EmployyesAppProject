package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class showAllEmployeeActivity extends AppCompatActivity {
    Button goBack;
    TextView txt;
    MyCursorAdapter adapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.itemEn) {
            changeLanguage("eng");
        } else if (item.getItemId() == R.id.itemFr) {
            changeLanguage("fr");
        } else if (item.getItemId() == R.id.itemAr) {
            changeLanguage("ar");
        }
        return true;
    }
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);// create a locale(lang is the language code)
        Locale.setDefault(myLocale);// set default locale
        Configuration config = new Configuration();// retrieve config
        config.locale = myLocale; // choose local configuration
        // Update configuration
        Resources resources=this.getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        // Update the text according to the new option
        goBack.setText(R.string.cancel);
        txt.setText(R.string.list_of_all_employees);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_employee);
        goBack = this.findViewById(R.id.goBack);
        txt = this.findViewById(R.id.txtAll);

        goBack.setOnClickListener(v -> finish());

        // Initialize DBHandler within a try-with-resources block
        try (DBHandler DB = new DBHandler(this)) {
            // Get the content of the table
            Cursor cursor = DB.getAllEmployees(); // A method that returns the student table
            adapter = new MyCursorAdapter(this, cursor);
            ListView lv = this.findViewById(R.id.lv);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(((parent, view, position, id) -> {
                Cursor clickedCursor = (Cursor) parent.getItemAtPosition(position);
                if(clickedCursor != null){
                    int FirstNameIndex = clickedCursor.getColumnIndex("firstName");
                    int LastNameIndex = clickedCursor.getColumnIndex("lastName");
                    int employeeIdIndex = clickedCursor.getColumnIndex("employeeID");
                    int phoneNumberIndex = clickedCursor.getColumnIndex("phoneNumber");
                    int emailIndex = clickedCursor.getColumnIndex("email");
                    int positionIndex = clickedCursor.getColumnIndex("position");

                    String employeeFirstName = clickedCursor.getString(FirstNameIndex);
                    String employeeLastName = clickedCursor.getString(LastNameIndex);
                    String employeeId = clickedCursor.getString(employeeIdIndex);
                    String employeePhoneNumber = clickedCursor.getString(phoneNumberIndex);
                    String employeeEmail = clickedCursor.getString(emailIndex);
                    String employeePosition = clickedCursor.getString(positionIndex);

                    if(FirstNameIndex != -1 && LastNameIndex != -1 && employeeIdIndex != -1 && phoneNumberIndex != -1 && emailIndex != -1 && positionIndex != -1){

                        Intent intent = new Intent(showAllEmployeeActivity.this, ItemDetail.class);
                        intent.putExtra("EMPLOYEE_FIRST_NAME", employeeFirstName);
                        intent.putExtra("EMPLOYEE_LAST_NAME", employeeLastName);
                        intent.putExtra("EMPLOYEE_ID", employeeId);
                        intent.putExtra("EMPLOYEE_PHONE_NUMBER", employeePhoneNumber);
                        intent.putExtra("EMPLOYEE_EMAIL", employeeEmail);
                        intent.putExtra("EMPLOYEE_POSITION", employeePosition);
                        startActivity(intent);
                        finish();
                    }else {
                        // Handle case where columns are not found
                        Toast.makeText(showAllEmployeeActivity.this, "Columns not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        } catch (Exception e) {
            // Handle any exceptions or errors here
            e.printStackTrace();
            Toast.makeText(this, "Error accessing the database", Toast.LENGTH_SHORT).show();
        }

    }
}