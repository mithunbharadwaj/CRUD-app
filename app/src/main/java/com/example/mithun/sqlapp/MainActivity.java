package com.example.mithun.sqlapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editId;
    Button buttonAdd, buttonAll, buttonEdit,buttonDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calls constructor of DatabaseHelper class
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        editId = (EditText) findViewById(R.id.editText_id);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAll = (Button) findViewById(R.id.button_viewAll);
        buttonEdit = (Button) findViewById(R.id.button_edit);
        buttonDelete = (Button) findViewById(R.id.button_delete);

        AddData();
        viewAll();
        EditData();
        DeleteData();

    }

    public void DeleteData() {
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void EditData() {
        buttonEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editId.getText().toString(), editName.getText().toString(), editSurname.getText().toString()
                                , editMarks.getText().toString());

                        if (isUpdated = true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //Helps to append Contents into the database
    public void AddData() {
        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void viewAll() {
        buttonAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessgae("Error ", "Nothing found");
                            //show All
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Surname : " + res.getString(2) + "\n");
                            buffer.append("Marks : " + res.getString(3) + "\n \n");
                        }
                        showMessgae("Data ", buffer.toString());
                    }
                }
        );
    }
     // Used to display message on AlertDialod window
    public void showMessgae(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
