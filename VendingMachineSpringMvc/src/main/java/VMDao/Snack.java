/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VMDao;

import java.math.BigDecimal;

/**
 *
 * @author camber
 */
public class Snack {

    private String name;
    private long snackId;
    private BigDecimal price;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSnackId() {
        return snackId;
    }

    public void setSnackId(long snackId) {
        this.snackId = snackId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

// june 28
