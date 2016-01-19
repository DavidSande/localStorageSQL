package com.homework_android_storage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Deparment extends AppCompatActivity {

    private static final String DATABASE_TABLE_DEPARTMENTS = "employees";
    private static final String DATABASE_NAME = "myDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deparment);

        SQLiteDatabase myDB;
        myDB = openOrCreateDatabase(DATABASE_NAME,
                Context.MODE_PRIVATE, null);

        //Taking id of the department selected
        Intent intent = getIntent();
        String idDepartment = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //Linking spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //Take from database the employees of this department
                String[] resultColumns = new String[] { "_id", "ename","salary","department_id"};
                Cursor cursor = myDB.query(DATABASE_TABLE_DEPARTMENTS, resultColumns,
                        "department_id="+idDepartment, null, null, null, null, null);


        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, // Use a template
                // that displays a
                // text view
                cursor, // Give the cursor to the adapter
                new String[] {"_id","ename","salary","department_id"},
                new int[] {android.R.id.text1,android.R.id.text2}, 0); // The "text1" view defined in
        // the XML template
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());


    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int position, long id) {
            Cursor c = (Cursor) parent.getItemAtPosition(position);
            String employee = c.getString(1); // Column index
            Toast.makeText(getApplicationContext(),
                    employee + " is selected",
                    Toast.LENGTH_SHORT).show();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

}
