package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.Locale;

public class ItemDetail extends AppCompatActivity {
    EditText id;
    EditText firstName;
    EditText lastName;
    EditText phone;
    EditText email;
    EditText position;
    DBHandler DB;
    Button call;
    Button sms;
    Button emailBtn;
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
        call.setText(R.string.call);
        emailBtn.setText(R.string.send_an_emal);
        sms.setText(R.string.send_sms);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        String employeeFirstName = getIntent().getStringExtra("EMPLOYEE_FIRST_NAME");
        String employeeLastName = getIntent().getStringExtra("EMPLOYEE_LAST_NAME");
        String employeeId = getIntent().getStringExtra("EMPLOYEE_ID");
        String employeePhoneNumber = getIntent().getStringExtra("EMPLOYEE_PHONE_NUMBER");
        String employeeEmail = getIntent().getStringExtra("EMPLOYEE_EMAIL");
        String employeePosition = getIntent().getStringExtra("EMPLOYEE_POSITION");

        firstName = this.findViewById(R.id.firstNameTextView);
        lastName = this.findViewById(R.id.lastNameTextView);
        id = this.findViewById(R.id.idTextView);
        phone = this.findViewById(R.id.phoneTextView);
        email = this.findViewById(R.id.emailTextView);
        position = this.findViewById(R.id.positionTextView);

        DB=new DBHandler(this);

        call = this.findViewById(R.id.Call);
        sms = this.findViewById(R.id.SMS);
        emailBtn = this.findViewById(R.id.EMAIL);

        call.setOnClickListener(v -> callFunc(employeePhoneNumber));
        sms.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter SMS Message");

            // Set up the input
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Send", (dialog, which) -> {
                String messageToSend = input.getText().toString().trim();
                if (!messageToSend.isEmpty()) {
                    smsFunc(employeePhoneNumber, messageToSend);
                } else {
                    Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
        emailBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Compose Email");

            // Set up the input fields
            final EditText subjectInput = new EditText(this);
            subjectInput.setInputType(InputType.TYPE_CLASS_TEXT);
            subjectInput.setHint("Subject");

            final EditText bodyInput = new EditText(this);
            bodyInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            bodyInput.setHint("Body");
            bodyInput.setMinLines(4);

            // Set up the layout for the dialog (add the input fields)
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(subjectInput);
            layout.addView(bodyInput);
            builder.setView(layout);

            // Set up the buttons
            builder.setPositiveButton("Send", (dialog, which) -> {
                String subject = subjectInput.getText().toString().trim();
                String body = bodyInput.getText().toString().trim();

                if (!subject.isEmpty() && !body.isEmpty()) {
                    emailFunc(employeeEmail, subject, body);
                } else {
                    Toast.makeText(this, "Please fill in subject and body", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });


        if (employeeFirstName != null && employeeLastName != null && employeeId != null && employeePhoneNumber != null && employeeEmail != null && employeePosition != null) {
            firstName.setText(employeeFirstName);
            lastName.setText(employeeLastName);
            id.setText(employeeId);
            phone.setText(employeePhoneNumber);
            email.setText(employeeEmail);
            position.setText(employeePosition);
        }
    }
    public void callFunc(String number){
        Uri uri = Uri.parse("tel:"+number);
        Intent appel = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(appel);
    }

    public void smsFunc(String employeePhoneNumber, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(employeePhoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_LONG).show();
            e.printStackTrace(); // Print the exception details in Logcat
        }
    }


    public void emailFunc(String email, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822"); // Set the MIME type for emails

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); // Recipient email address
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject); // Email subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, body); // Email body

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

}