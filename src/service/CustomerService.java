package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomerService {
    // provide a static reference
    private static CustomerService instance =  new CustomerService();
    public static CustomerService getInstance(){
        return instance;
    }

    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(String email, String firstName, String lastName){
        this.customers.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail){
        Iterator<Customer> iterator = customers.iterator();
        while(iterator.hasNext()){
            Customer cus = iterator.next();
            if(cus.getEmail().equals(customerEmail)){
                return cus;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
