package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class deleteEmployeeActivity extends AppCompatActivity {
    DBHandler DB;
    Button delete;
    TextView txt;
    Button cancel;
    EditText idEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
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
        delete.setText(R.string.delete_an_employee);
        cancel.setText(R.string.cancel);
        txt.setText(R.string.add_a_new_employee);
        idEditText.setHint(R.string.employee_id);
        firstNameEditText.setHint(R.string.employee_first_name);
        lastNameEditText.setHint(R.string.employee_last_name);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        DB = new DBHandler(this);

        delete = this.findViewById(R.id.deleteEmployeeButton);
        cancel = this.findViewById(R.id.cancelDeletingEmployeeButton);
        txt = this.findViewById(R.id.txtDelete);

        idEditText = this.findViewById(R.id.EmployeeIdDeleteEditText);
        firstNameEditText = this.findViewById(R.id.FirstNameDeleteEditText);
        lastNameEditText = this.findViewById(R.id.LastNameDeleteEditText);

        delete.setOnClickListener(v -> delete());
        cancel.setOnClickListener(v -> finish());
    }

    public void delete(){
        DB.deleteEmployee(idEditText.getText().toString());
        Employee e = DB.getEmployee(idEditText.getText().toString());
        if(e==null){
            Toast.makeText(this, "Employee Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Employee Still Exists", Toast.LENGTH_SHORT).show();
        }
    }
}