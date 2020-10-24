import java.util.ArrayList;
import java.util.Scanner;

/**
 * Etude 10
 * Arithmetic.java
 * Wayne Gee 8482738
 * Provides a method to check whether a problem is solvable in BEDMAS
 * or from left to right using depth first search.
 */
public class Arithmetic {
    /* Global identifiers */
    static ArrayList<Integer> numbers_array = new ArrayList<Integer>(); /* Values are stored here */
    static String final_operation = "";                                 /* The final operation */
    static String current_operation;                                    /* The operation */
    static Boolean end = false;                                         /* Whether to end the recursion */
    static int target;                                                  /* The target value */
    
    /* Recursive identifiers */
    static int sum;                                                     /* Sum of values */
    static int addition_num;                                            /* Addition number value */
    static int next_value;                                              /* Value of the next number */
    static int multiplication_num;                                      /* Multiplication number value */
    
    /* BEDMAS/normal recursion method  */
    public static void normal(int current_index_value, int current_number, int number) {
        if (end) {
            System.out.println(current_index_value);
            return;
        }
        if (current_index_value == numbers_array.size()) {
            sum = current_number + number;
            if (sum == target) {
                final_operation = current_operation;
                end = true;
            }
            return;
        }
        next_value = numbers_array.get(current_index_value);
        if (number < target && current_number < target) {
            current_operation += "+";
            normal((current_index_value + 1), (current_number + number), next_value);
            current_operation = current_operation.substring(0, (current_operation.length() - 1));
        }
        if (end) {return;}
        current_operation += "*";
        if (number == 0) {
            normal((current_index_value + 1), (current_number * next_value), number);
        } else {
            normal((current_index_value + 1), current_number, (number * next_value));
        }
        current_operation = current_operation.substring(0, current_operation.length()-1);
    }

    /* Left to right recursion method */
    public static void left_to_right(int current_index_value, int current_number) {
        if (end) {return;}
        /* Check if the current index is at the end of the numbers array */
        if (current_index_value == numbers_array.size()) {
            if (current_number == target) { // <- If correct
                final_operation = current_operation;
                end = true;
            }
            return;
        }
        /* Find the addition and multiplication number from addition and multiplication */
        addition_num = current_number + numbers_array.get(current_index_value);
        multiplication_num = current_number * numbers_array.get(current_index_value);
        if (addition_num <= target) { /* If addition operation is still less than target value */
            current_operation += "+";
            left_to_right(current_index_value + 1, addition_num);
            current_operation = current_operation.substring(0, current_operation.length()-1);
        }
        if (multiplication_num <= target) { /* If multiplication operation is still less than target value */
            current_operation += "*";
            left_to_right(current_index_value + 1, multiplication_num);
            current_operation = current_operation.substring(0, current_operation.length()-1);
        }
    }
    
    /* Main method */
    public static void main (String[]args) {
        String output;
        char order;
        int i;
        /* Read inputs from stdin */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            /* Clearing previous values */
            end = false;
            current_operation = "";
            numbers_array.clear();
            /* Getting values from scanner */
            Scanner numbers_to_use = new Scanner(scanner.nextLine());
            Scanner target_values_and_order = new Scanner(scanner.nextLine());
            /* Finding the target and order */
            target = target_values_and_order.nextInt();
            order = target_values_and_order.next().charAt(0);
            /* Inserting all the values into the numbers array */
            while (numbers_to_use.hasNext()) {
                numbers_array.add(numbers_to_use.nextInt());
            }
            /* Closing the scanners, so that scanner is no longer readable */
            target_values_and_order.close();
            numbers_to_use.close();
            /* Checking for base case, which is if there is only one number */
            if (numbers_array.size() == 1 && numbers_array.get(0) == target) {
                System.out.println(order + " " + target + " " + numbers_array.get(0));
            } else {
                /* Check the order of operation and then run it */
                if (order == 'L') {
                    left_to_right(1, numbers_array.get(0));
                } else if (order == 'N') {
                    normal(1, numbers_array.get(0), 0);
                }
                if (final_operation == "") {
                    System.out.println(order + " " + target + " " + "impossible");
                } else {
                    output = "";
                    for (i = 0; i < numbers_array.size(); i++) {
                        if (i != (numbers_array.size() - 1)) {
                            output += (numbers_array.get(i) + " " + final_operation.charAt(i) + " ");
                        } else {
                            output += numbers_array.get(i);
                        }
                    }
                    System.out.println(order + " " + target + " " + output);
                }
            }
        }
        scanner.close();
    }
}