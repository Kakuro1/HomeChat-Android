package com.example.android.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.location.Location;
import android.widget.EditText;

import com.example.android.homechat.ServerCommunication.Authentication;
import com.example.android.homechat.ServerCommunication.Database;
import com.example.android.homechat.ServerCommunication.GroupEventListener;
/**import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;**/

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.android.homechat.ServerCommunication.Database.attachDatabaseReadListener;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    ArrayList<Group> groupList = new ArrayList<Group>();
    ArrayList<String> groupListID = new ArrayList<String>();
    ArrayList<Group> nearGroupsList = new ArrayList<Group>();
    ArrayList<boolean[]> nearGroupsListselected = new ArrayList<boolean[]>();
    Group selectedGroup = null;
    private double myLat,myLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        if (!Authentication.userSignedIn()) {
            Authentication.signInAnonymously(this);
        }

        LocationUtils.attachLocationListener(this, new MyLocationListener());

        final ListView availableGroupsLV = (ListView) findViewById(R.id.availableGroupsLV);

        availableGroupsLV.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return nearGroupsList.size();
            }

            @Override
            public Object getItem(int i) {
                return nearGroupsList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Group curGroup = nearGroupsList.get(i);
                if(view == null)
                    view = getLayoutInflater().inflate(R.layout.group_layout, viewGroup, false);
                TextView groupNameTV = view.findViewById(R.id.groupNameTV);
                groupNameTV.setText(curGroup.getName());
                TextView groupLoctionTV = view.findViewById(R.id.groupLoctionTV);
                groupLoctionTV.setText("N: "+String.format("%3f",curGroup.getLat())+" E: "+String.format("%3f",curGroup.getLon()));
                if(nearGroupsListselected.get(i)[0])
                    view.setBackgroundColor(Color.GRAY);
                else
                    view.setBackgroundColor(Color.WHITE);
                return view;
            }
        });

        OnItemCL onitemcl = new OnItemCL();
        onitemcl.ja = this;
        availableGroupsLV.setOnItemClickListener(onitemcl);

        GroupEventListener gev = new GroupEventListener() {
            @Override
            public void onGroupAdded(@NonNull Group g,String key) {
                groupList.add(g);
                groupListID.add(key);
                Log.d(TAG, "msg is: "+g);
                ((BaseAdapter)availableGroupsLV.getAdapter()).notifyDataSetChanged();
            }
        };
        Database.attachDatabaseReadListener(gev);
    }
    protected class OnItemCL implements AdapterView.OnItemClickListener {
        JoinActivity ja = null;
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            for(int j = 0;j < nearGroupsList.size();j++){
                if(i != j)
                    nearGroupsListselected.get(j)[0] = false;
            }
            nearGroupsListselected.get(i)[0] = true;//!nearGroupsListselected.get(i)[0];
            ((BaseAdapter)((ListView)findViewById(R.id.availableGroupsLV)).getAdapter()).notifyDataSetChanged();
            Database.setUsernameToDatabase(((EditText)(findViewById(R.id.usernamePT))).getText().toString());
            Database.setUsergroupToDatabase(groupListID.get(i));
            Intent intent = new Intent(ja, ScrollingActivity.class);
            startActivity(intent);
        }
    };

    protected class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            myLat = location.getLatitude();
            myLon = location.getLongitude();
            final TextView myLocationStatusTV = (TextView) findViewById(R.id.myLocationStatusTV);
            myLocationStatusTV.setText("My location: N "+String.format("%3f",myLat)+" E"+String.format("%3f",myLon));
            nearGroupsList.clear();
            nearGroupsListselected.clear();
            System.out.println("TEST UPDATE");
            for(int i = 0;i < groupList.size();i++){
                Group g = groupList.get(i);
                float[] result = new float[3];
                Location.distanceBetween(myLon,myLat,g.getLon(),g.getLat(),result);
                if(result[0] < 50) {// result[0] is in meters
                    nearGroupsList.add(g);
                    boolean[] b = new boolean[1];
                    b[0] = false;
                    nearGroupsListselected.add(b);
                }
            }
            ((BaseAdapter)((ListView)findViewById(R.id.availableGroupsLV)).getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
