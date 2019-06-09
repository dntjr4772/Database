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

public class CarActivity  extends AppCompatActivity {
    public static ArrayList<CarSelectable>  al_carselectable=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        ListView listView = (ListView) findViewById(R.id.cartype_list);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MainActivity.cartype_al.size();
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
            view = getLayoutInflater().inflate(R.layout.list_car, null);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 250));

            TextView txt_cartype = (TextView) view.findViewById(R.id.car_type);
            TextView txt_carpeople = (TextView) view.findViewById(R.id.car_people);
            Button btn=(Button)view.findViewById(R.id.car_type_btn);

            txt_cartype.setText(MainActivity.cartype_al.get(i).carType);
            txt_carpeople.setText(String.valueOf(MainActivity.cartype_al.get(i).people));
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    get_car_selectable_list(i);
                }
            });
            return view;
        }
    }
    public void get_car_selectable_list(final int position) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    al_carselectable.clear();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("car_type",MainActivity.cartype_al.get(position).carType);
                    jsonParam.put("people",MainActivity.cartype_al.get(position).people);

                    URL url = new URL("http://13.124.67.34/get_car_model.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            JSONArray jobj=new JSONArray(jsonReply);
                            for(int i=0;i<jobj.length();i++){
                                String name=((JSONArray)jobj.get(i)).get(0).toString();
                                String company=((JSONArray)jobj.get(i)).get(1).toString();
                                String carcol=((JSONArray)jobj.get(i)).get(2).toString();
                                CarSelectable c=new CarSelectable(name,company,carcol);
                                al_carselectable.add(c);
                            }

                            Intent intent = new Intent(getApplicationContext(), CarSelectableActivity.class);
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
