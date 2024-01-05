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

public class addEmployeeActivity extends AppCompatActivity {
    DBHandler DB;
    Button add;
    TextView txt;
    Button cancel;
    EditText idEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;
    EditText emailEditText;
    EditText positionEditText;
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
        add.setText(R.string.add_an_employee);
        cancel.setText(R.string.cancel);
        txt.setText(R.string.add_a_new_employee);
        idEditText.setHint(R.string.employee_id);
        firstNameEditText.setHint(R.string.employee_first_name);
        lastNameEditText.setHint(R.string.employee_last_name);
        phoneNumberEditText.setHint(R.string.employee_phone_number);
        emailEditText.setHint(R.string.employee_e_mail);
        positionEditText.setHint(R.string.employee_position);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        DB = new DBHandler(this);

        add = this.findViewById(R.id.addEmployeeButton);
        cancel = this.findViewById(R.id.cancelAddingEmployeeButton);
        txt = this.findViewById(R.id.txtAdd);

        idEditText = this.findViewById(R.id.EmployeeIdEditText);
        firstNameEditText = this.findViewById(R.id.FirstNameEditText);
        lastNameEditText = this.findViewById(R.id.LastNameEditText);
        phoneNumberEditText = this.findViewById(R.id.PhoneNumberEditText);
        emailEditText = this.findViewById(R.id.EmailEditText);
        positionEditText = this.findViewById(R.id.PositionEditText);

        add.setOnClickListener(v -> add());
        cancel.setOnClickListener(v -> finish());
    }

    public void add(){
        Employee e = new Employee(idEditText.getText().toString(), firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), phoneNumberEditText.getText().toString(), emailEditText.getText().toString(), positionEditText.getText().toString());
        DB.addEmployee(e);
        Toast.makeText(this, "Employee added successfully", Toast.LENGTH_SHORT).show();
    }
}