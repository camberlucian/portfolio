/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

/**
 *
 * @author camber
 */
public class VMMoneyException extends Exception {

    public VMMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VMMoneyException(String message) {
        super(message);
    }

}

// june 28