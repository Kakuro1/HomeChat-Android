package com.example.android.homechat.ServerCommunication;


import android.util.Log;

import com.example.android.homechat.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Database {

    private static final String TAG = "Database";

    private static FirebaseDatabase database;
    private static DatabaseReference userRef;
    private static DatabaseReference groupRef;

    private static MessageEventListener msgListener;


    /**
     * Caching of the firebase database instance
     * @return FirebaseDatabase instance
     */
    private static FirebaseDatabase getFirebaseDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }
        return database;
    }

    /**
     * Caching of the firebase current user database reference (path)
     * @return Reference to the currents user data, null if no user is currently signed in
     */
    private static DatabaseReference getUserRef() {
        if(Authentication.userSignedIn()) { // set cache only if the user is signed in
            if (userRef == null) {
                userRef = getFirebaseDatabase().getReference("v1/users/" + Authentication.getCurrentUserID());
            }
        }
        else { // set cache to null if no user is signed in
            userRef = null;
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

    public static void saveUserToDatabase() {
        if (getUserRef() != null) {
            getUserRef().child("username").setValue(Authentication.getCurrentUsername());
        }
    }

    public static void saveMsgToDatabase(Message msg) {
        msg.setSender(Authentication.getCurrentUsername());
        getGroupRef().push().setValue(msg);
    }

    public static void attachDatabaseReadListener(MessageEventListener msgListener) {
        Database.msgListener = msgListener;
        getGroupRef().addChildEventListener(msgListener);
        Log.d(TAG, "test");
    }

    public static void detachDatabaseReadListener() {
        getGroupRef().removeEventListener(msgListener);
    }
}
