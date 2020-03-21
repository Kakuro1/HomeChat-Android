package com.example.android.homechat.ServerCommunication;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Database {

    private static final String TAG = "ServerCommunication/Database";

    private static FirebaseDatabase database;
    private static DatabaseReference userRef;
    private static DatabaseReference groupRef;


    private static FirebaseDatabase getFirebaseDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }
        return database;
    }

    private static DatabaseReference getUserRef() {
        if (userRef == null) {
            //TODO
            userRef = getFirebaseDatabase().getReference("v1/users/user1");
        }
        return userRef;
    }

    private static DatabaseReference getGroupRef() {
        if (groupRef == null) {
            //TODO
            groupRef = getFirebaseDatabase().getReference("v1/groups/group1");
        }
        return groupRef;
    }

    public static void attachDatabaseReadListener(MessageEventListener msgListener) {
        getGroupRef().addChildEventListener(msgListener);
    }
}
