import java.util.*;

public class Dates {

    static String[] possibleMonths = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", 
    "sep", "oct", "nov", "dec"};
    static HashMap<Integer, String> months = new HashMap<Integer, String>();
    static String originalDate = "";
    static String date = "";
    static Integer numberMonth = 0;
    static Integer numberYear = 0;
    static boolean leapYear = false;

    static void fail(String why) {
        System.out.println(originalDate + " - INVALID: " + why);
    }

    static boolean format = true;

    static boolean testYear(String year) {
        if (!year.matches("\\d+")) {
            fail("Year only contains numbers");
            return false;
        }
        if (!(year.length() == 2 || year.length() == 4)) {
            fail("Year is out of range");
            return false;
        }

        numberYear = Integer.parseInt(year);
        if (year.length() == 2) {
            if (numberYear > 49) {
                numberYear += 1900;
            }
            else {
                numberYear += 2000;
            }
        }

        if (!(numberYear >= 1753 && numberYear <= 3000)) {
            fail("Year needs to be between 1753 and 3000");
            return false;
        }

        if((numberYear % 400 == 0) || ((numberYear % 4 == 0) && (numberYear % 100 != 0))) {
            leapYear = true;
        }
		else {
            leapYear = false;
        }

        return true;
    }

    static boolean testMonth(String month) {
        int index = 0;
        if (!month.matches("\\d+")) {
            month = month.toLowerCase();
            for (index = 1; index <= possibleMonths.length; index++) {
                if (possibleMonths[index-1].equals(month)) {
                    numberMonth = index;
                    return true;
                }
                else if (index == possibleMonths.length) {
                    fail("There is no such month");
                    return false;
                }
            }
        } 
        else {
            int tempNum = Integer.parseInt(month);
            if (!(tempNum >= 1 && tempNum <= 12)) {
                fail("Month needs to be between 1 and 12");
                return false;
            }
            numberMonth = tempNum;
        }
        return true;
    }

    static boolean testDay(String day) {
        if (!day.matches("\\d+")) {
            fail("Day only contains numbers");
            return false;
        }
        if (!(day.length() == 1 || day.length() == 2)) {
            fail("Day can only be 1 or 2 digits long");
            return false;
        }
        if (day.indexOf(0) != 0) {
            int numberDate = Integer.parseInt(day);
            if (numberDate >= 10) {
                date = "" + numberDate;
                if (numberMonth == 2) {
                    if (leapYear) {
                        if (numberDate > 29) {
                            fail("Date is our of range");
                            return false;
                        }
                    }
                    else {
                        if (numberDate > 28) {
                            fail("Date is our of range");
                            return false;
                        }
                    }
                }
                if (numberMonth == 1 || numberMonth == 3 || numberMonth == 5 || numberMonth == 7 || 
                numberMonth == 8 || numberMonth == 10 || numberMonth == 12) {
                    if (numberDate > 31) {
                        fail("Date is out of range");
                        return false;
                    }
                }
                else {
                    if (numberDate > 30) {
                        fail("Date is out of range");
                        return false;
                    }
                }
            }
            else {
                date = "0" + numberDate;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String dateString[] = new String[4];
        Scanner input = new Scanner(System.in);
        int i = 0;
        for (i = 0; i < possibleMonths.length; i++) {
            months.put(i + 1, possibleMonths[i]);
        }
        while (input.hasNextLine()) {
            date = "";
            format = true;
            originalDate = input.nextLine();
            dateString = originalDate.replace("-"," ").replace("/"," ").split(" ");
            for (i = 0; i < dateString.length; i++) {
                date += (dateString[i] + " ");
                if (i >= 3) {
                    fail("Date format is wrong");
                    format = false;
                }
            }

            if (!(format)) {}
            else if (!(testYear(dateString[2]))) {} 
            else if (!(testMonth(dateString[1]))) {}
            else if (!(testDay(dateString[0]))) {}
            else {
                System.out.println(date + " " + (months.get(numberMonth)).substring(0, 1).toUpperCase() + 
                (months.get(numberMonth)).substring(1) + " " + numberYear);
            }
        }
        input.close();
    }
}