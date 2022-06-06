package com.example.project.ui.all;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.MainRecyclerAdapter;
import com.example.project.Models.Section;
import com.example.project.Models.Task;
import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class allFragment extends Fragment {

    private AllViewModel mViewModel;

    public static allFragment newInstance() {
        return new allFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_fragment, container, false);
    }


    List<Section> all = new ArrayList<>();
    RecyclerView mainRecyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
        HashMap<String, Section> allTasks = dataBaseHelper.getAllTasksHashMap();


        for (String i : allTasks.keySet()) {
            Section s  = allTasks.get(i);
            all.add(s);
        }

        mainRecyclerView = getActivity().findViewById(R.id.mainRecyclerView1);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this.getActivity(),all);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

    }

}