package com.example.contactsmanagerapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    //Available data sources for this app:
    // ROOM Database

    ExecutorService executor;

    Handler handler;

    private final ContactDAO contactDAO;

    public Repository(Application application) {


        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        this.contactDAO = contactDatabase.getContactDAO();


        //This will allow to have database operations to be executed in the background thread
        //instead of the main thread to prevent crashes and app failures

        //used for background database operations
        executor = Executors.newSingleThreadExecutor();

        //used for updating the UI
        handler = new Handler(Looper.getMainLooper());

    }


    //Methods in DAO being executed from Repository
    public void addContact(Contacts contact) {

        //Runnable: executing tasks on separate thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(contact);

            }
        });

    }

    public void deleteContact(Contacts contact) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });

    }

    public LiveData<List<Contacts>> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}
