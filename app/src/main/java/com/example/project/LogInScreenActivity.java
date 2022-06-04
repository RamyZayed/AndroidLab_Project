package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.SharedPreferences.SharedPrefManager;

public class LogInScreenActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        Button LogInButton = (Button)findViewById(R.id.LogInButton);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(LogInScreenActivity.this, "Project", null, 1);
        Intent goToHomePage = new Intent(LogInScreenActivity.this,nav_drawr.class);
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailText = (EditText)findViewById(R.id.EmailCredintial);
                EditText passwordText = (EditText)findViewById(R.id.PasswordCredintial);
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast toast =Toast.makeText(LogInScreenActivity.this,"Please Fill all the fields",Toast.LENGTH_SHORT);
                    toast.show();
                    LogInButton.startAnimation(AnimationUtils.loadAnimation(LogInScreenActivity.this, R.anim.shakeanimation));
                }
                else if(dataBaseHelper.ValidateLogIn(email,password) == true){
                    Toast toast =Toast.makeText(LogInScreenActivity.this,"LOGIN",Toast.LENGTH_SHORT);
                    toast.show();
                    CheckBox remember = (CheckBox)findViewById(R.id.rememberMe);
                    if(remember.isChecked()){
                        sharedPrefManager.writeString("EMAIL",email);
                    }
                    startActivity(goToHomePage);
                    sharedPrefManager.writeString("User_Email",email);
                    finish();

                }else {
                    Toast toast =Toast.makeText(LogInScreenActivity.this,"Password and email combination is wrong",Toast.LENGTH_SHORT);
                    toast.show();
                    LogInButton.startAnimation(AnimationUtils.loadAnimation(LogInScreenActivity.this, R.anim.shakeanimation));
                }


            }
        });


        Button SignupButton = (Button)findViewById(R.id.SignUpButton);
        Intent i = new Intent(LogInScreenActivity.this,SignUpActivity.class);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });
    }
}