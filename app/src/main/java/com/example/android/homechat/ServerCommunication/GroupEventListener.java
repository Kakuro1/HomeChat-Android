package com.example.android.homechat.ServerCommunication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.homechat.Group;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class GroupEventListener implements ChildEventListener {

    private static final String TAG = "GroupEventListener";

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Group g = dataSnapshot.getValue(Group.class);
        Log.d(TAG, "group is: "+g);
        onGroupAdded(g,dataSnapshot.getKey());
        return;
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        return;
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        return;
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        return;
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        return;
    }

    public abstract void onGroupAdded(@NonNull Group g,String key);
}