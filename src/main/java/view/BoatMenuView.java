package view;

import java.util.Scanner;

public class BoatMenuView {
    public static int get() {
        System.out.println("1. list the boats");
        System.out.println("2. add a boat");
        System.out.println("3. change boat");
        System.out.println("4. delete boat");
        System.out.println("0. main menu");

        Scanner sc = new Scanner(System.in);
        while(true) {
            int choice = sc.nextInt();

            if (choice >= 1 || choice <= 5)
                return choice;
        }
    }
}
