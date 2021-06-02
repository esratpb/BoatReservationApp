package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Customer;
import model.Model;
import view.CustomerMenuView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CustomerController {
    static Model model = new Model();
    static ObjectMapper mapper = new ObjectMapper();
    static Scanner scanner = new Scanner(System.in);

    public static void execute() {
        outer:
        while (true) {
            int choice = CustomerMenuView.get();

            switch (choice) {
                case 1:
                    try {
                        listCustomers();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    addCustomer();
                    break;
                case 3:
                    break;
                case 4:
                    try {
                        deleteCustomer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    break outer;
            }


        }
    }

    private static void listCustomers() throws IOException {
        Model model=ReadWriteToModel.readModel();
        for (Customer customerIn : model.customers) {
            System.out.println(customerIn);
        }
    }

    private static void addCustomer(){
        Model model=ReadWriteToModel.readModel();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter customer name:");
        String keyInput_customer = scanner.nextLine();
        System.out.println("Please enter phone number of the customer ");
        int keyInput_phoneNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter email of the customer");
        String keyInput_email = scanner.nextLine();
        System.out.println("Please enter address details of the customer");
        String keyInput_address = scanner.nextLine();
        Customer customer = new Customer(model.nextCustomerId(), keyInput_customer, keyInput_phoneNumber, keyInput_email, keyInput_address);
        System.out.println("A new customer is added successfully.");



       ReadWriteToModel.writeModel(model);

    }

    private static void deleteCustomer() throws IOException {
        Model model= ReadWriteToModel.readModel();
        System.out.println("Please enter the Id of the customer you want to delete:");
        int customerId = scanner.nextInt();

        for (Customer customerIn : model.customers){
            if(customerId == customerIn.getCustomerId()){
                model.customers.remove(customerIn);
                System.out.println("The customer with id:" + customerId + " has been deleted");
                break;
            }

        }
        // Java object to JSON file
   ReadWriteToModel.writeModel(model);

}}

