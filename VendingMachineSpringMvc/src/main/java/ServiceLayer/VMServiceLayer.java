/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import VMDao.Snack;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author camber
 */
public interface VMServiceLayer {

    public void addMoney(String amt) throws VMMoneyException;

    public List<Snack> giveSlots();

    public String vend(String input) throws VMVendException;

    public Changer giveChange();

    public BigDecimal getMoney();

}

// june 28
