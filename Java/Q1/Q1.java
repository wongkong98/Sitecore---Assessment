package Q1;

import java.util.Scanner;

public class Q1 {
    public static void question1() {
        boolean quit = false;

        Aggregation aggregation = new Aggregation();

        while(!quit) {
            System.out.println("List of shapes: ");
            System.out.println("==================================================================");

            for(int i = 0; i < aggregation.items.length; i++) {
                System.out.println((i + 1) + ". " + aggregation.items[i].toString());
            }

            System.out.println("==================================================================");
            System.out.println("1. Move");
            System.out.println("2. Rotate");
            System.out.println("3. Quit");
            System.out.print("Choice: ");

            Scanner intScanner = new Scanner(System.in);
            int choice = intScanner.nextInt();

            switch(choice) {
                case 1 -> {
                    System.out.print("Enter the movement on x-axis: ");
                    int x = intScanner.nextInt();
                    System.out.print("Enter the movement on y-axis: ");
                    int y = intScanner.nextInt();

                    aggregation.move(x, y);
                }
                case 2 -> {
                    System.out.print("Enter the degree of rotation: ");
                    int degree = intScanner.nextInt();

                    aggregation.rotate(degree);
                }
                case 3 -> {
                    quit = true;
                }
                default -> {
                    System.out.println("Error.");
                }
            }
        }
    }
}
