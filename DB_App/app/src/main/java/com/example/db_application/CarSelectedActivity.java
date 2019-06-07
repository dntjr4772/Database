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

import static com.example.db_application.MainActivity.convertStreamToString;
import static com.example.db_application.MainActivity.set_Connect_info;

public class CarSelectedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_selected);

        ListView listView = (ListView) findViewById(R.id.car_list_selected);
        CarSelectedActivity.CustomAdapter customAdapter = new CarSelectedActivity.CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return CarSelectableActivity.al_carSelected.size();
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
            view = getLayoutInflater().inflate(R.layout.list_car_selected, null);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 250));

            TextView txt_car_num = (TextView) view.findViewById(R.id.car_num);
            TextView txt_car_name=(TextView)view.findViewById(R.id.car_name);
            TextView txt_car_company=(TextView)view.findViewById(R.id.car_company);
            TextView txt_car_driven=(TextView)view.findViewById(R.id.car_driven);
            TextView txt_car_location=(TextView)view.findViewById(R.id.car_location);

            Button btn_select=(Button)view.findViewById(R.id.car_selected_btn);

            txt_car_num.setText(CarSelectableActivity.al_carSelected.get(i).car_num);
            txt_car_name.setText(CarSelectableActivity.al_carSelected.get(i).name);
            txt_car_company.setText(CarSelectableActivity.al_carSelected.get(i).company);
            txt_car_driven.setText(CarSelectableActivity.al_carSelected.get(i).driven);
            txt_car_location.setText(CarSelectableActivity.al_carSelected.get(i).location);

            btn_select.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    get_calculation(i);
                }
            });
            return view;
        }
    }
    public void get_calculation(final int position) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("carname",CarSelectableActivity.al_carSelected.get(position).name);

                    URL url = new URL("http://13.124.67.34/get_car_select.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            JSONArray jobj=new JSONArray(jsonReply);
                            for(int i=0;i<jobj.length();i++){
                                String car_num=((JSONArray)jobj.get(i)).get(0).toString();
                                String name=((JSONArray)jobj.get(i)).get(1).toString();
                                String location=((JSONArray)jobj.get(i)).get(2).toString();
                                String company=((JSONArray)jobj.get(i)).get(3).toString();
                                String driven=((JSONArray)jobj.get(i)).get(4).toString();

                                CarSelected c=new CarSelected(car_num,name,location,company,driven);
                            }

                            Intent intent = new Intent(getApplicationContext(), CarSelectedActivity.class);
                            startActivityForResult(intent, 101);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("error", "Connect fail");
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}