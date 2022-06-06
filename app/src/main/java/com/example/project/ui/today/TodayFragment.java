package com.example.project.ui.today;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.LogInScreenActivity;
import com.example.project.Models.Task;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;
import com.example.project.myAdapter;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    private TodayViewModel mViewModel;


    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.today_fragment, container, false);
    }

    RecyclerView recyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodayViewModel.class);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);

        ArrayList<Task> tasks = new ArrayList<Task>();
        Cursor todayTasks = dataBaseHelper.getTodayTasks();

        while (todayTasks.moveToNext()){
            Task task = new Task();
            task.setId(todayTasks.getInt(0));
            task.setDesc(todayTasks.getString(1));
            task.setYear(todayTasks.getInt(2));
            task.setMonth(todayTasks.getInt(3));
            task.setDay(todayTasks.getInt(4));
            task.setEmail(todayTasks.getString(5));
            task.setComplete(todayTasks.getInt(6));


            System.out.println("HELLO");
            System.out.println(task.toString());
            tasks.add(task);
        }

        Task arrayTasks[] = new Task[tasks.size()];
        tasks.toArray(arrayTasks);

        Boolean isAllDone = true;
        for(Task d : arrayTasks){
            if(d.getComplete() == 0){
                isAllDone = false;
                break;
            }
        }
        if(isAllDone == true){
            Toast toast =Toast.makeText(getActivity(),"All of today  tasks are DONE!!!",Toast.LENGTH_SHORT);
            toast.show();
        }
        recyclerView = getActivity().findViewById(R.id.list);
        myAdapter myAdapter = new myAdapter(getActivity(),arrayTasks);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }



}