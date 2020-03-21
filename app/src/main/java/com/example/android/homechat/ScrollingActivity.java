package com.example.android.homechat;

import android.database.DataSetObserver;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";

    /**
     * onCreate for the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PostMessageFragment postMessageFragment =
                (PostMessageFragment) getSupportFragmentManager().findFragmentById(R.id.post_fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView messageLV = (ListView) findViewById(R.id.content_scrolling);
        System.out.println("TEST "+messageLV);

        messageLV.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                System.out.println("TEST HM");
                return 1;
            }

            @Override
            public Object getItem(int i) {
                if(i == 0)
                    return "LOL";
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                System.out.println("TEST WTF");
                if(view == null)
                    view = getLayoutInflater().inflate(R.layout.message_layout, viewGroup, false);
                TextView msgTextView = view.findViewById(R.id.msgTextView);
                msgTextView.setText("LOL");
                return view;
            }
        });
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
