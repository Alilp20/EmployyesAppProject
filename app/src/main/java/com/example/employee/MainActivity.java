package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button add;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switcher;
    Boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView txt;
    Button delete;
    Button search;
    Button modify;
    Button showAll;

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
        delete.setText(R.string.delete_an_employee);
        search.setText(R.string.search_for_an_employee);
        modify.setText(R.string.upadate_an_employee);
        showAll.setText(R.string.show_all_employees);
        txt.setText(R.string.employees_managment_app);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = this.findViewById(R.id.addButton);
        delete = this.findViewById(R.id.deleteButton);
        search = this.findViewById(R.id.searchButton);
        modify = this.findViewById(R.id.modifyButton);
        showAll = this.findViewById(R.id.showAllButton);
        txt = this.findViewById(R.id.txt);
        switcher = this.findViewById(R.id.themeSwitch);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        if (nightMode){
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switcher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });
        add.setOnClickListener(v -> add());
        delete.setOnClickListener(v -> delete());
        search.setOnClickListener(v -> search());
        modify.setOnClickListener(v -> modify());
        showAll.setOnClickListener(v -> showAllEmployees());
    }

    public void add(){
        Intent addEmployee=new Intent(this, addEmployeeActivity.class);
        startActivity(addEmployee);
    }

    public void delete(){
        Intent deleteEmployee=new Intent(this, deleteEmployeeActivity.class);
        startActivity(deleteEmployee);
    }

    public void search(){
        Intent searchEmployee=new Intent(this, searchEmployeeActivity.class);
        startActivity(searchEmployee);
    }

    public void modify(){
        Intent modifyEmployee=new Intent(this, modifyEmployeeActivity.class);
        startActivity(modifyEmployee);
    }

    public void showAllEmployees(){
        Intent showAllEmployees=new Intent(this, showAllEmployeeActivity.class);
        startActivity(showAllEmployees);
    }
}