package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Boat;
import model.Model;
import view.BoatMenuView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BoatController {
    static Scanner scanner = new Scanner(System.in);

    public static void execute() {

        outer:
        while (true) {
            int choice = BoatMenuView.get();
            switch (choice) {
                case 1:
                    listBoats();
                    break;
                case 2:
                    addBoat();
                    break;
                case 3:
                    updateBoat();
                    break;
                case 4:
                    deleteBoat();
                    break;
                case 0:
                    break outer;
            }


        }
    }

    public static void listBoats(){

        Model model=ReadWriteToModel.readModel();
        System.out.println("BOAT ID \t BOAT TYPE \t SEATS \t\t PRICE PER HOUR \t CHARGING TIME");
        for (Boat boatIn : model.boats) {

            System.out.println(boatIn.getBoatId() + "\t\t\t TYPE:" + boatIn.getBoatType() + "\t\t SEATS: " + boatIn.getSeats() + "\t PRICE:" + boatIn.getMinimumPrice() + "\t\t CHARGING TIME:" + boatIn.getChargingTime());
        }
    }

    public static Boat addBoat(){
        Scanner scanner = new Scanner(System.in);
        String boatType = "";
        int chargingTime = 0;
        int boatSeats = 0;
        double keyInput_boatPrice;

        System.out.println("Please Select Boat Type: \n PRESS 1 FOR ELECTRICAL BOAT \n PRESS 2 FOR KAJAK \n PRESS 3 FOR ROWING BOAT \n PRESS 4 FOR SUPBOARD");
        int keyInput_boatType = scanner.nextInt();
        if(keyInput_boatType == 1){
            boatType = "ELECTRICALBOAT";
            boatSeats = 4;
            chargingTime = 1;

        } else if(keyInput_boatType== 2){
            boatType = "KAJAK";
            boatSeats = 2;
            chargingTime = 0;

        } else if (keyInput_boatType == 3){
            boatType = "ROWINGBOAT";
            boatSeats = 4;
            chargingTime = 0;

        } else if (keyInput_boatType == 4){
            boatType = "SUPBOARD";
            boatSeats = 1;
            chargingTime = 0;
        }
        System.out.println("Please enter boat rent price per hour: ");
        keyInput_boatPrice = scanner.nextInt();
        scanner.nextLine();
        if (keyInput_boatType == 1){
            if(keyInput_boatPrice < 35){
                while (keyInput_boatPrice < 35){
                    System.out.println("The renting price should be minimum 35: ");
                    keyInput_boatPrice = scanner.nextInt();
                    scanner.nextLine();
                }
            }
        } else if (keyInput_boatType == 2) {
            if (keyInput_boatPrice < 27.5) {
                while (keyInput_boatPrice < 27.5) {
                    System.out.println("The renting price should be minimum 27.5: ");
                    keyInput_boatPrice = scanner.nextInt();
                    scanner.nextLine();
                }
            }
        } else if (keyInput_boatType == 3) {
            if(keyInput_boatPrice < 23){
                while (keyInput_boatPrice < 23){
                    System.out.println("The renting price should be minimum 23: ");
                    keyInput_boatPrice = scanner.nextInt();
                    scanner.nextLine();
                }
            }
        }
        else if (keyInput_boatType == 4) {
            if(keyInput_boatPrice < 17.5){
                while (keyInput_boatPrice < 17.5){
                    System.out.println("The renting price should be minimum 17.5: ");
                    keyInput_boatPrice = scanner.nextInt();
                    scanner.nextLine();
                }
            }
        }

        Model model=ReadWriteToModel.readModel();

        Boat newBoat = new Boat(model.nextBoatId(), boatType, boatSeats, keyInput_boatPrice, chargingTime);
        System.out.println("A new Boat is added successfully.");


            model.boats.add(newBoat);

        ReadWriteToModel.writeModel(model);
        return newBoat;
    }

    public static void updateBoat( ){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id of the Boat ");
        int boatId = scanner.nextInt();
        System.out.println("Please enter new price for the boat");
        double minimumPrice = scanner.nextDouble();
        Model model=ReadWriteToModel.readModel();
        for (Boat boatIn : model.boats){
            if(boatIn.getBoatId()==boatId){
                boatIn.setMinimumPrice(minimumPrice);
                System.out.println("The boat with id: " + boatId + " has been updated");
                break;
            }
        }

        ReadWriteToModel.writeModel(model);
    }

    public static void deleteBoat(){

        System.out.println("Please enter the Id of the customer you want to delete:");
        int boatId = scanner.nextInt();
        Model model=ReadWriteToModel.readModel();

        for (Boat boatIn : model.boats){
            if(boatId == boatIn.getBoatId()){
                model.boats.remove(boatIn);
                System.out.println("The customer with id:" + boatId + " has been deleted");
                break;
            }

        }
        // Java object to JSON file
        ReadWriteToModel.writeModel(model);
    }

}



