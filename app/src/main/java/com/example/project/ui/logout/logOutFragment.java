package com.example.project.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.LogInScreenActivity;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;

public class logOutFragment extends Fragment {

    private LogOutViewModel mViewModel;

    public static logOutFragment newInstance() {
        return new logOutFragment();
    }

    SharedPrefManager sharedPrefManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        sharedPrefManager.clear("EMAIL");
        Intent intent = new Intent(getActivity(), LogInScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogOutViewModel.class);
        // TODO: Use the ViewModel
    }

}