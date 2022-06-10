package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.Models.User;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button SignupButton = (Button)findViewById(R.id.SignUp);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText EmailEditText = (EditText)findViewById(R.id.EmailTextField);
                EditText FirstNameText = (EditText)findViewById(R.id.FirstNameText);
                EditText LastNameText = (EditText)findViewById(R.id.LastNameText);
                EditText PasswordText = (EditText)findViewById(R.id.PasswordText);
                EditText PasswordConfirmationText = (EditText)findViewById(R.id.ConfirmPasswordText);

                String email = EmailEditText.getText().toString();
                String FirstName = FirstNameText.getText().toString();
                String LastName = LastNameText.getText().toString();
                String Password = PasswordText.getText().toString();
                String ConfirmPassword = PasswordConfirmationText.getText().toString();
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUpActivity.this, "Project", null, 1);
                boolean d = dataBaseHelper.checkAlreadyExist(email);
                if(email.isEmpty() || FirstName.isEmpty() || LastName.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()
                || validateOtherFields(FirstName) == false ||validateOtherFields(LastName) == false || validatePassowrd(Password) == false ){
                    if(email.isEmpty()){
                        String myToast = "Email Field is empty.";
                        Toast toast =Toast.makeText(SignUpActivity.this,myToast,Toast.LENGTH_LONG);
                        toast.show();
                        EditText text = (EditText)findViewById(R.id.EmailTextField);
                        text.setBackgroundColor(Color.parseColor("#30FF0000"));
                    }
                    if(!validateOtherFields(FirstName)){
                        String myToast = "First Name must have a Minimum 3 characters and maximum 20 characters.";
                        Toast toast =Toast.makeText(SignUpActivity.this,myToast,Toast.LENGTH_LONG);
                        toast.show();
                        EditText text = (EditText)findViewById(R.id.FirstNameText);
                        text.setBackgroundColor(Color.parseColor("#30FF0000"));
                    }
                    if(!validateOtherFields(LastName)){
                        String myToast = "Last name must have a Minimum 3 characters and maximum 20 characters.";
                        Toast toast =Toast.makeText(SignUpActivity.this,myToast,Toast.LENGTH_LONG);
                        toast.show();
                        EditText text = (EditText)findViewById(R.id.LastNameText);
                        text.setBackgroundColor(Color.parseColor("#30FF0000"));
                    }
                    if(validatePassowrd(Password) == false){
                        EditText text = (EditText)findViewById(R.id.PasswordText);
                        text.setBackgroundColor(Color.parseColor("#30FF0000"));
                        String myToast = "Password must have a Minimum 8 characters and maximum 15 characters. It must contain at least one number, one lowercase letter, and one uppercase letter.";
                        Toast toast =Toast.makeText(SignUpActivity.this,myToast,Toast.LENGTH_LONG);
                        toast.show();
                    }
                    if(ConfirmPassword.isEmpty()){
                        Toast toast =Toast.makeText(SignUpActivity.this,"Please Confirm the password",Toast.LENGTH_LONG);
                        toast.show();
                        EditText text = (EditText)findViewById(R.id.ConfirmPasswordText);
                        text.setBackgroundColor(Color.parseColor("#30FF0000"));
                    }

                    Toast toast =Toast.makeText(SignUpActivity.this,"Please Fill all the Fields",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(Password.compareTo(ConfirmPassword) != 0){
                    Toast toast =Toast.makeText(SignUpActivity.this,"The Passwords Don't Match",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(d == true){
                    Toast toast =Toast.makeText(SignUpActivity.this,"Email Already Exists",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(!email.contains("@") || !email.contains(".")){
                    PasswordText.setBackgroundColor(Color.parseColor("#30FF0000"));
                    Toast toast =Toast.makeText(SignUpActivity.this,"Please Enter a valid Email",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    EditText text1 = (EditText)findViewById(R.id.EmailTextField);
                    text1.setBackgroundColor(Color.parseColor("#30ffffff"));

                    EditText text2 = (EditText)findViewById(R.id.FirstNameText);
                    text2.setBackgroundColor(Color.parseColor("#30ffffff"));

                    EditText text3 = (EditText)findViewById(R.id.LastNameText);
                    text3.setBackgroundColor(Color.parseColor("#30ffffff"));


                    EditText text4 = (EditText)findViewById(R.id.PasswordText);
                    text4.setBackgroundColor(Color.parseColor("#30ffffff"));

                    EditText text5 = (EditText)findViewById(R.id.ConfirmPasswordText);
                    text5.setBackgroundColor(Color.parseColor("#30ffffff"));

                    User u = new User(email,
                            FirstName,
                            LastName,
                            Password);
                    dataBaseHelper.insertCustomer(u);
                    Toast toast =Toast.makeText(SignUpActivity.this,"New User Added",Toast.LENGTH_SHORT);
                    toast.show();
                    Cursor alluser = dataBaseHelper.getAllCustomers();
                    System.out.println("PEOPLE NUMBER = " + alluser.getCount());
                }
            }
        });

        Button GoBackButton = (Button)findViewById(R.id.backButton);
        Intent i = new Intent(SignUpActivity.this,LogInScreenActivity.class);
        GoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
                finish();
            }
        });
    }

    public boolean validatePassowrd(String password){
        if(password.length() <8 || password.length() > 15) {
            return false;
        }
        if(!password.matches(".*[0-9].*") ){
            return false;
        }
        if(!password.matches(".*[A-Z].*") ){
            return false;
        }
        if(!password.matches(".*[a-z].*") ){
            return false;
        }

        return true;

    }

    public boolean validateOtherFields(String field){
        if(field.length() <3 || field.length() > 20) {
            return false;
        }
        return  true;
    }

}