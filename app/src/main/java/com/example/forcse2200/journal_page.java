package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class journal_page extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<Model_Class>noteList;
    database database1;
    CoordinatorLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ForCse2200);
        setContentView(R.layout.activity_journal_page);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler);
        fab = (FloatingActionButton) findViewById(R.id.floatingbutton);
        layout = findViewById(R.id.coordinate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(journal_page.this, Edit_Note.class);
                startActivity(it);
            }
        });

        noteList = new ArrayList<>();
        database1=new database(this);
        fetch();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, journal_page.this,noteList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }
    void fetch()
    {

        Cursor cursor = database1.readAllData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(getApplicationContext(), "NO data found", Toast.LENGTH_LONG).show();
        }
        else
        {

            while (cursor.moveToNext()){
                noteList.add(new Model_Class(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diarymenu, menu);

        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView search = (SearchView) searchItem.getActionView();
        search.setQueryHint("search");
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return true;
            }
        };
        search.setOnQueryTextListener(listener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.deleteall) {
            Delete();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Delete() {
        database db = new database(journal_page.this);
        db.deleteAllNotes();
        recreate();

    }
    ItemTouchHelper.SimpleCallback callback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final Model_Class item = adapter.getList().get(position);
            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(layout,"Item Deleted",Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adapter.restoreItem(item,position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if(!(event== DISMISS_EVENT_ACTION)){
                                database db = new database(journal_page.this);
                                db.deleteSingleItem(item.getId());
                            }

                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();

        }
    };

}