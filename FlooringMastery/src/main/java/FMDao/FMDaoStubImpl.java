/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDao;

import FMDto.Order;
import FMDto.Product;
import FMServiceLayer.InvalidInputException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author camber
 */
public class FMDaoStubImpl implements FMDao {

    private Map<LocalDate, HashMap<Integer, Order>> ordersByDate = new HashMap<>();
    private Map<String, BigDecimal> stateTaxes = new HashMap<>();
    private Map<String, Product> productList = new HashMap<>();
    private int currentId;

    public FMDaoStubImpl() {
        Order testOrder = new Order();
        testOrder.setArea(new BigDecimal("2.00"));
        testOrder.setCustomerName("Camber");
        testOrder.setDate(LocalDate.of(1941, 12, 7));
        testOrder.setOrderNumberId(666);
        Product product = new Product();
        product.setName("obsidian");
        product.setLaborCostPer(new BigDecimal("3.00"));
        product.setMaterialCostPer(new BigDecimal("4.00"));
        testOrder.setProductType(product);
        testOrder.setState("KY");
        testOrder.setTaxRate(new BigDecimal("6.00"));
        testOrder.setTotalLaborCost(new BigDecimal("9.00"));
        testOrder.setTotalTax(new BigDecimal("10.00"));
        testOrder.setTotalPrice(new BigDecimal("4.00"));
        Map<Integer, Order> currentFile = new HashMap<>();
        currentFile.put(testOrder.getOrderNumberId(), testOrder);
        ordersByDate.put(testOrder.getDate(), (HashMap<Integer, Order>) currentFile);
        productList.put(testOrder.getProductType().getName(), testOrder.getProductType());
        stateTaxes.put("KY", new BigDecimal("6.00"));
        stateTaxes.put("VI", new BigDecimal("11.00"));
        currentId = 666666;

    }

    @Override
    public Order addOrder(Order order) {
        if (order.getOrderNumberId() != 0 && order.getDate() != null) {
            return order;
        } else {
            return null;
        }

    }

    @Override
    public Order getOrder(int id, LocalDate date) throws InvalidInputException {
        boolean hasDate = false;
        boolean goodEntry = false;
        boolean exists = false;
        LocalDate realDate = LocalDate.now();
        if (ordersByDate.containsKey(date)) {
            hasDate = true;
            if (hasDate && ordersByDate.get(date).containsKey(id)) {
                goodEntry = true;
            }
        }

        if (goodEntry) {
            return ordersByDate.get(date).get(id);
        } else {
            exists = false;
            for (LocalDate fileDate : ordersByDate.keySet()) {
                if (ordersByDate.get(fileDate).containsKey(id)) {
                    exists = true;
                    realDate = fileDate;
                }
            }
        }

        if (exists) {
            return ordersByDate.get(realDate).get(id);
        } else {
            throw new InvalidInputException("Order Specified does not exist");
        }
    }

    @Override
    public Order removeOrder(int id, LocalDate date) {
        if (id != 0 && date != null) {
            return new Order();
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        Map<Integer, Order> orderMap = ordersByDate.get(date);
        Collection<Order> col = orderMap.values();
        return new ArrayList(col);

    }

    @Override
    public void load() throws FMDaoException {
        return;

    }

    @Override
    public void save() throws FMDaoException {
        return;

    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList(productList.values());
    }

    @Override
    public Set<String> getAllStates() {
        return stateTaxes.keySet();
    }

    @Override
    public Product getProduct(String type) throws InvalidInputException {
        if (productList.containsKey(type)) {
            return productList.get(type);
        } else {
            throw new InvalidInputException("TWe do no carry this product");
        }
    }

    @Override
    public BigDecimal getState(String state) throws InvalidInputException {
        BigDecimal tax;
        if (stateTaxes.containsKey(state)) {
            tax = stateTaxes.get(state);
        } else {
            throw new InvalidInputException("The state is either incorrect or we do not ship to this state.");
        }
        return tax;

    }

    @Override
    public List<Order> getOrdersByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product addProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product removeProduct(String productName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addState(String name, BigDecimal rate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal removeState(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCurrentId() {
        return currentId;
    }

    @Override
    public int setCurrentId(int currentId) {
        this.currentId = currentId;
        return currentId;
    }

    @Override
    public boolean hasProduct(String name) {
        return productList.containsKey(name);

    }

    @Override
    public boolean hasState(String state) {
        if (stateTaxes.containsKey(state)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public File logThisFile(File badFile) {
        return badFile;
    }

}
// June 11