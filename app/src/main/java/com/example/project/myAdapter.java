package com.example.project;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.Models.Task;
import com.example.project.ui.today.TodayFragment;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    Task[] task;
    Context context;
    public myAdapter(Context ct, Task[] task){
        this.context  = ct;
        this.task = task;
    }


    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context, "Project", null, 1);
        holder.t.setText(task[position].getDesc());
        String d = task[position].getDay() +"/"+task[position].getMonth()+"/"+task[position].getYear();
        holder.Date.setText(d);
        if(task[position].getComplete() == 1){
            holder.v.setChecked(true);
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.v.isChecked()){
                    task[position].setComplete(1);
                    dataBaseHelper.MarkTodayTaskAsDone(task[position].getId(),1);
                    Boolean isAllDone = true;
                    for(Task t: task){
                        if(t.getComplete() == 0){
                            isAllDone = false;
                            break;
                        }
                    }
                    if(isAllDone == true){
                        int day = task[position].getDay();
                        int month = task[position].getMonth();
                        int year = task[position].getYear();

                        Toast toast =Toast.makeText(context,"All Tasks FOR "+day+"/"+month+"/"+year+" Are Done!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    task[position].setComplete(0);
                    dataBaseHelper.MarkTodayTaskAsDone(task[position].getId(),0);
                }
            }
        });


        holder.t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dataBaseHelper.changeTextOfTask(task[position].getId(),holder.t.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return task.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        EditText t;
        CheckBox v;
        TextView Date;
        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            t = itemView.findViewById(R.id.textView5);
            v = itemView.findViewById(R.id.checkBox);
            Date = itemView.findViewById(R.id.DateTextView);
        }
    }
}
