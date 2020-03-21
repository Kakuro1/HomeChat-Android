package com.example.android.homechat.ServerCommunication;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class Authentication {

    private static final String TAG = "ServerCommunication/Authentication";

    private static FirebaseAuth auth;
    private static FirebaseUser user;


    private static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    private static FirebaseUser getCurrentUser() {
        if (user == null) {
            user = auth.getCurrentUser();
            // Check if user is signed in (non-null)
            if (user == null) {
                signInAnonymously();
            }
        }
        return user;
    }

    private static void signInAnonymously() {
        //TODO
    }

}
