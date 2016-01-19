package com.homework_android_storage;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private static final String DATABASE_NAME = "myDB.db";
    private static final String DATABASE_TABLE_DEPARTMENTS = "DEPARTMENTS";
    private static final String DATABASE_TABLE_EMPLOYEES = "employees";

    public static String EXTRA_MESSAGE = "";
    Intent intent;

    private static final String CREATE_TABLE_DEPARTMENTS = "CREATE TABLE IF NOT EXISTS "
            + DATABASE_TABLE_DEPARTMENTS
            + " ( _id int(11) NOT NULL , dname varchar(255) NOT NULL, PRIMARY KEY(_id))";


    private static final String CREATE_TABLE_EMPLOYEES = "create table if not exists "
            + DATABASE_TABLE_EMPLOYEES
            + " ( _id int(11) NOT NULL , "
            + " ename varchar(255) NOT NULL, "
            + "department_id int(11) default NULL,"
            + "salary decimal(7,2) NOT NULL,"
            + "PRIMARY KEY  (_id)" +
            "    )";


    private static final String DATABASE_DELETE_DEPARTMENTS = "drop table if exists "
            + DATABASE_TABLE_DEPARTMENTS;

    private static final String DATABASE_DELETE_EMPLOYEES = "drop table if exists "
            + DATABASE_TABLE_EMPLOYEES;

    SQLiteDatabase myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Create or open database
        myDB = openOrCreateDatabase(DATABASE_NAME,
                Context.MODE_PRIVATE, null);

        //Delete and create tables
        myDB.execSQL(DATABASE_DELETE_DEPARTMENTS);
        myDB.execSQL(DATABASE_DELETE_EMPLOYEES);

        myDB.execSQL(CREATE_TABLE_DEPARTMENTS);
        myDB.execSQL(CREATE_TABLE_EMPLOYEES);

        //Insert Data on tables
        myDB.execSQL("INSERT INTO departments( _id, dname) " +
                        " VALUES" +
                        " (1,'Engineering'),       " +
                        " (2,'Sales'),      " +
                        " (3,'Marketing')," +
                        " (4,'HR');");

        myDB.execSQL("INSERT INTO employees( _id, ename, salary, department_id)" +
                " VALUES" +
                " (1,'jack','3000.00', 1)," +
                " (2,'mary','2500.00', 2)," +
                " (3,'nichole','4000.00', 1)," +
                " (4,'angie','5000.00', 2)," +
                " (5,'jones','5000.00', 3)," +
                " (6,'newperson','5000.00', NULL);");


        String[] resultColumns = new String[] { "_id", "dname"};
        Cursor cursor = myDB.query(DATABASE_TABLE_DEPARTMENTS, resultColumns,
                null, null, null, null, null, null);

        // Create ListAdapter object
        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, new String[] {
                "_id", "dname" }, new int[] {
                android.R.id.text1, android.R.id.text2 }, 0);
        setListAdapter(adapter);

        //cursor.close();

        ListView lv = getListView();

        intent = new Intent(this, Deparment.class);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Cursor c = (Cursor) parent.getItemAtPosition(position);
                String idDep = c.getString(0); // Column index


                intent.putExtra(EXTRA_MESSAGE, idDep);
                startActivity(intent);

            }
        });

        lv.setTextFilterEnabled(true);


    }



}
