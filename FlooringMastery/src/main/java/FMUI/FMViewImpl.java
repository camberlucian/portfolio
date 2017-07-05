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
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author camber
 */
public class FMViewImpl implements FMView {

    private UserIO io;

    public FMViewImpl(UserIO io) {
        this.io = io;
    }

    @Override
    public int displayMenuAndGetInput() {
        io.print("========================");
        io.print("++++++++++++++++++++++++");
        io.print("========================");
        io.print("Gibbons Flooring Company");
        io.print("========================");
        io.print("Choose one");
        io.print("1) New Order");
        io.print("2) Show Orders from a date");
        io.print("3) Edit an Order");
        io.print("4) Delete an Order");

        io.print("5) Save your work");
        io.print("6) exit");
        io.print("========================");
        return io.readInt("Enter a number", 1, 6);

    }

    @Override
    public Order createBasicOrder(List<Product> products, Set<String> states) {
        Order newOrder = new Order();
        boolean goodName = false;
        while (!goodName) {
            String inputName = io.readString("Enter Customer Name");
            if (inputName.trim().isEmpty()) {
                io.print("Please enter at least one character for customer name");
            } else if (inputName.contains(",") || inputName.trim().isEmpty()) {
                io.print("Our system does not allow commas and does not allow blank input");
            } else {
                newOrder.setCustomerName(inputName);
                goodName = true;
            }

        }

        boolean orderToday = io.readYesorNo("Is this order for today?");
        if (orderToday) {
            newOrder.setDate(LocalDate.now());
        } else {
            newOrder.setDate(io.readDate("when should this order be processed?", "MM-dd-yyyy"));
        }

        boolean goodArea = false;
        BigDecimal newArea;
        while (!goodArea) {
            newArea = (io.readBigD("How many square feet will the order be?"));
            if (newArea.compareTo(new BigDecimal("0.00")) <= 0) {
                io.print("Please enter a positive number for your area");
            } else {
                goodArea = true;
                newOrder.setArea(newArea);
            }
        }

        String state = "";
        boolean confirmState = false;
        while (!confirmState) {
            state = io.readString("Enter Customer's State postal code (example: KY for kentucky)").toUpperCase();
            if (state.contains(",") || state.trim().isEmpty()) {
                io.print("Our system does not allow commas and does not allow blank input");
            } else if (states.contains(state)) {
                newOrder.setState(state);
                confirmState = true;
            } else {
                if (!io.readYesorNo(state + " is not recognized by system. is this a custom state?")) {
                    continue;
                } else {
                    newOrder.setTaxRate(io.readBigD("What is the standing tax rate for " + state));
                    confirmState = true;
                }

            }

        } //  end state confirmation

        io.print("Choose a material");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            io.print((i + 1) + ") " + p.getName() + " - Mat per sq foot: $" + p.getMaterialCostPer() + " - Labor per sq foot: $" + p.getLaborCostPer());
        }
        io.print("0) Custom Product");

        int selection = io.readInt("Enter corrisponding material number", 0, products.size());

        if (selection == 0) {
            boolean goodProduct = false;
            String pName = "";
            Product customP = new Product();
            while (!goodProduct) {
                pName = io.readString("enter product name");
                if (pName.contains(",") || pName.trim().isEmpty()) {
                    io.print("Our system does not allow commas and does not allow blank input");
                } else {
                    goodProduct = true;
                }

            }
            customP.setName(pName);
            customP.setLaborCostPer(io.readBigD("enter labor cost per square foot"));
            customP.setMaterialCostPer(io.readBigD("enter material cost per square foot"));
            newOrder.setProductType(customP);

        } else {
            newOrder.setProductType(products.get(selection - 1));
        }

        return newOrder;
    }

    @Override
    public Order editOrderBasic(Order order, Set<String> states) {
        Order newOrder = new Order();

        io.print("Now Editing Order");
        if (io.readYesorNo("Would you like to give this order a new ID number?")) {
            newOrder.setOrderNumberId(0);
        } else {
            newOrder.setOrderNumberId(order.getOrderNumberId());
        }

        io.print("Current Name: " + order.getCustomerName());
        boolean goodName = false;
        String newInput = "";
        while (!goodName) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                if (newInput.contains(",")) {
                    io.print("our system does not allow for commmas");
                } else {
                    newOrder.setCustomerName(newInput);
                    goodName = true;
                }
            } else {
                newOrder.setCustomerName(order.getCustomerName());
                goodName = true;
            }
        }

        io.print("Current Order Date: " + order.getDate().format(DateTimeFormatter.ofPattern("MM=-dd-yyyy")));
        boolean validDate = false;
        while (!validDate) {
            newInput = io.readString("Enter new date or press enter to skip. Please enter in format MM-dd-yyyy");
            if (!newInput.trim().equals("")) {
                try {
                    LocalDate newDate = LocalDate.parse(newInput, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                    newOrder.setDate(newDate);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    io.print("Entered date was not a valid date. Please enter date in proper format");
                }
            } else {
                newOrder.setDate(order.getDate());
                validDate = true;
            }

        }//end date validation

        io.print("Current State: " + order.getState());
        boolean goodState = false;
        while (!goodState) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                if (states.contains(newInput)) {
                    goodState = true;
                    newOrder.setState(newInput);
                } else if (newInput.contains(",")) {
                    io.print("our system does not allow for commmas");
                } else if (io.readYesorNo(newInput + " is not currently a U.S. state, commonwealth, or territory. Continue anyway?")) {
                    goodState = true;
                    newOrder.setState(newInput);
                }
            } else {
                newOrder.setState(order.getState());
                goodState = true;
            }
        }//end state validation

        io.print("Current Tax Percentage: %" + order.getTaxRate());
        boolean validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newTax = new BigDecimal(newInput);
                    newOrder.setTaxRate(newTax);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for a tax percentage");
                }
            } else {
                newOrder.setTaxRate(order.getTaxRate());
                validBigD = true;
            }
        }// end validation for tax rate

        Product newProduct = new Product();

        io.print("Current Product: " + order.getProductType().getName());
        boolean goodProduct = false;
        while (!goodProduct) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                if (newInput.contains(",")) {
                    io.print("our system does not allow for commmas");
                } else {
                    newProduct.setName(newInput);
                    goodProduct = true;
                }
            } else {
                newProduct.setName(order.getProductType().getName());
                goodProduct = true;
            }
        }

        io.print("Current material cost per square foot:" + order.getProductType().getMaterialCostPer());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newMatPer = new BigDecimal(newInput);
                    newProduct.setMaterialCostPer(newMatPer);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for the material cost per square foot");
                }
            } else {
                newProduct.setMaterialCostPer(order.getProductType().getMaterialCostPer());
                validBigD = true;
            }

        }//end validation mat cost per

        io.print("Current labor cost per square foot:" + order.getProductType().getLaborCostPer());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newLabPer = new BigDecimal(newInput);
                    newProduct.setLaborCostPer(newLabPer);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for the material cost per square foot");
                }
            } else {
                newProduct.setLaborCostPer(order.getProductType().getLaborCostPer());
                validBigD = true;
            }

        }//end validation mat cost per

        newOrder.setProductType(newProduct);

        io.print("Current area" + order.getArea() + " sq feet");
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newArea = new BigDecimal(newInput);
                    newOrder.setArea(newArea);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for the area");
                }
            } else {
                newOrder.setArea(order.getArea());
                validBigD = true;
            }
        }// end validation for area

        io.print("Current Total Material Cost" + order.getTotalMaterialCost());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newMatTotal = new BigDecimal(newInput);
                    newOrder.setTotalMaterialCost(newMatTotal);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for Total Material Cost");
                }
            } else {
                newOrder.setTotalMaterialCost(order.getTotalMaterialCost());
                validBigD = true;
            }
        }// end validation for Total Material Cost

        io.print("Current Total Labor Cost" + order.getTotalLaborCost());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newLabTotal = new BigDecimal(newInput);
                    newOrder.setTotalLaborCost(newLabTotal);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for Total Labor Cost");
                }
            } else {
                newOrder.setTotalLaborCost(order.getTotalLaborCost());
                validBigD = true;
            }
        }// end validation for Total Labor Cost

        io.print("Current Total Tax" + order.getTotalTax());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newTaxTotal = new BigDecimal(newInput);
                    newOrder.setTotalTax(newTaxTotal);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for Total Tax");
                }
            } else {
                newOrder.setTotalTax(order.getTotalTax());
                validBigD = true;
            }
        }// end validation for Total Tax

        io.print("Current Total Price" + order.getTotalPrice());
        validBigD = false;
        while (!validBigD) {
            newInput = io.readString("Enter new value or press enter to skip");
            if (!newInput.trim().equals("")) {
                try {
                    double test = Double.parseDouble(newInput);
                    BigDecimal newPriceTotal = new BigDecimal(newInput);
                    newOrder.setTotalTax(newPriceTotal);
                    validBigD = true;
                } catch (NumberFormatException e) {
                    io.print("Input is not a valid number pleace enter a valid number for Total Tax");
                }
            } else {
                newOrder.setTotalPrice(order.getTotalPrice());
                validBigD = true;
            }
        }// end validation for Total Tax

        return newOrder;
    }

    @Override
    public void printOrders(List<Order> orders) {
        io.print("All Orders for " + orders.get(0).getDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        io.print("====================");
        for (Order k : orders) {
            io.print("Order Number: " + k.getOrderNumberId() + " | Customer Name: " + k.getCustomerName() 
                   +" | State: " + k.getState() 
                   +" | Tax Rate: " + k.getTaxRate() 
                   +" | Product: " + k.getProductType().getName() 
                   +" | Area: " + k.getArea() 
                   +" | Mat Cost Per: " + k.getProductType().getMaterialCostPer() 
                   +" | SLab Cost Per: " + k.getProductType().getLaborCostPer() 
                   +" | Total Mat Cost: " + k.getTotalMaterialCost() 
                   +" | Total Lab Cost: " + k.getTotalLaborCost() 
                   +" | Total Tax: " + k.getTotalTax() 
                   +" | Total Price: " + k.getTotalPrice());
        }
    }

    @Override
    public LocalDate getDateInput() {
        return io.readDate("Please Enter a date in the form " + "MM-dd-yyyy", "MM-dd-yyyy");
    }

    @Override
    public void displayBanner(String prompt
    ) {
        io.print("=====   " + prompt + "   =====");
    }

    @Override
    public boolean getConfirmation(String prompt
    ) {
        return io.readYesorNo(prompt);
    }

    @Override
    public void displayErrorMessage(String prompt
    ) {
        io.print(prompt);
    }

    @Override
    public boolean confirmOrder(Order order
    ) {
        io.print(order.toString());
        return io.readYesorNo("Confirm order?");
    }

    @Override
    public int getOrderId() {
        return io.readInt("Please enter an Order Number");
    }

    @Override
    public LocalDate getEditOrderDate() {
        if (io.readYesorNo("Do you know the date the order was processed?")) {
            return io.readDate("Please enter the Date your order was processed", "MM-dd-yyyy");
        } else {
            return LocalDate.of(1066, Month.OCTOBER, 14);
        }
    }

}
// June 11