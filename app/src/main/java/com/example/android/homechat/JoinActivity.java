package com.example.android.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.android.homechat.ServerCommunication.Authentication;
import com.example.android.homechat.ServerCommunication.Database;
import com.example.android.homechat.ServerCommunication.GroupEventListener;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.android.homechat.ServerCommunication.Database.attachDatabaseReadListener;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    ArrayList<Group> groupList = new ArrayList<Group>();
    ArrayList<Group> nearGroupsList = new ArrayList<Group>();
    Group selectedGroup = null;
    private double myLat,myLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        if (!Authentication.userSignedIn()) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    //new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.AnonymousBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
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
                return view;
            }
        });

        availableGroupsLV.setOnItemClickListener(new OnItemCL());

        GroupEventListener gev = new GroupEventListener() {
            @Override
            public void onGroupAdded(@NonNull Group g) {
                groupList.add(g);
                Log.d(TAG, "msg is: "+g);
                ((BaseAdapter)availableGroupsLV.getAdapter()).notifyDataSetChanged();
            }
        };
        Database.attachDatabaseReadListener(gev);
    }
    protected class OnItemCL implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
            for(int i = 0;i < groupList.size();i++){
                Group g = groupList.get(i);
                float[] result = new float[3];
                Location.distanceBetween(myLon,myLat,g.getLon(),g.getLat(),result);
                if(result[0] < 50)// result[0] is in meters
                    nearGroupsList.add(g);
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
