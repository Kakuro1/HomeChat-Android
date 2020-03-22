package com.example.android.homechat.ServerCommunication;

import androidx.annotation.NonNull;

import com.example.android.homechat.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class UserEventListener implements ValueEventListener {

    private static final String TAG = "UserEventListener";

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        onUserChange(user);
        return;
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        return;
    }

    public abstract void onUserChange(@NonNull User user);
}
