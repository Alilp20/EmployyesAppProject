package com.example.employee;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_layout, parent,
                false);
    }
    // The bindView method is used to bind all data to a given view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView empId =  view.findViewById(R.id.empId);
        TextView empFName =  view.findViewById(R.id.empFName);
        TextView empLName =  view.findViewById(R.id.empLName);
        TextView empPhone =  view.findViewById(R.id.empPhone);
        TextView empEmail =  view.findViewById(R.id.empEmail);
        TextView empPos =  view.findViewById(R.id.empPos);
        // Extract properties from cursor
        String id = cursor.getString(1);
        String fName = cursor.getString(2);
        String lNAme = cursor.getString(3);
        String phone = cursor.getString(4);
        String email = cursor.getString(5);
        String position = cursor.getString(6);
        // Populate fields with extracted properties
        empId.setText(id);
        empFName.setText(fName);
        empLName.setText(lNAme);
        empPhone.setText(phone);
        empEmail.setText(email);
        empPos.setText(position);
    }
}
