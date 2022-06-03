package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.SharedPreferences.SharedPrefManager;


// Rami Zayed
// Omar Qattosh
public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        Intent LogIn = new Intent(MainActivity.this,LogInScreenActivity.class);
        Intent LoggedIn = new Intent(MainActivity.this,nav_drawr.class);
        String s = sharedPrefManager.readString("EMAIL","NotFound");
        if(s.compareTo("NotFound") == 0){
            startActivity(LogIn);
        }else{
            startActivity(LoggedIn);
        }
        //sharedPrefManager.clear("EMAIL");
        finish();
    }
}