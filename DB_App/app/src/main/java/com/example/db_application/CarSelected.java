package com.example.db_application;

public class CarSelected {
    String car_num;
    String name;
    String location;
    String company;
    String driven;
    String type;
    int price=0;
    public CarSelected(String car_num, String name, String location, String company,String driven, String type) {
        this.car_num=car_num;
        this.name=name;
        this.location=location;
        this.company=company;
        this.driven=driven;
        this.type=type;
    }
    public void get_price(String company){
        if(company.equals("쏘카")){
            this.price=MainActivity.kilo*Integer.parseInt(CarSelectableActivity.al_price.get(1))+MainActivity.time*Integer.parseInt(CarSelectableActivity.al_price.get(2));

        }else if(company.equals("그린카")){
            this.price=MainActivity.kilo*Integer.parseInt(CarSelectableActivity.al_price.get(4))+MainActivity.time*Integer.parseInt(CarSelectableActivity.al_price.get(5));

        }

    }
}
