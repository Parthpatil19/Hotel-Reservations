package service;

import java.util.Collection;
import java.util.HashMap;

import model.Customer;

public class CustomerService {


    static HashMap<String,Customer>  customers;
    private static CustomerService instance = null;


    public static CustomerService CustomerService() {
        if (instance == null) {
            instance = new CustomerService();
            customers=new HashMap();

        }
        return instance;
    }
    public Customer getCustomer(String customerEmail) {
        if(customers.containsKey(customerEmail)) {
            return customers.get(customerEmail);
        }
        return null;

    }

    public final void addCustomer(String email, String firstName, String lastName) {
        if(customers.containsKey(email)) {
            System.out.println("Email already exists!!");
            return;
        }

        Customer newCustomer=new Customer(firstName,lastName,email);
        customers.put(email, newCustomer);
    }


    public Collection<Customer> getAllCustomers(){
        return customers.values();

    }

}
