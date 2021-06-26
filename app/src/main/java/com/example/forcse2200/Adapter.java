package com.example.forcse2200;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    Context context;//eth in a page, full screen's eth, current state of the application
    Activity activity;
    List<Model_Class> noteList, searcher;

    public Adapter(Context context, Activity activity, List<Model_Class> list) {
        this.context = context;
        this.activity = activity;
        this.noteList = list;
        this.searcher= new ArrayList<>(noteList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(noteList.get(position).getTitle());
        holder.description.setText(noteList.get(position).getDescription());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,updata_notes.class);
                intent.putExtra("title",noteList.get(position).getTitle());
                intent.putExtra("description",noteList.get(position).getDescription());
                intent.putExtra("id",noteList.get(position).getId());

                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public Filter getFilter() {

        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model_Class>filteredList = new ArrayList<>();

            if(constraint ==null || constraint.length()==0)
            {
                filteredList.addAll(searcher);
            }else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Model_Class item:searcher)
                {
                    if(item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }

                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            noteList.clear();
            noteList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView title, description;
        RelativeLayout layout;

        public MyViewHolder(@NonNull  View itemView) {

            super(itemView);

            title=itemView.findViewById(R.id.show_title);//title of recycler view
            description=itemView.findViewById(R.id.show_description);

            layout=itemView.findViewById(R.id.relative);

        }
    }
    public List<Model_Class> getList ()
    {

        return noteList;
    }
    public  void removeItem(int position)
    {
        noteList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Model_Class item, int position)
    {
        noteList.add(position,item);
        notifyItemInserted(position);
    }

}
