package com.example.project.ui.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.project.SharedPreferences.SharedPrefManager;
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
    int startDay = 0;
    int startMonth = 0;
    int startYear = 0;
    int endDay = 0;
    int endMonth = 0;
    int endYear = 0;
    RecyclerView mainRecyclerView;
    SharedPrefManager sharedPrefManager;
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
                TextView end = (TextView)getView().findViewById(R.id.endText);
                TextView start = (TextView)getView().findViewById(R.id.startText);
                if(startDay == 0 || endDay == 0) {
                    start.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shakeanimation));
                    end.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shakeanimation));
                    Toast toast = Toast.makeText(getActivity(), "Please Select a start or an end date", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
                sharedPrefManager = SharedPrefManager.getInstance(getActivity());
                String email = sharedPrefManager.readString("User_Email","NO ");
                List<Section> all =  dataBaseHelper.getTasksBetween(email,startDay,startMonth,startYear,endDay,endMonth,endYear);

                if(all.size() == 0){
                    Toast toast =Toast.makeText(getActivity(),"There is no Todos for you in this range",Toast.LENGTH_SHORT);
                    toast.show();
                }
                System.out.println(startDay);


                    System.out.println("Am here");
                    mainRecyclerView = getActivity().findViewById(R.id.mainRecyclerView1);
                    MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getActivity(),all);
                    mainRecyclerView.setAdapter(mainRecyclerAdapter);
                    mainRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                }

            }
        });




    }

}