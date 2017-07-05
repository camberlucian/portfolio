/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author camber
 */
public class Order {

    private int orderNumberId;
    private String customerName;
    private LocalDate date;
    private String state;
    private BigDecimal taxRate;
    private Product productType;
    private BigDecimal area;
    private BigDecimal totalPrice;
    private BigDecimal totalMaterialCost;
    private BigDecimal totalLaborCost;
    private BigDecimal totalTax;

    public int getOrderNumberId() {
        return orderNumberId;
    }

    public void setOrderNumberId(int orderNumberId) {
        this.orderNumberId = orderNumberId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal tax) {
        this.taxRate = tax;
    }

    public Product getProductType() {
        return productType;
    }

    public void setProductType(Product productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    @Override
    public String toString() {
        return "orderNumberId: " + orderNumberId + ""
                + "\n Customer Name: " + customerName
                + "\n date: " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                + "\n state: " + state
                + "\n Tax Rate: " + taxRate
                + "\n Product Type: " + productType.getName()
                + "\n material cost per square foot: " + productType.getMaterialCostPer()
                + "\n labor cost per square foot" + productType.getLaborCostPer()
                + "\n area: " + area
                + "\n Total MaterialCost: " + totalMaterialCost
                + "\n Total LaborCost: " + totalLaborCost
                + "\n Total Tax: " + totalTax
                + "\n Total Price: " + totalPrice;
    }

}
// June 11