/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMServiceLayer;

import FMDao.ConfigDao;
import FMDao.FMDao;
import FMDao.FMDaoException;
import FMDto.Order;
import FMDto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 *
 * @author camber
 */
public class FMServiceLayerImpl implements FMServiceLayer {

    private FMDao dao;
    private ConfigDao config;

    public FMServiceLayerImpl(FMDao dao, ConfigDao config) {
        this.dao = dao;
        this.config = config;

    }

    @Override
    public Order completeOrder(Order order) throws InvalidInputException {
        BigDecimal stateTaxRate;
        int Id = dao.getCurrentId() + 1;
        BigDecimal hundred = new BigDecimal("100");
        Product product;
        BigDecimal totalLabor;
        BigDecimal totalTax;
        BigDecimal totalMaterial;
        BigDecimal totalPrice;
        BigDecimal area = order.getArea();

        //try to read the state input
        if (dao.hasState(order.getState())) {
            stateTaxRate = dao.getState(order.getState()).divide(hundred);
        } else {
            stateTaxRate = order.getTaxRate();
        }

        // try to read the product input
        if (dao.hasProduct(order.getProductType().getName())) {
            product = dao.getProduct(order.getProductType().getName());
        } else {
            product = order.getProductType();
        }

        totalLabor = area.multiply(product.getLaborCostPer());
        totalMaterial = order.getArea().multiply(product.getMaterialCostPer());
        totalTax = stateTaxRate.multiply(totalLabor.add(totalMaterial));
        totalPrice = totalTax.add(totalLabor.add(totalMaterial));

        order.setTaxRate(stateTaxRate);
        order.setOrderNumberId(Id);
        dao.setCurrentId(Id);
        order.setProductType(product);
        order.setTotalLaborCost(totalLabor.setScale(2, RoundingMode.HALF_UP));
        order.setTotalTax(totalTax.setScale(2, RoundingMode.HALF_UP));
        order.setTotalMaterialCost(totalMaterial.setScale(2, RoundingMode.HALF_UP));
        order.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP));

        return order;

    }

    @Override
    public List<Order> getOrders(LocalDate date) throws InvalidInputException {
        List<Order> list = null;

        try {
            list = dao.getOrdersByDate(date);
        } catch (NullPointerException e) {
            throw new InvalidInputException("ERROR: There are not orders under this date");
        }

        return list;

    }

    @Override
    public void saveWork() throws FMDaoException {
        dao.save();
    }

    @Override
    public boolean load() throws FMDaoException {
        dao.load();
        return config.readConfig();
    }

    @Override
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    @Override
    public Order addOrder(Order order) {
        dao.addOrder(order);
        return order;
    }

    @Override
    public Order RecalcEditedOrder(Order oldOrder, Order newOrder) throws InvalidInputException {
        // get the tax rate, the area, the product costs for labor and square footage and recalculate the total
        //material cost, total labor cost, total tax and total price

        // if the values are different the just use what the order has
        // if the values are the same and the product is the same then ust use what the order has
        //if the values are the same and the product is different and valid then get the new product info
        // if the values are the same and the product is different and not valid then use what the order has
        if (newOrder.getOrderNumberId() == 0) {
            int Id = dao.getCurrentId() + 1;
            newOrder.setOrderNumberId(Id);
        }

        boolean sameProduct = newOrder.getProductType().getName().equals(oldOrder.getProductType().getName());
        boolean sameValues = false;
        if (newOrder.getProductType().getMaterialCostPer().equals(oldOrder.getProductType().getMaterialCostPer())
                && newOrder.getProductType().getLaborCostPer().equals(oldOrder.getProductType().getLaborCostPer())) {
            sameValues = true;
        }

        if (sameValues && !sameProduct) {
            if (dao.hasProduct(newOrder.getProductType().getName())) {
                newOrder.setProductType(dao.getProduct(newOrder.getProductType().getName()));
            }
        }

        newOrder.setTotalMaterialCost(newOrder.getArea().multiply(newOrder.getProductType().getMaterialCostPer()).setScale(2, RoundingMode.HALF_UP));
        newOrder.setTotalLaborCost(newOrder.getArea().multiply(newOrder.getProductType().getLaborCostPer()).setScale(2, RoundingMode.HALF_UP));
        BigDecimal subtotal = newOrder.getTotalLaborCost().add(newOrder.getTotalMaterialCost());
        boolean sameState = newOrder.getState().equals(oldOrder.getState());
        boolean sameTax = newOrder.getTaxRate().equals(oldOrder.getTaxRate());
        BigDecimal hundred = new BigDecimal("100");

        // if they didnt change tax or state then tax rate is fine, recalc
        // if they changed the state to a valid state but not the tax then get the new states tax rate
        // if they changed teh state to a non valid state then use the new orders tax rate
        // if they changed the state to a valid state but not the tax rate then the tax rate is fine
        if (sameTax) {
            if (sameState) {
                //recalc with new order tax rate
                newOrder.setTotalTax(subtotal.multiply(newOrder.getTaxRate().divide(hundred)).setScale(2, RoundingMode.HALF_UP));
            } else if (dao.getAllStates().contains(newOrder.getState())) {
                // get new state tax and recalc
                newOrder.setTaxRate(dao.getState(newOrder.getState()));
                newOrder.setTotalTax(subtotal.multiply(newOrder.getTaxRate().divide(hundred)).setScale(2, RoundingMode.HALF_UP));
            } else {
                //use new order's tax rate
                newOrder.setTotalTax(subtotal.multiply(newOrder.getTaxRate().divide(hundred)).setScale(2, RoundingMode.HALF_UP));
            }
        } else {
            //recalc with orders tax
            newOrder.setTotalTax(subtotal.multiply(newOrder.getTaxRate().divide(hundred)).setScale(2, RoundingMode.HALF_UP));
        }

        newOrder.setTotalTax(subtotal.add(newOrder.getTotalTax()).setScale(2, RoundingMode.HALF_UP));

        return newOrder;

    }

    @Override
    public Order getSpecificOrder(LocalDate date, int Id) throws InvalidInputException {
        return dao.getOrder(Id, date);
    }

    @Override
    public Set<String> getAllStates() {
        return dao.getAllStates();
    }

    @Override
    public Order removeOrder(Order order) {
        Order deleteOrder = order;
        dao.removeOrder(order.getOrderNumberId(), order.getDate());
        return deleteOrder;
    }

}
// June 11
