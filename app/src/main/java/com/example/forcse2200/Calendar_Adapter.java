package com.example.forcse2200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Calendar_Adapter extends RecyclerView.Adapter<Calendar_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Share_to_Users> list;

    public Calendar_Adapter(Context context, ArrayList<Share_to_Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Calendar_Adapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.shareto_users, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Calendar_Adapter.MyViewHolder holder, int position) {
        Share_to_Users share_to_users = list.get(position);

        holder.show.setText(share_to_users.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView show;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            show = itemView.findViewById(R.id.show_share_to);
        }
    }
}
