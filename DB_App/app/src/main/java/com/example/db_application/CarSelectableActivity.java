package com.example.db_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

public class CarSelectableActivity  extends AppCompatActivity {
    static ArrayList<CarSelected> al_carSelected=new ArrayList<>();
    static ArrayList<CarDetail> al_carDetail=new ArrayList<>();
    static ArrayList<String> al_price=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);

        ListView listView = (ListView) findViewById(R.id.car_list_selected);
        CarSelectableActivity.CustomAdapter customAdapter = new CarSelectableActivity.CustomAdapter();
        listView.setAdapter(customAdapter);
    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return CarActivity.al_carselectable.size();
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
            view = getLayoutInflater().inflate(R.layout.list_car_selectable, null);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 250));

            TextView txt_car_name = (TextView) view.findViewById(R.id.car_name);
            TextView txt_car_company=(TextView)view.findViewById(R.id.car_company);
            TextView txt_car_col=(TextView)view.findViewById(R.id.car_col);
            Button btn_select=(Button)view.findViewById(R.id.car_selectable_btn);
            Button btn_spec=(Button)view.findViewById(R.id.car_spec_btn);

            txt_car_name.setText(CarActivity.al_carselectable.get(i).name);
            txt_car_company.setText(CarActivity.al_carselectable.get(i).company);
            txt_car_col.setText(CarActivity.al_carselectable.get(i).carcol);
            btn_select.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    get_car_price(i);
                    get_car_selected_list(i);
                }
            });
            btn_spec.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    get_car_specification(i);
                }
            });
            return view;
        }
    }
    public void get_car_selected_list(final int position) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    al_carSelected.clear();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("carname",CarActivity.al_carselectable.get(position).name);

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
                                String type=((JSONArray)jobj.get(i)).get(5).toString();

                                if(company.equals("0"))
                                    company="쏘카";
                                else if(company.equals("1"))
                                    company="그린카";

                                CarSelected c=new CarSelected(car_num,name,location,company,driven,type);
                                c.get_price(company);
                                al_carSelected.add(c);
                            }

                            Intent intent = new Intent(getApplicationContext(), CarSelectedActivity.class);
                            intent.putExtra("position",position);
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
    public void get_car_price(final int position) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    al_price.clear();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("model",CarActivity.al_carselectable.get(position).name);
                    jsonParam.put("carcol",CarActivity.al_carselectable.get(position).carcol);

                    URL url = new URL("http://13.124.67.34/get_price_info.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            JSONArray jobj=new JSONArray(jsonReply);
                            for(int i=0;i<jobj.length();i++){
                                String id=((JSONArray)jobj.get(i)).get(0).toString();
                                String kilo=((JSONArray)jobj.get(i)).get(1).toString();
                                String time=((JSONArray)jobj.get(i)).get(2).toString();
                                al_price.add(id);
                                al_price.add(kilo);
                                al_price.add(time);

                            }

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
    public void get_car_specification(final int position) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    al_carDetail.clear();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("carname",CarActivity.al_carselectable.get(position).name);
                    URL url = new URL("http://13.124.67.34/get_car_detail.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            JSONArray jobj=new JSONArray(jsonReply);
                            for(int i=0;i<jobj.length();i++){
                                String name=((JSONArray)jobj.get(i)).get(0).toString();
                                String carcol=((JSONArray)jobj.get(i)).get(1).toString();
                                String type=((JSONArray)jobj.get(i)).get(2).toString();
                                String company=((JSONArray)jobj.get(i)).get(3).toString();
                                String passengers=((JSONArray)jobj.get(i)).get(4).toString();
                                String efficiency=((JSONArray)jobj.get(i)).get(5).toString();

                                CarDetail c=new CarDetail(name,carcol,type,company,passengers,efficiency);
                                al_carDetail.add(c);
                            }

                            Intent intent = new Intent(getApplicationContext(), CarDetailActivity.class);
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
