import java.util.Scanner;

public class Addition {
    public static void main(String[] args) {
        Integer base = 10;
        String a = "", b = "";
        Scanner usrInput = new Scanner(System.in);
        // This part asks the the user input, assuming that I only need to ask once.
        // If he sends it back becuase it needs to be in a loop, then just add a while loop.
        System.out.println("What is the base:");
        base = usrInput.nextInt();
        if (base > 10 || base < 2) {
            System.out.println("The base is not within 2 and 10");
            System.exit(0);
        }
        System.out.println("What is the first number");
        a = usrInput.next();
        System.out.println("What is the second number");
        b = usrInput.next();
        add(a, b, base);
        usrInput.close();
    }

    public static void add(String a, String b, int base) {
        Integer[] numberA = new Integer[1001];
        Integer[] numberB = new Integer[1001];
        Integer[] resultnum = new Integer[1001];
        Integer count = 0, num = 0, carry = 0;
        String result = "";
        // I am initializing the array here.
        for (int i = 0; i <= 1000; i++) {
            numberA[i] = 0;
            numberB[i] = 0;
            resultnum[i] = 0;
        }
        // I am putting the the first and second number into arrays from back to front.
        count = a.length();
        if (count > 1000) {
            System.out.println("The first number is longer than 1000 digits");
            System.exit(0);
        }
        for (int i = 999; i >= 999 - a.length() + 1; i--) {
            numberA[i] = Character.getNumericValue(a.charAt(count-- - 1));
        }
        count = b.length();
        if (count > 1000) {
            System.out.println("The second number is longer than 1000 digits");
            System.exit(0);
        }
        for (int i = 999; i >= 999 - b.length() + 1; i--) {
            numberB[i] = Character.getNumericValue(b.charAt(count-- - 1));
        }
        // Adding the two integers according to the base.
        for (int i = 999; i >= 0 + 1; i--) {
            num = numberA[i] + numberB[i];
            if (carry > 0) {
                num += carry;
                carry = 0;
            }
            while (num > base) {
                num = num - base;
                carry++;
            }
            resultnum[i] = num;
        }
        // appends the result to a string so that you can print it.
        for (int i = 0; i <= 999; i++) {
            result += resultnum[i];
        }
        // this just removes the leading zeros.
        result = result.replaceFirst("^0+(?!$)", "");
        // prints out the sum of the two numbers.
        System.out.println("The sum of the two numbers is: " + result);

        String currentNum = "";
        for (int i = 0; i <= result.length(); i++) {
            currentNum += Character.getNumericValue(result[i]);
        }
    }
}