/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMServiceLayer;

import FMDao.FMDaoException;
import FMDto.Order;
import FMDto.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 *
 * @author camber
 */
public interface FMServiceLayer {

    public Order completeOrder(Order order) throws InvalidInputException;

    public List<Order> getOrders(LocalDate date) throws InvalidInputException;

    public Order getSpecificOrder(LocalDate date, int Id) throws InvalidInputException;

    public void saveWork() throws FMDaoException;

    public boolean load() throws FMDaoException;

    public List<Product> getAllProducts();

    public Order addOrder(Order order);

    public Order RecalcEditedOrder(Order oldOrder, Order newOrder) throws InvalidInputException;

    public Order removeOrder(Order order);

    public Set<String> getAllStates();
}
// June 11
