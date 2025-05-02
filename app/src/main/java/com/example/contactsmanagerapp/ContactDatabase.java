package com.example.contactsmanagerapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contacts.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();

    //singleton ensures only one instance of the database exists throughout the app's lifecycle optimising resource usage
    private static ContactDatabase dbInstance;

    public static synchronized ContactDatabase getInstance(Context context){

        //condition if the database is not created
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ContactDatabase.class,
                    "contacts_db"
            ).fallbackToDestructiveMigration()
                    .build();
        }

        return dbInstance;
    }
}
