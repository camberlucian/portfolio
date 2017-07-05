/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author camber
 */
public class Changer {

    private int quarters;
    private int dimes;
    private int nickels;
    private int dollars;

    public Changer(BigDecimal change) {
        int quartersNow = 0;
        int dimesNow = 0;
        int nickelsNow = 0;
        int dollarsNow = 0;
        BigDecimal quarterD = new BigDecimal("0.25");
        BigDecimal dimeD = new BigDecimal("0.10");
        BigDecimal nickelD = new BigDecimal("0.05");
        BigDecimal dollarD = new BigDecimal("1.00");
        BigDecimal amount = change.setScale(2, RoundingMode.HALF_UP);

        while (amount.compareTo(dollarD) >= 0) {
            amount = amount.subtract(dollarD);
            dollarsNow++;
        }

        this.setDollars(dollarsNow);

        while (amount.compareTo(quarterD) >= 0) {
            amount = amount.subtract(quarterD);
            quartersNow++;
        }

        this.setQuarters(quartersNow);

        while (amount.compareTo(dimeD) >= 0) {
            amount = amount.subtract(dimeD);
            dimesNow++;
        }

        this.setDimes(dimesNow);

        while (amount.compareTo(nickelD) >= 0) {
            amount = amount.subtract(nickelD);
            nickelsNow++;
        }

        this.setNickels(nickelsNow);

    }

    public int getQuarters() {
        return quarters;
    }

    public void setQuarters(int quarters) {
        this.quarters = quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public void setDimes(int dimes) {
        this.dimes = dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public void setNickels(int nickels) {
        this.nickels = nickels;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

}

// June 28
