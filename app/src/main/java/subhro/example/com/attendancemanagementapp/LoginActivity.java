package subhro.example.com.attendancemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    EditText user,pass;
    private long backPressedTime;
    int count=0;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=(Button)findViewById(R.id.login);
        user=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        forgot=(TextView)findViewById(R.id.forgotpass);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Please Mail Us  at hr@pragatisoftech.com", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        String user_name=user.getText().toString().trim();
        String user_pass=pass.getText().toString().trim();

        if(user_name.equals("admin")&& user_pass.equals("admin123")) {
            startActivity(new Intent(LoginActivity.this,AttendanceActivity.class));
        }


        else {
            Toast.makeText(this,"username and password do not matched!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        }

        backPressedTime=System.currentTimeMillis();
    }

}

