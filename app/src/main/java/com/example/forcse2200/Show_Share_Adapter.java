package com.example.forcse2200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Show_Share_Adapter extends RecyclerView.Adapter<Show_Share_Adapter.MyViewHolder> {

    Context context;
    ArrayList<For_Events> list;

    public Show_Share_Adapter(Context context, ArrayList<For_Events> list) {
        this.context = context;
        this.list = list;
    }

    public Show_Share_Adapter(Friends friends, ArrayList<Show_Share_Adapter> list) {
    }

    @NonNull
    @Override
    public Show_Share_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.calendar_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  Show_Share_Adapter.MyViewHolder holder, int position) {

        For_Events for_events = list.get(position);
        holder.date.setText(for_events.getLast_event());
        holder.e1.setText(for_events.getEvent1());
        holder.e2.setText(for_events.getEvent2());
        holder.e3.setText(for_events.getEvent3());
        holder.e4.setText(for_events.getEvent4());
        holder.e5.setText(for_events.getEvent5());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView e1, e2, e3, e4, e5, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            e1=itemView.findViewById(R.id.other_event1);
            e2=itemView.findViewById(R.id.other_event2);
            e3=itemView.findViewById(R.id.other_event3);
            e4=itemView.findViewById(R.id.other_event4);
            e5=itemView.findViewById(R.id.other_event5);
            date=itemView.findViewById(R.id.date);
        }
    }
}
