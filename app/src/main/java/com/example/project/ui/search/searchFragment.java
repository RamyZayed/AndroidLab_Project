package com.example.project.ui.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.LogInScreenActivity;
import com.example.project.MainRecyclerAdapter;
import com.example.project.Models.Section;
import com.example.project.R;
import com.example.project.SignUpActivity;

import java.util.Calendar;
import java.util.List;

public class searchFragment extends Fragment {

    private SearchViewModel mViewModel;

    public static searchFragment newInstance() {
        return new searchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }


    Calendar c;
    DatePickerDialog datePickerDialog;
    int startDay;
    int startMonth;
    int startYear;
    int endDay;
    int endMonth;
    int endYear;
    RecyclerView mainRecyclerView;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button startButton = (Button) getView().findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month =  c.get(Calendar.MONTH ) ;
                int year =  c.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        TextView t = (TextView)getView().findViewById(R.id.startText);
                        startDay = mday;
                        startMonth = mmonth + 1;
                        startYear = myear;
                        t.setText(startDay+"/"+startMonth +"/"+startYear);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });



        Button endButton = (Button) getView().findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month =  c.get(Calendar.MONTH ) ;
                int year =  c.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        TextView t = (TextView)getView().findViewById(R.id.endText);
                        endDay = mday;
                        endMonth = mmonth + 1;
                        endYear = myear;
                        t.setText(endDay+"/"+endMonth +"/"+endYear);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });

        Button searchButton = (Button)getActivity().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
                List<Section> all =  dataBaseHelper.getTasksBetween(startDay,startMonth,startYear,endDay,endMonth,endYear);
                //List<Section> all =  dataBaseHelper.getTasksBetween(6,6,2022,30 ,6,2022);

                mainRecyclerView = getActivity().findViewById(R.id.mainRecyclerView1);
                MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(),all);
                mainRecyclerView.setAdapter(mainRecyclerAdapter);
                mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
            }
        });




    }

}