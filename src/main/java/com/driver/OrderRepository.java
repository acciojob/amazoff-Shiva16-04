package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    private HashMap<String, Order>ordersDatabase;
    private HashMap<String, DeliveryPartner>deliveryPartnerDatabase;
    private HashMap<String, List<Order>>assignedOrdersDatabase;
    OrderRepository(){
        ordersDatabase=new HashMap<>();
        deliveryPartnerDatabase=new HashMap<>();
        assignedOrdersDatabase=new HashMap<>();
    }

    public HashMap<String, Order> getOrdersDatabase() {
        return ordersDatabase;
    }

    public HashMap<String, DeliveryPartner> getDeliveryPartnerDatabase() {
        return deliveryPartnerDatabase;
    }

    public HashMap<String, List<Order>> getAssignedOrdersDatabase() {
        return assignedOrdersDatabase;
    }

    //Method 1: addOrder
    public String addOrder(Order order){
        ordersDatabase.put(order.getId(), order);
        return "New order added successfully";
    }

    //Method 2: addPartner
    public String addPartner(String partnerId){
        //updating database 1
        DeliveryPartner newPartner=new DeliveryPartner(partnerId);
        deliveryPartnerDatabase.put(partnerId, newPartner);
        return "New delivery partner added successfully";
    }

    //Method 3: Assigning order to a partner
    public String addOrderPartnerPair(String orderId, String partnerId){
        DeliveryPartner partner=deliveryPartnerDatabase.get(partnerId);
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
//        if(getAssignedOrdersDatabase().containsKey(partnerId)==false){
//            List<Order>ordersList=new ArrayList<>();
//            ordersList.add(getOrdersDatabase().get(orderId));
//            assignedOrdersDatabase.put(partnerId,ordersList);
//        }else {
            List<Order> orders = assignedOrdersDatabase.getOrDefault(partnerId,new ArrayList<>());
            orders.add(getOrdersDatabase().get(orderId));
//        }
        return "New order-partner pair added successfully";
    }
}
