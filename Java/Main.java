import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner intScanner = new Scanner(System.in);

        System.out.print("Choice: ");
        int choice = intScanner.nextInt();

        switch (choice) {
            case 1 -> Q1.Q1.question1();
            case 2 -> Q2.Q2.question2();
            case 3 -> Q3.Q3.question3();
        }
    }
}
