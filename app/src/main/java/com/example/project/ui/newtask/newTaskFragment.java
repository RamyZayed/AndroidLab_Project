package com.example.project.ui.newtask;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.Models.Task;
import com.example.project.R;
import com.example.project.SharedPreferences.SharedPrefManager;
import com.example.project.SignUpActivity;

import java.util.Calendar;

public class newTaskFragment extends Fragment {

    private NewTaskViewModel mViewModel;

    public static newTaskFragment newInstance() {
        return new newTaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_task_fragment, container, false);


    }

    Calendar c;
    DatePickerDialog datePickerDialog;
    int chosen_Day;
    int chosen_Month;
    int chosen_Year;
    SharedPrefManager sharedPrefManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);
        Button Date = (Button) getView().findViewById(R.id.DatePicker);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month =  c.get(Calendar.MONTH ) ;
                int year =  c.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        TextView t = (TextView)getView().findViewById(R.id.DateText);
                        chosen_Day = mday;
                        chosen_Month = mmonth + 1;
                        chosen_Year = myear;
                        t.setText(chosen_Day+"/"+chosen_Month +"/"+chosen_Year);
                    }
                }, year,month,day);
            datePickerDialog.show();
            }
        });
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity(), "Project", null, 1);
        Button AddTaskButton = (Button) getView().findViewById(R.id.Add_Task_Button);
        AddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText description = (EditText)getView().findViewById(R.id.desc_text);
                String email = sharedPrefManager.readString("User_Email","NOT FOUND");
                Task t = new Task(description.getText().toString(),
                        chosen_Year,
                        chosen_Month,
                        chosen_Day,
                        email);
                dataBaseHelper.insertTask(t);
                Cursor allTasks = dataBaseHelper.getAllTasks();
                System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII  --"+allTasks.getCount());
            }
        });

    }

}