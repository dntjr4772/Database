package com.example.db_application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CarDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car);

        TextView text_name=(TextView)findViewById(R.id.car_name_detail);
        TextView text_col=(TextView)findViewById(R.id.car_col_detail);
        TextView text_company=(TextView)findViewById(R.id.car_company_detail);
        TextView text_type=(TextView)findViewById(R.id.car_type_detail);
        TextView text_passenger=(TextView)findViewById(R.id.car_passenger_detail);
        TextView text_driven=(TextView)findViewById(R.id.car_driven_detail);

        text_name.setText(CarSelectableActivity.al_carDetail.get(0).name);
        text_col.setText(CarSelectableActivity.al_carDetail.get(0).carcol);
        text_company.setText(CarSelectableActivity.al_carDetail.get(0).company);
        text_type.setText(CarSelectableActivity.al_carDetail.get(0).type);
        text_passenger.setText(CarSelectableActivity.al_carDetail.get(0).passengers);
        text_driven.setText(CarSelectableActivity.al_carDetail.get(0).efficiency);
    }
}
