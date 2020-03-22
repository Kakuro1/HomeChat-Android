package com.example.android.homechat.ServerCommunication;


import android.util.Log;

import com.example.android.homechat.Message;
import com.example.android.homechat.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Database {

    private static final String TAG = "Database";

    private static FirebaseDatabase database;
    private static DatabaseReference userRef;
    private static DatabaseReference groupRef;
    private static DatabaseReference groupInfoRef;

    public static String groupID = "";

    private static MessageEventListener msgListener;
    private static GroupEventListener groupListener;
    private static UserEventListener userListener;


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
        groupRef = getFirebaseDatabase().getReference("v1/groups/"+groupID);
        return groupRef;
    }

    private static DatabaseReference getGroupListRef() {
        if (groupInfoRef == null) {
            groupInfoRef = getFirebaseDatabase().getReference("v1/groupInfos");
        }
        return groupInfoRef;
    }

    public static void saveUserToDatabase() {
        if (getUserRef() != null) {
            getUserRef().child("username").setValue(Authentication.getCurrentUsername());
        }
    }

    public static void setUsernameToDatabase(String username) {
        if (getUserRef() != null) {
            getUserRef().child("username").setValue(username);
        }
    }

    public static void setUsergroupToDatabase(String g) {
        if (getUserRef() != null) {
            getUserRef().child("homeGroupId").setValue(g);
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

    public static void attachDatabaseReadListener(GroupEventListener groupListener) {
        Database.groupListener = groupListener;
        getGroupListRef().addChildEventListener(groupListener);
        Log.d(TAG, "test_gev");
    }

    public static void attachDatabaseUserListener(UserEventListener userListener) {
        Database.userListener = userListener;
        getUserRef().addValueEventListener(userListener);
        //TODO detach
    }

    public static void detachDatabaseReadListener() {
        if (msgListener != null)
            getGroupRef().removeEventListener(msgListener);
        if (groupListener != null)
            getGroupListRef().removeEventListener(groupListener);
    }
}
