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

    Section [] sections;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
        HashMap<String, Section> allTasks = dataBaseHelper.getAllTasksHashMap();



        for (String i : allTasks.keySet()) {
            Section s  = allTasks.get(i);
            all.add(s);
        }

        sections = new Section[all.size()];

        for (int i =0 ; i < all.size();i++){
            sections[i] = all.get(i);
        }


        for(int i = 0; i< sections.length; i++){
            for(int j = 0 ; j < sections.length - i - 1;j++){
                int day1 = sections[j].getSectionItems().get(0).getDay();
                int day2 = sections[j+1].getSectionItems().get(0).getDay();

                int month1 = sections[j].getSectionItems().get(0).getMonth();
                int month2 = sections[j+1].getSectionItems().get(0).getMonth();

                int year1 = sections[j].getSectionItems().get(0).getYear();
                int year2 = sections[j+1].getSectionItems().get(0).getYear();

                if(year1 > year2){
                    Section s = sections[j];
                    sections[j] = sections[j+1];
                    sections[j+1] = s;

                }else if ( year1 == year2 && month1 > month2){
                    Section s = sections[j];
                    sections[j] = sections[j+1];
                    sections[j+1] = s;

                }else if(year1 == year2 && month1 == month2 && day1>day2){
                    Section s = sections[j];
                    sections[j] = sections[j+1];
                    sections[j+1] = s;
                }

            }

        }
        all.clear();
        for(int i = 0 ; i < sections.length ; i++){
            all.add(sections[i]);
        }

        mainRecyclerView = getActivity().findViewById(R.id.mainRecyclerView1);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this.getActivity(),all);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

    }

}