package com.example.project.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.LogInScreenActivity;
import com.example.project.Models.User;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;

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
                dataBaseHelper.updateProfile(EmailText.getText().toString(),
                        FirstName.getText().toString(),
                        LastName.getText().toString(),
                        password.getText().toString());
            }
        });
    }

}