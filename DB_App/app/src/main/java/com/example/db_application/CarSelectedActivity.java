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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.db_application.MainActivity.convertStreamToString;
import static com.example.db_application.MainActivity.set_Connect_info;

public class CarSelectedActivity extends AppCompatActivity {
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_selected);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",0);
        TextView show_time_kilo=(TextView)findViewById(R.id.show_time_kilo);
        Button btn_home=(Button)findViewById(R.id.back_home);
        Button btn_zzim=(Button)findViewById(R.id.add_zzim);
        String text="설정하신 시간은 "+MainActivity.time+"시간, 예상운행거리는 "+MainActivity.kilo+"km 입니다.";
        show_time_kilo.setText(text);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarSelectedActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        btn_zzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_zzim();
            }
        });
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
            TextView txt_car_type=(TextView)view.findViewById(R.id.car_type);
            TextView txt_car_price=(TextView)view.findViewById(R.id.car_price);
            txt_car_num.setText(CarSelectableActivity.al_carSelected.get(i).car_num);
            txt_car_name.setText(CarSelectableActivity.al_carSelected.get(i).name);
            txt_car_company.setText(CarSelectableActivity.al_carSelected.get(i).company);
            txt_car_driven.setText(CarSelectableActivity.al_carSelected.get(i).driven);
            txt_car_location.setText(CarSelectableActivity.al_carSelected.get(i).location);
            txt_car_type.setText(CarSelectableActivity.al_carSelected.get(i).type);
            txt_car_price.setText(String.valueOf(CarSelectableActivity.al_carSelected.get(i).price)+"원");

            return view;
        }
    }
    public void add_zzim(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("model",CarActivity.al_carselectable.get(position).name);
                    jsonParam.put("carcol",CarActivity.al_carselectable.get(position).carcol);
                    URL url = new URL("http://13.124.67.34/set_zzim_list.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            Intent intent = new Intent(CarSelectedActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
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