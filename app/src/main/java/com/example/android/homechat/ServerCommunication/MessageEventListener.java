package com.example.android.homechat.ServerCommunication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.homechat.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class MessageEventListener implements ChildEventListener {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Message msg = dataSnapshot.getValue(Message.class);
        onMsgAdded(msg);
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

    public abstract void onMsgAdded(@NonNull Message msg);
}
