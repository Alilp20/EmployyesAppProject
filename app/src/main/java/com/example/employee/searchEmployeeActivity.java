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

public class searchEmployeeActivity extends AppCompatActivity {
    DBHandler DB;
    Button search;
    TextView txt;
    Button cancel;
    EditText idEditText;
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
        search.setText(R.string.search);
        cancel.setText(R.string.cancel);
        txt.setText(R.string.search_for_an_employee);
        idEditText.setHint(R.string.search_by_employee_s_id);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        DB = new DBHandler(this);

        search = this.findViewById(R.id.SearchEmployeeButton);
        cancel = this.findViewById(R.id.cancelSearchingEmployeeButton);
        txt = this.findViewById(R.id.txtSearch);

        idEditText = this.findViewById(R.id.EmployeeIdSearchEditText);

        search.setOnClickListener(v -> search());
        cancel.setOnClickListener(v -> finish());
    }

    public void search(){
        String result = "Student Not Found";
        Employee e = DB.getEmployee(idEditText.getText().toString());
        if(e==null){
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
        else{
            result = "Student Found";
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}