/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMUI;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author camber
 */
public class UserIOImpl implements UserIO {

    private Scanner scan = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();
        boolean isValid = false;
        while (!isValid) {
            try {
                Double thing = Double.parseDouble(input);
                isValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a double");
                isValid = false;
                input = scan.nextLine();
            }
        }
        Double thing = Double.parseDouble(input);
        return thing;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        String input = scan.nextLine();

        double thing = 500.00;
        boolean isValid = false;
        while (!isValid) {
            try {
                thing = Double.parseDouble(input);
                if (thing >= min && thing <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    input = scan.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a double");
                isValid = false;
                input = scan.nextLine();
            }
        }

        return thing;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();

        boolean isValid = false;
        while (!isValid) {
            try {
                float thing = Float.parseFloat(input);
                isValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a float");
                isValid = false;
                input = scan.nextLine();
            }
        }
        float thing = Float.parseFloat(input);
        return thing;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        String input = scan.nextLine();

        float thing = 500.00f;
        boolean isValid = false;
        while (!isValid) {
            try {
                thing = Float.parseFloat(input);
                if (thing >= min && thing <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    input = scan.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a float");
                isValid = false;
                input = scan.nextLine();
            }
        }

        return thing;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();
        boolean isValid = false;
        while (!isValid) {
            try {
                int thing = Integer.parseInt(input);
                isValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
                isValid = false;
                input = scan.nextLine();
            }
        }
        int thing = Integer.parseInt(input);
        return thing;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        String input = scan.nextLine();

        int thing = 500;
        boolean isValid = false;
        while (!isValid) {
            try {
                thing = Integer.parseInt(input);
                if (thing >= min && thing <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    input = scan.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
                isValid = false;
                input = scan.nextLine();
            }
        }

        return thing;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();
        boolean isValid = false;
        while (!isValid) {
            try {
                long thing = Long.parseLong(input);
                isValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a long");
                isValid = false;
                input = scan.nextLine();
            }
        }
        long thing = Long.parseLong(input);
        return thing;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        String input = scan.nextLine();

        long thing = 500;
        boolean isValid = false;
        while (!isValid) {
            try {
                thing = Long.parseLong(input);
                if (thing >= min && thing <= max) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    input = scan.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a long");
                isValid = false;
                input = scan.nextLine();
            }
        }

        return thing;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scan.nextLine();
    }

    @Override
    public BigDecimal readBigD(String prompt) {
        System.out.println(prompt);
        String input = scan.nextLine();
        boolean isValid = false;
        while (!isValid) {
            try {
                Double thing = Double.parseDouble(input);
                isValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number amount");
                isValid = false;
                input = scan.nextLine();
            }
        }
        BigDecimal thingD = new BigDecimal(input);

        return thingD;
    }

    @Override
    public LocalDate readDate(String prompt, String format) {
        System.out.println(prompt);
        String input = scan.nextLine();
        boolean isValid = false;
        LocalDate date = null;
        while (!isValid) {
            try {
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern(format));
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a proper date in the form " + format);
                isValid = false;
                input = scan.nextLine();
            }
        }

        return date;

    }

    @Override
    public boolean readYesorNo(String prompt) {
        System.out.println(prompt + "respond with y or n");
        boolean confirmed = false;
        boolean isValid = false;
        String input = scan.nextLine();
        while (!isValid) {
            if (input.toLowerCase().equals("y")) {
                isValid = true;
                confirmed = true;
            } else if (input.toLowerCase().equals("n")) {
                isValid = true;
            } else {
                System.out.println("Please enter y or n");
                input = scan.nextLine();
            }

        }

        return confirmed;
    }

}
// June 11