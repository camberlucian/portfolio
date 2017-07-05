/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMUI;

import FMDto.Order;
import FMDto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author camber
 */
public interface FMView {

    public int displayMenuAndGetInput();

    public Order createBasicOrder(List<Product> products, Set<String> states);

    public Order editOrderBasic(Order order, Set<String> states);

    public void printOrders(List<Order> orders);

    public LocalDate getDateInput();

    public LocalDate getEditOrderDate();

    public int getOrderId();

    public void displayBanner(String prompt);

    public boolean getConfirmation(String prompt);

    public void displayErrorMessage(String prompt);

    public boolean confirmOrder(Order order);

}
// June 11