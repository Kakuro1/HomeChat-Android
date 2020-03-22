package com.example.android.homechat;

import android.database.DataSetObserver;
import android.os.Bundle;

import com.example.android.homechat.ServerCommunication.Authentication;
import com.example.android.homechat.ServerCommunication.Database;
import com.example.android.homechat.ServerCommunication.MessageEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";
    private ArrayList<Message> msgList = new ArrayList<Message>();

    /**
     * onCreate for the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Log.d(TAG, "up and running!");

        if (!Authentication.userSignedIn()) {
            Authentication.signInAnonymously(this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PostMessageFragment postMessageFragment =
                (PostMessageFragment) getSupportFragmentManager().findFragmentById(R.id.post_fragment);

        final ListView messageLV = (ListView) findViewById(R.id.content_scrolling);
        System.out.println("TEST "+messageLV);

        messageLV.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return msgList.size();
            }

            @Override
            public Object getItem(int i) {
                return msgList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Message curMsg = msgList.get(i);
                if(view == null)
                    view = getLayoutInflater().inflate(R.layout.message_layout, viewGroup, false);
                TextView senderTextView = view.findViewById(R.id.senderTextView);
                senderTextView.setText(curMsg.getSender());
                TextView msgTextView = view.findViewById(R.id.msgTextView);
                msgTextView.setText(curMsg.getMsg());
                return view;
            }
        });

        MessageEventListener mev = new MessageEventListener() {
            @Override
            public void onMsgAdded(@NonNull Message msg) {
                msgList.add(msg);
                Log.d(TAG, "msg is: "+msg);
                ((BaseAdapter)messageLV.getAdapter()).notifyDataSetChanged();
            }
        };
        Database.attachDatabaseReadListener(mev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
