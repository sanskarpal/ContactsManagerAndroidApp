package com.example.contactsmanagerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsmanagerapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Data Source
    private ContactDatabase contactDatabase;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();

    //Adapter
    private MyAdapter myAdapter;

    //Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data Binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);

        mainBinding.setClickHandler(handlers);

        //RecyclerView
        RecyclerView recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Database
        contactDatabase = ContactDatabase.getInstance(this);

        //Initialize adapter first
        myAdapter = new MyAdapter(contactsArrayList);
        recyclerView.setAdapter(myAdapter);

        //ViewModel
        MyViewModel viewModel = new ViewModelProvider(this)
                .get(MyViewModel.class);

        //Inserting a new contact for testing
        Contacts c1 = new Contacts(1, "Jack","jack@gmail.com");
        viewModel.addNewContact(c1);

        //loading the data from ROOM DB
        viewModel.getAllContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contacts) {
                contactsArrayList.clear(); // Clear the list before adding new items

                for (Contacts c: contacts){
                    Log.v("TAGY", c.getName());
                    contactsArrayList.add(c);
                }

                myAdapter.notifyDataSetChanged();
            }
        });

        myAdapter = new MyAdapter(contactsArrayList);

        //linking the recycler view with the adapter
        recyclerView.setAdapter(myAdapter);

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //if item swipe to the left
                Contacts c = contactsArrayList.get(viewHolder.getAdapterPosition());

                viewModel.deleteContact(c);
            }
        }).attachToRecyclerView(recyclerView);

    }
}