package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBase.DataBaseHelper;
import com.example.project.Models.Task;
import com.example.project.ui.today.TodayFragment;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    List<Task> tasks;
    Context context;
    public myAdapter(Context ct, List<Task> tasks){
        this.context  = ct;
        this.tasks = tasks;
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
        //holder.t.setText(task[position].getDesc());
        holder.t.setText(tasks.get(position).getDesc());

        String d = tasks.get(position).getDay() +"/"+tasks.get(position).getMonth()+"/"+tasks.get(position).getYear();
        holder.Date.setText(d);
        if(tasks.get(position).getComplete() == 1){
            holder.v.setChecked(true);
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.v.isChecked()){
                    tasks.get(position).setComplete(1);
                    dataBaseHelper.MarkTodayTaskAsDone(tasks.get(position).getId(),1);
                    Boolean isAllDone = true;
                    for(Task t: tasks){
                        if(t.getComplete() == 0){
                            isAllDone = false;
                            break;
                        }
                    }
                    if(isAllDone == true){
                        int day = tasks.get(position).getDay();
                        int month = tasks.get(position).getMonth();
                        int year = tasks.get(position).getYear();

                        Toast toast =Toast.makeText(context,"All Tasks FOR "+day+"/"+month+"/"+year+" Are Done!",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    tasks.get(position).setComplete(0);
                    dataBaseHelper.MarkTodayTaskAsDone(tasks.get(position).getId(),0);
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dataBaseHelper.deleteTask(tasks.get(position).getId());
               tasks.remove(position);
               notifyItemRemoved(position);
            }
        });


        holder.gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gmailIntent =new Intent();
                gmailIntent.setAction(Intent.ACTION_SENDTO);
                gmailIntent.setType("message/rfc822");
                gmailIntent.setData(Uri.parse("mailto:"));
                gmailIntent.putExtra(Intent.EXTRA_EMAIL,"rajaie.imseeh@gmail.com");
                gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"To Do");
                gmailIntent.putExtra(Intent.EXTRA_TEXT,tasks.get(position).getDesc());
                context.startActivity(gmailIntent);
            }
        });

        holder.t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dataBaseHelper.changeTextOfTask(tasks.get(position).getId(),holder.t.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        EditText t;
        CheckBox v;
        TextView Date;
        ImageButton delete;
        ImageButton gmail;
        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            t = itemView.findViewById(R.id.textView5);
            v = itemView.findViewById(R.id.checkBox);
            Date = itemView.findViewById(R.id.DateTextView);
            delete = itemView.findViewById(R.id.deleteButton);
            gmail = itemView.findViewById(R.id.gmailbutton);
        }
    }
}
