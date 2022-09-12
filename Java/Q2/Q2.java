package Q2;

import java.util.ArrayList;
import java.util.Scanner;

public class Q2 {
    public static void question2() {
        System.out.print("InputString: ");
        Scanner stringScanner = new Scanner(System.in);
        String inputString = stringScanner.nextLine();

        int leftIndex = 0, rightIndex = inputString.length() - 1;
        ArrayList<Character> trashArray = new ArrayList<>();
        boolean result = true;

        while(leftIndex < rightIndex) {
            // Check if the left index is a special character
            if(containSpecialChar(inputString.charAt(leftIndex))){
                if(!trashArray.contains(inputString.charAt(leftIndex))){
                    trashArray.add(inputString.charAt(leftIndex));
                }
                leftIndex++;
            }

            // Check if the right index is a special character
            if(containSpecialChar(inputString.charAt(rightIndex))) {
                if(!trashArray.contains(inputString.charAt(rightIndex))){
                    trashArray.add(inputString.charAt(rightIndex));
                }
                rightIndex--;
            }

            // Check if both sides are not special character
            if(!(containSpecialChar(inputString.charAt(leftIndex))) && !(containSpecialChar(inputString.charAt(rightIndex)))) {

                // Check if two of them are equal or not, if it is not equal, it's not a palindrome
                if(inputString.charAt(leftIndex) != inputString.charAt(rightIndex)){
                    result = false;
                }
                // Both of them are equal, continue to scan the next index
                leftIndex++;
                rightIndex--;
            }
        }

        System.out.print("TrashSymbolString: ");

        for(int i = 0; i < trashArray.size(); i++) {
            if(trashArray.get(i) != null){
                System.out.print(trashArray.get(i));
            }
        }

        System.out.println("");
        System.out.println("Result: " + result);
    }

    private static boolean containSpecialChar(char character){
        return !(Character.isLetterOrDigit(character));
    }

    private static boolean contains(char[] array, char character) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == character){
                return true;
            }
        }
        return false;
    }
}
