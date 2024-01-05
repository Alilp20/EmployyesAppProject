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

public class modifyEmployeeActivity extends AppCompatActivity {
    DBHandler DB;
    Button modify;
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
        txt.setText(R.string.update_an_employee_by_id);
        modify.setText(R.string.upadate_an_employee);
        cancel.setText(R.string.cancel);
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
        setContentView(R.layout.activity_modify_employee);
        DB = new DBHandler(this);

        modify = this.findViewById(R.id.updateEmployeeButton);
        cancel = this.findViewById(R.id.cancelUpdatingEmployeeButton);
        txt = this.findViewById(R.id.txtUpdate);

        idEditText = this.findViewById(R.id.EmployeeIdUpdateEditText);
        firstNameEditText = this.findViewById(R.id.FirstNameUpdateEditText);
        lastNameEditText = this.findViewById(R.id.LastNameUpdateEditText);
        phoneNumberEditText = this.findViewById(R.id.PhoneNumberUpdateEditText);
        emailEditText = this.findViewById(R.id.EmailUpdateEditText);
        positionEditText = this.findViewById(R.id.PositionUpdateEditText);

        modify.setOnClickListener(v -> modify());
        cancel.setOnClickListener(v -> finish());
    }
    public void modify(){
        String employeeID = idEditText.getText().toString();
        String employeeFirstName = firstNameEditText.getText().toString();
        String employeeLastName = lastNameEditText.getText().toString();
        String employeePhoneNumber = phoneNumberEditText.getText().toString();
        String employeeEmail = emailEditText.getText().toString();
        String employeePosition = positionEditText.getText().toString();

        if (!employeeID.isEmpty() && !employeeFirstName.isEmpty() && !employeeLastName.isEmpty()) {
            DB.modifyEmployee(employeeID, employeeFirstName, employeeLastName, employeePhoneNumber, employeeEmail, employeePosition);
            Toast.makeText(this, "Employee Updated", Toast.LENGTH_SHORT).show();
        }
    }
}