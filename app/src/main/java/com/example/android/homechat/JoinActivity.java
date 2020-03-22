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

import com.example.android.homechat.ServerCommunication.Database;
import com.example.android.homechat.ServerCommunication.GroupEventListener;

import java.util.ArrayList;

import static com.example.android.homechat.ServerCommunication.Database.attachDatabaseReadListener;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "JoinActivity";

    ArrayList<Group> groupList = new ArrayList<Group>();
    ArrayList<Group> nearGroupsList = new ArrayList<Group>();
    Group selectedGroup = null;
    private double myLat,myLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

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
                groupLoctionTV.setText("N: "+curGroup.getLat()+" E: "+curGroup.getLon());
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
            myLat = 0.1;//location.getLatitude();
            myLon = 3.4;//location.getLongitude();
            System.out.println("TEST LAT: "+myLat+" LON: "+myLon);
            nearGroupsList.clear();
            for(int i = 0;i < groupList.size();i++){
                Group g = groupList.get(i);
                float[] result = new float[3];
                Location.distanceBetween(myLon,myLat,g.getLon(),g.getLat(),result);
                System.out.println("TEST DIST= "+result[0]);
                if(result[0] < 50)
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
