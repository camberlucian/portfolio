/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDto;

import java.math.BigDecimal;

/**
 *
 * @author camber
 */
public class Product {

    private String name;
    private BigDecimal materialCostPer;
    private BigDecimal laborCostPer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaterialCostPer() {
        return materialCostPer;
    }

    public void setMaterialCostPer(BigDecimal materialCostPer) {
        this.materialCostPer = materialCostPer;
    }

    public BigDecimal getLaborCostPer() {
        return laborCostPer;
    }

    public void setLaborCostPer(BigDecimal laborCostPer) {
        this.laborCostPer = laborCostPer;
    }

}
// June 11