package subhro.example.com.attendancemanagementapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AttendanceActivity extends AppCompatActivity {

    EditText ename,subject_no,emarks;
    Button add,view,viewall,logout1,delete,modify,aboutus;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ename=(EditText)findViewById(R.id.name);
        logout1=(Button)findViewById(R.id.logout);

        subject_no=(EditText)findViewById(R.id.roll_no);
        emarks=(EditText)findViewById(R.id.marks);
        add=(Button)findViewById(R.id.addbtn);

        view=(Button)findViewById(R.id.viewbtn);
        viewall=(Button)findViewById(R.id.viewallbtn);
        delete=(Button)findViewById(R.id.deletebtn);

        modify=(Button)findViewById(R.id.modifybtn);
        aboutus=(Button)findViewById(R.id.about);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.pragatisoftech.com"));
                startActivity(intent);
            }
        });

        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent(AttendanceActivity.this,LoginActivity.class);
                startActivity(viewIntent);
            }
        });



        //Here You Create Database
        db=openOrCreateDatabase("Intern_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS intern(usn INTEGER,name VARCHAR,attendance INTEGER);");


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(subject_no.getText().toString().trim().length()==0||
                        ename.getText().toString().trim().length()==0||
                        emarks.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO intern VALUES('"+subject_no.getText()+"','"+ename.getText()+
                        "','"+emarks.getText()+"');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(subject_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Intern ID:");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM intern WHERE usn='"+subject_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM intern WHERE usn='"+subject_no.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Id!!");
                }
                clearText();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(subject_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Intern Id");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM intern WHERE usn='"+subject_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE intern SET name='"+ename.getText()+"',attendance='"+emarks.getText()+
                            "' WHERE usn='"+subject_no.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Id!!!");
                }
                clearText();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(subject_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Intern Id:");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM intern WHERE usn='"+subject_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    ename.setText(c.getString(1));
                    emarks.setText(c.getString(2));
                }
                else
                {
                    showMessage("Error", "Invalid Id!!!");
                    clearText();
                }
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c=db.rawQuery("SELECT * FROM intern", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Inter Id:: "+c.getString(0)+"\n");
                    buffer.append("Intern Name: "+c.getString(1)+"\n");
                    buffer.append("Attendances: "+c.getString(2)+"\n\n");
                }
                showMessage("Intern Details", buffer.toString());
            }
        });


    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        subject_no.setText("");
        ename.setText("");
        emarks.setText("");
        subject_no.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

}