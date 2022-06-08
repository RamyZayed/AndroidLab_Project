package com.example.project.ui.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.LogInScreenActivity;
import com.example.project.MainRecyclerAdapter;
import com.example.project.Models.Section;
import com.example.project.Models.Task;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;
import com.example.project.myAdapter;

import java.util.ArrayList;
import java.util.List;

public class weekFragment extends Fragment {

    private WeekViewModel mViewModel;

    public static weekFragment newInstance() {
        return new weekFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.week_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    List<Section> week = new ArrayList<>();
    RecyclerView  mainRecyclerView;
    SharedPrefManager sharedPrefManager;
    private void initData() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        String email = sharedPrefManager.readString("User_Email","NO ");
        week = dataBaseHelper.getWeek(email);

        mainRecyclerView = getActivity().findViewById(R.id.mainRecyclerView);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this.getActivity(),week);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

    }

}