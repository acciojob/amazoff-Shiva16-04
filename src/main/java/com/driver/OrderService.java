package com.driver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    public OrderRepository repoObj=new OrderRepository();

    //Method 1: addOrder
    public String addOrder(Order order){
        return repoObj.addOrder(order);
    }

    //Method 2: addPartner
    public String addPartner(String partnerId){
        return repoObj.addPartner(partnerId);
    }

    //Method 3: Assigning order to a partner
    public String addOrderPartnerPair(String orderId, String partnerId){
        return repoObj.addOrderPartnerPair(orderId, partnerId);
    }

    //Method 4: get Order by orderId
    public Order getOrderById(String orderId){
        HashMap<String,Order>res=repoObj.getOrdersDatabase();
        return res.get(orderId);
    }

    //Method 5: get Partner by partnerId
    public DeliveryPartner getPartnerById(String partnerId){
        HashMap<String, DeliveryPartner>res=repoObj.getDeliveryPartnerDatabase();
        return res.get(partnerId);
    }

    //Method 6: get-order-count-by-partner-id

    public int getOrderCountByPartnerId(String partnerId){
        return repoObj.getAssignedOrdersDatabase().get(partnerId).size();
    }

    //Method 7: getOrdersByPartnerId

    public List<String>getOrdersByPartnerId(String partnerId) {
        List<String>res=new ArrayList<>();
        HashMap<String, DeliveryPartner> db=repoObj.getDeliveryPartnerDatabase();
        if(db.containsKey(partnerId)==true && repoObj.getAssignedOrdersDatabase().containsKey(partnerId)==true) {
            List<Order> resDb = repoObj.getAssignedOrdersDatabase().get(partnerId);
            if (resDb.size() != 0) {
                for (Order order : resDb) {
                    res.add(order.getId());
                }
            }
            return res;
        }
        return res;
    }

    //Method 8: get-all-orders

    public List<String>getAllOrders(){
        HashMap<String, Order>ordersDb=repoObj.getOrdersDatabase();
        List<String>res=new ArrayList<>();
        for(Order order:ordersDb.values()){
            res.add(order.getId());
        }
        return res;
    }

    //Method 9: get-count-of-unassigned-orders

    public int getCountOfUnassignedOrders(){
        HashMap<String, List<Order>>assignedDb=repoObj.getAssignedOrdersDatabase();
        int overallCnt = repoObj.getOrdersDatabase().size();
        int assignedCnt = 0;
        for (List<Order> orders : assignedDb.values()) {
            assignedCnt += orders.size();
        }
        return overallCnt - assignedCnt;
    }

    //Method 10: get-count-of-orders-left-after-given-time

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int finalTime=conversion(time);
        HashMap<String, List<Order>>db=repoObj.getAssignedOrdersDatabase();
        List<Order>orders=db.get(partnerId);
        Integer cnt=0;
        for(Order order:orders){
            if(order.getDeliveryTime()>finalTime)cnt++;
        }
        return cnt;
    }

    //Method 11: get-Last-Delivery-Time-By-PartnerId
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        HashMap<String, List<Order>>db=repoObj.getAssignedOrdersDatabase();
        if(db.containsKey(partnerId)==true) {
            List<Order> orders = db.get(partnerId);
            int max = Integer.MIN_VALUE;
            for (Order order : orders) {
                if (order.getDeliveryTime() > max) max = order.getDeliveryTime();
            }
            return revConversion(max);
        }else return "";
    }

    //Method 12: delete-partner-by-id

    public String deletePartnerById(String partnerId){
        HashMap<String, DeliveryPartner>db=repoObj.getDeliveryPartnerDatabase();
        db.remove(partnerId);
        HashMap<String, List<Order>>db2=repoObj.getAssignedOrdersDatabase();
        if(db2.containsKey(partnerId)==true){
            db2.remove(partnerId);
            return " removed successfully";
        }
        return  " removed successfully";
    }

    //Method 13: delete-order-by-id
    public String deleteOrderById(String orderId){
        HashMap<String, Order>orders=repoObj.getOrdersDatabase();
        HashMap<String, List<Order>>db=repoObj.getAssignedOrdersDatabase();
        orders.remove(orderId);
        for(String partnerId:db.keySet()){
            for(Order order:db.get(partnerId)){
                if(order.getId()==orderId)db.get(partnerId).remove(order);
                return " removed successfully";
            }
        }
        return " removed successfully";
    }
    //Methods : Utility
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
    private String revConversion(int time){
        String temp1="";
        String hr="";
        String min="";
        if(time/60==0){
            hr=""+"00";
        }else{
            if(time/60<10) hr=""+0+time/60;
            else hr=""+time/60;
        }
        if(time%60==0){
            min="00";
        }else {
            if (time % 60 < 10) min = ""+0+time%60;
            else min = ""+time%60;
        }
        temp1 = "" + hr + ":" + min;
        return temp1;
    }
}