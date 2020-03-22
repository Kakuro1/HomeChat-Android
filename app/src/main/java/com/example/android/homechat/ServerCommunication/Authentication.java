package com.example.android.homechat.ServerCommunication;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public abstract class Authentication {

    private static final String TAG = "Authentication";

    // Cache variables
    private static FirebaseAuth auth;
    private static FirebaseUser user;


    /**
     * Caching of the firebase authentication instance
     * @return FirebaseAuth instance
     */
    private static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    /**
     * Caching of the firebase current user
     * @return Currently signed in FirebaseUser
     */
    private static FirebaseUser getCurrentUser() {
        if (user == null) {
            user = getFirebaseAuth().getCurrentUser();
        }
        return user;
    }

    /**
     * request the unique identifier of the currently signed in user
     * @return User id or null, if the user isn't signed in
     */
    public static String getCurrentUserID() {
        if(userSignedIn()) {
            return getCurrentUser().getUid();
        }
        Log.w(TAG, "current user id requested, but no current user found");
        return null;
    }

    public static String getCurrentUsername() {
        if (userSignedIn()) {
            //TODO Display name, not UserID
            Log.w(TAG, "not yet implemented properly");
            return getCurrentUser().getUid();
        }
        Log.w(TAG, "current username requested, but no current user found");
        return null;
    }

    /**
     * is the user signed in?
     * @return true, if user is signed in
     */
    public static boolean userSignedIn() {
        if (getCurrentUser() == null)
            return false;
        else
            return true;
    }

    /**
     * gives the user of the app an anonymous account
     * @param activity activity which executes the sign-in workflow
     */
    public static void signInAnonymously(final AppCompatActivity activity) {
        getFirebaseAuth().signInAnonymously()
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInAnonymously:success");
                            user = getFirebaseAuth().getCurrentUser();
                            Database.saveUserToDatabase();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
