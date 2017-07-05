/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDao;

import FMDto.Order;
import FMDto.Product;
import FMServiceLayer.InvalidInputException;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 *
 * @author camber
 */
public interface FMDao {

    public Order addOrder(Order order);

    public Order getOrder(int id, LocalDate date) throws InvalidInputException;

    public Order removeOrder(int id, LocalDate date);

    public List<Order> getOrdersByDate(LocalDate date)throws InvalidInputException;

    public List<Order> getOrdersByName(String name);

    public void load() throws FMDaoException;

    public void save() throws FMDaoException;

    public int getCurrentId();

    public int setCurrentId(int currentId);

    public List<Product> getAllProducts();

    public Product getProduct(String type) throws InvalidInputException;

    public Product addProduct(Product product);

    public Product removeProduct(String productName);

    public BigDecimal getState(String state) throws InvalidInputException;

    public Set<String> getAllStates();

    public String addState(String name, BigDecimal rate);

    public BigDecimal removeState(String name);

    public boolean hasProduct(String name);

    public boolean hasState(String state);

    public File logThisFile(File badFile);

}
// June 11
