package com.example.project.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.LogInScreenActivity;
import com.example.project.Models.User;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;
import com.example.project.SignUpActivity;

public class profileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static profileFragment newInstance() {
        return new profileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    SharedPrefManager sharedPrefManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
        String email = sharedPrefManager.readString("User_Email","NotFound");

        User user = dataBaseHelper.getUser(email);

        EditText EmailText = (EditText)getActivity().findViewById(R.id.emailProfile);
        EditText FirstName = (EditText)getActivity().findViewById(R.id.firstNameProfile);
        EditText LastName = (EditText)getActivity().findViewById(R.id.lastNameProfile);
        EditText password = (EditText)getActivity().findViewById(R.id.passwordProfile);

        EmailText.setText(user.getEmail());
        FirstName.setText(user.getFirstName());
        LastName.setText(user.getLastName());
        password.setText(user.getPassword());

        Button changes = (Button)getActivity().findViewById(R.id.changesButton);
        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                String first = FirstName.getText().toString();
                String last = LastName.getText().toString();
                String Email = EmailText.getText().toString();
                if(!EmailText.getText().toString().contains("@") || !EmailText.getText().toString().contains(".")){
                    Toast toast =Toast.makeText(getActivity(),"Please Enter a valid Email",Toast.LENGTH_SHORT);
                    toast.show();
                    EmailText.setBackgroundColor(Color.parseColor("#30FF0000"));
                }else if(!validatePassowrd(pass)){

                    password.setBackgroundColor(Color.parseColor("#30FF0000"));
                    String myToast = "Password must have a Minimum 8 characters and maximum 15 characters. It must contain at least one number, one lowercase letter, and one uppercase letter.";
                    Toast toast =Toast.makeText(getActivity(),myToast,Toast.LENGTH_LONG);
                    toast.show();

                }else if(!validateOtherFields(first)){

                    FirstName.setBackgroundColor(Color.parseColor("#30FF0000"));
                    String myToast = "First Name must have a Minimum 3 characters and maximum 20 characters";
                    Toast toast =Toast.makeText(getActivity(),myToast,Toast.LENGTH_LONG);
                    toast.show();
                }else if(!validateOtherFields(last)){
                    LastName.setBackgroundColor(Color.parseColor("#30FF0000"));
                    String myToast = "Last Name must have a Minimum 3 characters and maximum 20 characters";
                    Toast toast =Toast.makeText(getActivity(),myToast,Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    EmailText.setBackgroundColor(Color.parseColor("#30FFFFFF"));
                    password.setBackgroundColor(Color.parseColor("#30FFFFFF"));
                    FirstName.setBackgroundColor(Color.parseColor("#30FFFFFF"));
                    LastName.setBackgroundColor(Color.parseColor("#30FFFFFF"));
                    dataBaseHelper.updateProfile(EmailText.getText().toString(),
                            FirstName.getText().toString(),
                            LastName.getText().toString(),
                            password.getText().toString());
                }

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