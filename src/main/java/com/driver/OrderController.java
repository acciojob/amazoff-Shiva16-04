package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    OrderService serviceObj=new OrderService();

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        String res=serviceObj.addOrder(order);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        String res=serviceObj.addPartner(partnerId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        //This is basically assigning that order to that partnerId
        String res=serviceObj.addOrderPartnerPair(orderId, partnerId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){

        Order order=serviceObj.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){

        //deliveryPartner should contain the value given by partnerId
        DeliveryPartner deliveryPartner=serviceObj.getPartnerById(partnerId);
        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){

        //orderCount should denote the orders given by a partner-id
        Integer orderCount = serviceObj.getOrderCountByPartnerId(partnerId);
        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){

        //orders should contain a list of orders by PartnerId
        List<String> orders =serviceObj.getOrdersByPartnerId(partnerId);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        //Get all orders
        List<String> orders =serviceObj.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
//        Integer countOfOrders = 0;

        //Count of orders that have not been assigned to any DeliveryPartner
        Integer countOfOrders=serviceObj.getCountOfUnassignedOrders();
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }
    //                            ........error in method API call........{time}is missing
    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){

//        Integer countOfOrders = 0;

        //countOfOrders that are left after a particular time of a DeliveryPartner
        Integer countOfOrders= serviceObj.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
//        String time = null;

        //Return the time when that partnerId will deliver his last delivery order.
        String time= serviceObj.getLastDeliveryTimeByPartnerId(partnerId);
        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        String res= serviceObj.deletePartnerById(partnerId);
        return new ResponseEntity<>(partnerId+res, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        String res= serviceObj.deleteOrderById(orderId);
        return new ResponseEntity<>(orderId+res, HttpStatus.CREATED);
    }
}
