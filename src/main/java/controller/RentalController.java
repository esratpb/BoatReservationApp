package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

import view.RentalMenuView;

import javax.jws.WebParam;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class RentalController {

    static Scanner scanner = new Scanner(System.in);

    // check for existing id
    JsonNode rootNode = null;

    public static void execute() throws ParseException {
        outer:
        while (true) {
            int choice = RentalMenuView.get();

            switch (choice) {
                case 1:
                    showRentals();
                    break;
                case 2:
                    addRentals();
                    break;
                case 3:
                    changeRentals();
                    break;
                case 4:
                    deleteRentals();
                    break;
                case 0:
                    break outer;
            }


        }
    }

    private static void deleteRentals() {
        Model model=ReadWriteToModel.readModel();
        System.out.println("Please enter the Id of the rental you want to delete:");
        int rentalId = scanner.nextInt();

        for (Rental rental : model.rentals){
            if(rentalId == rental.getRentalId()){
                model.rentals.remove(rental);
                System.out.println("The rental with id:" + rentalId + " has been deleted");
                break;
            }

        }
        // Java object to JSON file
       ReadWriteToModel.writeModel(model);

    }

    private static void changeRentals() {
        Model model=ReadWriteToModel.readModel();
        System.out.println("Please enter the id of the rental ");
        int rentalId = scanner.nextInt();
        System.out.println("Please enter discount rate %:");
        int discount = scanner.nextInt();
        System.out.println("IS PAYMENT DONE? Y / N ?");
        scanner.nextLine();
        String keyInput_Payment = scanner.nextLine();
        for (Rental rentalIn : model.rentals){
            if(rentalIn.getRentalId()==rentalId){
                rentalIn.setTotalPrice(rentalIn.getTotalPrice() - (rentalIn.getTotalPrice() * discount / 100));
                System.out.println((rentalIn.getTotalPrice() * discount / 100) + " Euro discount is done.");
                if (keyInput_Payment.equalsIgnoreCase("Y")){
                    rentalIn.setPaymentIsDone(true);
                } else {
                    rentalIn.setPaymentIsDone(false);
                }
                System.out.println("The reservation with id: " + rentalId + " has been updated");
            }
        }
        ReadWriteToModel.writeModel(model);
    }

    public static void addRentals() throws ParseException {

        Model model=ReadWriteToModel.readModel();
        System.out.println("Please select customer:");
        for (Customer customerIn : model.customers){
            System.out.println(customerIn);
        }
        int input = scanner.nextInt();
        Customer customer = new Customer();
        for (Customer customerIn : model.customers){
            if(customerIn.getCustomerId() == input){
                customer = customerIn;
            }
        }

        scanner.nextLine();
//        String strDateFormat = "dd-MM-yyyy"; //Date format is Specified
//        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
//        System.out.println("Enter rental date in the form dd-MM-YYYY");
//        String enteredDate = scanner.nextLine();
//        System.out.println("Enter rental start time in the form 14:30");
//        String enteredStartTime = scanner.nextLine();
//        System.out.println("Enter rental end time in the form 14:30");
//        String enteredEndTime = scanner.nextLine();
        Rental rental = null;

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        Date date1 = format.parse(enteredStartTime);
//        Date date2 = format.parse(enteredEndTime);
//        double difference =  date2.getTime() - date1.getTime();
//        double rentDuration = difference / 3_600_000;
        List<String> boatInfo = getBoatInfo();
        showAvailableBoats(boatInfo.get(0),boatInfo.get(1),boatInfo.get(2));

        System.out.println("Please select a boat to make a reservation");
        Boat boat = new Boat();

        int inputBoat = scanner.nextInt();
        for (Boat boatIn : model.boats){
            if(boatIn.getBoatId() == inputBoat){
                boat = boatIn;
            }
        }


        scanner.nextLine();
        System.out.println("Is payment received? Y OR N ?");
        String payment = scanner.nextLine();
        boolean isPaymentDone = false;
        if(payment.equalsIgnoreCase("Y")){
            isPaymentDone = true;
        } else{
            isPaymentDone = false;
        }
        try {
            String strDateFormat = "dd-MM-yyyy"; //Date format is Specified
            SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
//     public Rental(int rentalId, Date rentDate, Boat boat, Customer customer, String startTime,
//     String endTime, double rentDuration, boolean paymentIsDone) {
            LocalTime startTime2 = LocalTime.parse(boatInfo.get(1));
            LocalTime endTime2 = LocalTime.parse(boatInfo.get(2));
            double rentDuration =  endTime2.getHour() - startTime2.getHour();


                rental = new Rental(model.nextRentalId(), objSDF.parse(boatInfo.get(0)), boat, customer,
                    boatInfo.get(1), boatInfo.get(2), rentDuration,isPaymentDone);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("A new reservation is added successfully.");


            model.rentals.add(rental);

        ReadWriteToModel.writeModel(model);
    }

    public static List<String> getBoatInfo(){
        System.out.println("Enter rental date in the form dd-MM-YYYY");
        String enteredDate = scanner.nextLine();

        System.out.println("Enter rental start time in the form 14:30");
        String enteredStartTime = scanner.nextLine();

        System.out.println("Enter rental end time in the form 14:30");
        String enteredEndTime = scanner.nextLine();
        List<String> boatInfo = new ArrayList<>();
        boatInfo.add(enteredDate);
        boatInfo.add(enteredStartTime);
        boatInfo.add(enteredEndTime);

        return boatInfo;
    }

    public static void showAvailableBoats(String enteredDate,String enterStartTime, String enterEndTime) {
        Model model=ReadWriteToModel.readModel();
        Scanner scanner = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> bookedBoats = new ArrayList<>();

        // check for existing id
        JsonNode rootNode = null;
        try {


            LocalTime startTime2 = LocalTime.parse(enterStartTime);
            LocalTime endTime2 = LocalTime.parse(enterEndTime);
//            double difference =  date2.getTime() - date1.getTime();
//            double rentDuration = difference / 3_600_000;
            System.out.println("time duration of = " + MINUTES.between(startTime2, endTime2));
            rootNode = mapper.readTree(new File("src/main/java/model/model.json"));
            String[] keys = {
                    "boats", "employees", "customers", "rentals"
            };
            for (String key : keys) {
                JsonNode value = rootNode.findValue(key);
                if (key == "rentals") {
//                        System.out.printf("Key %s exists? %s --> value=%s%n", key, value != null,
//                                value == null ? null : value.fields());
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println("List of available boats");
                    System.out.println("Boat Id\tBoat Type\t\t\tSeats\tMinimum price");
                    System.out.println("-----------------------------------------------------------------------------------");

                    for (Rental rental : model.rentals) {
                        Locale locale = new Locale("nl", "NL");
                        String strDateFormat = "dd-MM-yyyy"; //Date format is Specified
                        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                        LocalTime startTime1 = LocalTime.parse(rental.getStartTime());
                        LocalTime endTime1 = LocalTime.parse(rental.getEndTime());
                        boolean chkOverlap = isOverlapping(startTime1, endTime1, startTime2, endTime2);

                        if (objSDF.parse(enteredDate).equals(rental.getRentDate())) {
                            // add booked boats to a list for later use
                            if (chkOverlap)
                                bookedBoats.add(rental.getBoat().getBoatId());
                        }
                    }
                }
            }

            if (model.boats.size() > 0) {
                for (Boat boat : model.boats) {
                    if (!bookedBoats.contains(boat.getBoatId()))
                        System.out.println(boat.getBoatId() + "\t\t" + boat.getBoatType() + "\t\t\t\t" +
                                boat.getSeats() + "\t\t" + boat.getMinimumPrice());
                }
            }


        } catch (IOException | ParseException e) {
            System.out.println("Please enter the correct information");
//            e.printStackTrace();
            return;
        }
        System.out.println("\n");
    }

    public static boolean isOverlapping(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2) {
        return !startTime1.isAfter(endTime2) && !startTime2.isAfter(endTime1);
    }

    private static void showRentals() {
       Model model=ReadWriteToModel.readModel();
        System.out.println("No\tDate\t \t Start Time\tEnd Time\tBoat Id\tBoat Type \t\t Duration \t Total Price\tCustomer Name\t Payment");
        for (Rental rental : model.rentals){
            String payment = "";
            if (rental.isPaymentIsDone()){
                payment = "DONE";
            } else{
                payment = "NOT DONE";
            }
            Locale locale = new Locale("nl", "NL");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
            String rentDate = dateFormat.format(rental.getRentDate());

            System.out.println(rental.getRentalId() + "\t" + rentDate + "\t\t" + rental.getStartTime() + "\t\t"
                    + rental.getEndTime() + "\t\t" + rental.getBoat().getBoatId() + "\t\t\t" + rental.getBoat().getBoatType() + "\t\t\t" +
                    rental.getRentDuration() + "\t\t\t" + rental.getTotalPrice() + "\t\t" + rental.getCustomer().getName() + "\t\t\t" + payment);
        }
    }
}
