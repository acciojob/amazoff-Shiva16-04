package com.driver;


public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.deliveryTime=conversion(deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    private int conversion(String deliveryTime){
        String temp1="";
        int temp2=0;
        temp1+=deliveryTime.charAt(0);
        temp1+=deliveryTime.charAt(1);
        temp2=Integer.valueOf(temp1)*60;
        temp1="";
        temp1+=deliveryTime.charAt(3);
        temp1+=deliveryTime.charAt(4);
        temp2+=Integer.valueOf(temp1);
        return temp2;
    }
}
