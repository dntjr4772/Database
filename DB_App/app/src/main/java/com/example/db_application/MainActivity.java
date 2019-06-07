package com.example.db_application;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int time;
    public static int kilo;
    public static ArrayList<CarType> cartype_al=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
        // Spinner
        final Spinner start_yearSpinner = (Spinner) findViewById(R.id.start_spinner_year);
        ArrayAdapter start_yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        start_yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_yearSpinner.setAdapter(start_yearAdapter);
        final Spinner start_monthSpinner = (Spinner) findViewById(R.id.start_spinner_month);
        ArrayAdapter start_monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        start_monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_monthSpinner.setAdapter(start_monthAdapter);
        final Spinner start_daySpinner = (Spinner) findViewById(R.id.start_spinner_day);
        ArrayAdapter start_dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        start_dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_daySpinner.setAdapter(start_dayAdapter);
        final Spinner start_hourSpinner = (Spinner) findViewById(R.id.start_spinner_hour);
        ArrayAdapter start_hourAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_hour, android.R.layout.simple_spinner_item);
        start_hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_hourSpinner.setAdapter(start_hourAdapter);

        final Spinner end_yearSpinner = (Spinner) findViewById(R.id.end_spinner_year);
        ArrayAdapter end_yearAdappter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        end_yearAdappter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_yearSpinner.setAdapter(end_yearAdappter);
        final Spinner end_monthSpinner = (Spinner) findViewById(R.id.end_spinner_month);
        ArrayAdapter end_monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        end_monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_monthSpinner.setAdapter(end_monthAdapter);
        final Spinner end_daySpinner = (Spinner) findViewById(R.id.end_spinner_day);
        ArrayAdapter end_dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        end_dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_daySpinner.setAdapter(end_dayAdapter);
        final Spinner end_hourSpinner = (Spinner) findViewById(R.id.end_spinner_hour);
        ArrayAdapter end_hourAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_hour, android.R.layout.simple_spinner_item);
        end_hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_hourSpinner.setAdapter(end_hourAdapter);

        Button time_btn=(Button)findViewById(R.id.time_btn);
        Button kilo_btn=(Button)findViewById(R.id.kilo_btn);
        Button submit_btn=(Button)findViewById(R.id.submit);

        final EditText kilo_edit=(EditText)findViewById(R.id.kilo_edit);
        final TextView kilo_text=(TextView)findViewById(R.id.kilo_text);
        final TextView time_text=(TextView)findViewById(R.id.time_text);

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_year=start_yearSpinner.getSelectedItem().toString();
                String start_month=start_monthSpinner.getSelectedItem().toString();
                String start_day=start_daySpinner.getSelectedItem().toString();
                String start_hour=start_hourSpinner.getSelectedItem().toString();
                String end_year=end_yearSpinner.getSelectedItem().toString();
                String end_month=end_monthSpinner.getSelectedItem().toString();
                String end_day=end_daySpinner.getSelectedItem().toString();
                String end_hour=end_hourSpinner.getSelectedItem().toString();
                time=calculate_time(start_year,start_month,start_day,start_hour,end_year,end_month,end_day,end_hour);
                String result=start_year+"년 "+start_month+"월 "+start_day+"일 "+start_hour+"시부터 "+end_year+"년 "+end_month+"월 "+end_day+"일 "+end_hour+"시까지 "+time+"시간입니다.";
                time_text.setText(result);
            }
        });
        kilo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kilo=Integer.parseInt(kilo_edit.getText().toString());
                kilo_text.setText(kilo+"km 운행 예정입니다.");
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_kilo_time(time, kilo);
            }
        });
    }

    public int calculate_time(String s_y,String s_m, String s_d, String s_h, String e_y, String e_m, String e_d, String e_h){
        int s_year=Integer.parseInt(s_y);
        int s_month=Integer.parseInt(s_m);
        int s_day=Integer.parseInt(s_d);
        int s_hour=Integer.parseInt(s_h);
        int e_year=Integer.parseInt(e_y);
        int e_month=Integer.parseInt(e_m);
        int e_day=Integer.parseInt(e_d);
        int e_hour=Integer.parseInt(e_h);
        int result=(e_hour-s_hour)+(e_day-s_day)*24+(e_month-s_month)*24*30+(e_year-s_year)*24*30*365;
        return result;

    }
    public void set_kilo_time(final int time, final int kilo) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cartype_al.clear();
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("time",time);
                    jsonParam.put("kilo", kilo);

                    URL url = new URL("http://13.124.67.34/get_car_type.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
                            JSONArray jobj=new JSONArray(jsonReply);
                            for(int i=0;i<jobj.length();i++){
                                String type=((JSONArray)jobj.get(i)).get(0).toString();
                                int people=Integer.parseInt(((JSONArray)jobj.get(i)).get(1).toString());
                                CarType c=new CarType(type,people);
                                cartype_al.add(c);
                            }

                            Intent intent = new Intent(getApplicationContext(), CarActivity.class);

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
    public void test() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user_info", "store_info");

                    URL url = new URL("http://13.124.67.34/main.php");

                    HttpURLConnection conn = set_Connect_info(url, jsonParam);
                    if (conn.getResponseCode() == 200) {
                        InputStream response = conn.getInputStream();
                        String jsonReply = convertStreamToString(response);
                        try {
                            Log.d("test", jsonReply);
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

    public static HttpURLConnection set_Connect_info(URL url, JSONObject jsonParam) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Log.i("JSON", jsonParam.toString());
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            os.write(jsonParam.toString());

            os.flush();
            os.close();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
            while (true) {
                final String line = reader.readLine();
                if (line == null) break;
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}

