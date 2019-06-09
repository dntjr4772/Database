package com.example.db_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.db_application.MainActivity.convertStreamToString;
import static com.example.db_application.MainActivity.set_Connect_info;

public class ZzimActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zzim_list);

        ListView listView = (ListView) findViewById(R.id.zzim_list);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MainActivity.zzim_al.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.zzim, null);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 250));

            TextView txt_car_model = (TextView) view.findViewById(R.id.zzim_car_model);
            TextView txt_car_num = (TextView) view.findViewById(R.id.zzim_car_num);
            TextView txt_car_type = (TextView) view.findViewById(R.id.zzim_car_type);
            TextView txt_car_driven = (TextView) view.findViewById(R.id.zzim_car_driven);
            TextView txt_car_company = (TextView) view.findViewById(R.id.zzim_car_company);
            TextView txt_car_location = (TextView) view.findViewById(R.id.zzim_car_location);


            txt_car_model.setText(MainActivity.zzim_al.get(i).model);
            txt_car_num.setText(MainActivity.zzim_al.get(i).car_num);
            txt_car_type.setText(MainActivity.zzim_al.get(i).type);
            txt_car_driven.setText(MainActivity.zzim_al.get(i).distance_driven);
            if(MainActivity.zzim_al.get(i).company.equals("0"))
                txt_car_company.setText("쏘카");
            if(MainActivity.zzim_al.get(i).company.equals("1"))
                txt_car_company.setText("그린카");
            txt_car_location.setText(MainActivity.zzim_al.get(i).location);

            return view;
        }
    }
//    public void get_car_selectable_list(final int position) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        zzim_list.clear();
//                        JSONObject jsonParam = new JSONObject();
//                        jsonParam.put("car_type",MainActivity.zzim_list.get(position).carType);
//                        jsonParam.put("people",MainActivity.zzim_list.get(position).people);
//
//                        URL url = new URL("http://13.124.67.34/get_car_model.php");
//
//                        HttpURLConnection conn = set_Connect_info(url, jsonParam);
//                        if (conn.getResponseCode() == 200) {
//                            InputStream response = conn.getInputStream();
//                            String jsonReply = convertStreamToString(response);
//                            try {
//                                Log.d("test", jsonReply);
//                                JSONArray jobj=new JSONArray(jsonReply);
//                                for(int i=0;i<jobj.length();i++){
//                                    String name=((JSONArray)jobj.get(i)).get(0).toString();
//                                    String company=((JSONArray)jobj.get(i)).get(1).toString();
//                                    String carcol=((JSONArray)jobj.get(i)).get(2).toString();
//                                    CarSelectable c=new CarSelectable(name,company,carcol);
//                                    al_carselectable.add(c);
//                                }
//
//                                Intent intent = new Intent(getApplicationContext(), CarSelectableActivity.class);
//                                startActivityForResult(intent, 101);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Log.d("error", "Connect fail");
//                        }
//                        conn.disconnect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//    }
}
