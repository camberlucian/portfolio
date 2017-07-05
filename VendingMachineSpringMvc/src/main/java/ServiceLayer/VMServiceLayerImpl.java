/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer;

import VMDao.Snack;
import VMDao.VMDao;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author camber
 */
public class VMServiceLayerImpl implements VMServiceLayer {

    private VMDao dao;
    private BigDecimal money;

    @Inject
    public VMServiceLayerImpl(VMDao dao) {
        this.dao = dao;
        this.money = new BigDecimal("0.00");
    }

    @Override
    public void addMoney(String amt) throws VMMoneyException {

        BigDecimal amount = null;

        try {
            amount = new BigDecimal(amt);
        } catch (NumberFormatException e) {
            throw new VMMoneyException("you need to enter a number to insert money");

        }

        if (amount.compareTo(new BigDecimal("0")) <= 0) {
            throw new VMMoneyException("You can not add negative or no money");

        }

        amount = amount.add(money);
        this.money = amount;
        return;
    }

    @Override
    public List<Snack> giveSlots() {
        return dao.getAllSnacks();
    }

    @Override
    public String vend(String input) throws VMVendException {
        Snack vendSnack = null;
        long id = 0;

        try {
            id = Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new VMVendException("you must use an ID number to get a snack");

        }

        try {
            vendSnack = dao.getSnack(id);
        } catch (NullPointerException e) {
            throw new VMVendException("This is not an available snack");

        }

        if (vendSnack.getQuantity() < 1) {
            throw new VMVendException("SOLD OUT!!!");

        }

        if (this.money.compareTo(vendSnack.getPrice()) < 0) {
            throw new VMVendException("please insert $" + vendSnack.getPrice().subtract(this.money));

        }

        vendSnack.setQuantity(vendSnack.getQuantity() - 1);

        dao.updateSnack(vendSnack);

        return "Enjoy your " + vendSnack.getName();

    }

    @Override
    public Changer giveChange() {

        Changer change = new Changer(money);
        this.money = new BigDecimal("0.00");
        return change;

    }

    public BigDecimal getMoney() {
        return money;
    }

}

// june 28
