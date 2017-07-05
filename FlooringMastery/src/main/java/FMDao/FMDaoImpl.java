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
public class FMDaoImpl implements FMDao {

    private Map<LocalDate, HashMap<Integer, Order>> ordersByDate = new HashMap<>();
    private Map<String, BigDecimal> stateTaxes = new HashMap<>();
    private Map<String, Product> productList = new HashMap<>();
    private List<String> brokenOrders = new ArrayList<>();
    private int currentId;
    private String loadPath;
    private String brokenOrderFile;

    public FMDaoImpl(String loadPath, String brokenOrderFile) {
        this.loadPath = loadPath;
        this.brokenOrderFile = brokenOrderFile;
    }

    @Override
    public Order addOrder(Order order) {
        Map<Integer, Order> currentFile;
        if (ordersByDate.containsKey(order.getDate())) {
            currentFile = ordersByDate.get(order.getDate());
        } else {
            currentFile = new HashMap<>();
        }
        currentFile.put(order.getOrderNumberId(), order);
        ordersByDate.put(order.getDate(), (HashMap<Integer, Order>) currentFile);
        currentId = order.getOrderNumberId();
        return order;

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
        Order deleteOrder = ordersByDate.get(date).get(id);
        ordersByDate.get(date).remove(id);
        return deleteOrder;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws InvalidInputException {
        Collection<Order> col = null;
        boolean validDate = false;
        if (ordersByDate.containsKey(date)) {
            if (ordersByDate.get(date).size() > 0) {
                Map<Integer, Order> orderMap = ordersByDate.get(date);
                col = orderMap.values();
                validDate = true;
            }

        }
        if(!validDate){
            throw new InvalidInputException("No orders exist under the requested date");
        }
        return new ArrayList(col);

    }

    @Override
    public void load() throws FMDaoException {
        File folder = new File(loadPath);
        File[] files = folder.listFiles();
        Scanner scan;

        // load broken orders
        try {
            scan = new Scanner(new BufferedReader(new FileReader(brokenOrderFile)));
        } catch (FileNotFoundException e) {
            throw new FMDaoException("Could Not Loat From File", e);
        }

        while (scan.hasNextLine()) {
            brokenOrders.add(scan.nextLine());
        }

        // load orders from files
        for (File f : files) {
            String[] firstSplit = f.getName().split("_");
            if (firstSplit.length != 2) {
                this.logThisFile(f);
                continue;
            }
            String[] secondSplit = firstSplit[1].split("\\.");
            if (secondSplit.length != 2) {
                this.logThisFile(f);
                continue;
            }
            String dateString = secondSplit[0];

            if (Integer.parseInt(dateString) <= 0) {
                this.logThisFile(f);
                continue;
            }

            if (Integer.parseInt(dateString.substring(2, 4)) > 31 || Integer.parseInt(dateString.substring(2, 4)) < 1) {
                this.logThisFile(f);
                continue;
            }
            LocalDate fileDate;
            if (dateString.length() == 8) {
                fileDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));

            } else {
                this.logThisFile(f);
                continue;
            }

            try {
                scan = new Scanner(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                throw new FMDaoException("Could Not Loat From File", e);
            }

            while (scan.hasNextLine()) {
                Order newO = new Order();
                Product newP = new Product();
                String newLine = scan.nextLine();
                String[] line = newLine.split(",");
                if (line.length < 12 || line.length > 12) {
                    brokenOrders.add(newLine);

                    continue;
                }

                try {

                    newP.setName(line[4]);

                    newP.setMaterialCostPer(new BigDecimal(line[6]).setScale(2, RoundingMode.HALF_UP));
                    newP.setLaborCostPer(new BigDecimal(line[7]).setScale(2, RoundingMode.HALF_UP));

                    newO.setDate(fileDate);

                    newO.setOrderNumberId(Integer.parseInt(line[0]));
                    newO.setCustomerName(line[1]);
                    newO.setState(line[2]);
                    newO.setTaxRate(new BigDecimal(line[3]).setScale(2, RoundingMode.HALF_UP));
                    newO.setProductType(newP);
                    newO.setArea(new BigDecimal(line[5]).setScale(2, RoundingMode.HALF_UP));
                    newO.setTotalMaterialCost(new BigDecimal(line[8]).setScale(2, RoundingMode.HALF_UP));
                    newO.setTotalLaborCost(new BigDecimal(line[9]).setScale(2, RoundingMode.HALF_UP));
                    newO.setTotalTax(new BigDecimal(line[10]).setScale(2, RoundingMode.HALF_UP));
                    newO.setTotalPrice(new BigDecimal(line[11]).setScale(2, RoundingMode.HALF_UP));

                    this.addOrder(newO);
                } catch (NumberFormatException e) {

                    brokenOrders.add(newLine);
                    continue;
                }

            }

        }
        // read from state tax file
        try {
            scan = new Scanner(new BufferedReader(new FileReader("Taxes.txt")));
        } catch (FileNotFoundException e) {
            throw new FMDaoException("Could not load state tax rates");
        }
        while (scan.hasNextLine()) {
            String[] stateAndTax = scan.nextLine().split(",");
            String state = stateAndTax[0];
            BigDecimal rate = null;
            try {
                rate = new BigDecimal(stateAndTax[1]);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                continue;
            }
            rate = rate.setScale(2, RoundingMode.HALF_UP);

            stateTaxes.put(state, rate);
        }

        // read from product file
        try {
            scan = new Scanner(new BufferedReader(new FileReader("Products.txt")));
        } catch (FileNotFoundException e) {
            throw new FMDaoException("Could not load Product List");
        }

        while (scan.hasNextLine()) {
            String[] product = scan.nextLine().split(",");
            Product newP = new Product();
            String name = product[0];
            BigDecimal costPer = null;
            BigDecimal laborPer = null;

            try {
                costPer = new BigDecimal(product[1]);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                continue;
            }

            costPer = costPer.setScale(2, RoundingMode.HALF_UP);

            try {
                laborPer = new BigDecimal(product[2]);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                continue;
            }

            laborPer = laborPer.setScale(2, RoundingMode.HALF_UP);

            newP.setName(name);
            newP.setMaterialCostPer(costPer);
            newP.setLaborCostPer(laborPer);

            productList.put(newP.getName(), newP);

        }

        //get latest ID Number
        try {
            scan = new Scanner(new BufferedReader(new FileReader("lastId.txt")));
        } catch (FileNotFoundException e) {
            throw new FMDaoException("Could not load ID Number File");
        }

        try {
            currentId = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            throw new FMDaoException("Id Number File does not contain a number");
        }

    }

    @Override
    public void save() throws FMDaoException {
        // save orders to their files
        PrintWriter print;
        Set<LocalDate> dates = ordersByDate.keySet();
        for (LocalDate date : dates) {
            String fileName = "Orders_" + date.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
            try {
                print = new PrintWriter(new FileWriter(loadPath + "/" + fileName));
            } catch (IOException e) {
                throw new FMDaoException("Could not write to the order file for" + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            }

            Collection<Order> orders = ordersByDate.get(date).values();

            for (Order order : orders) {
                print.println(order.getOrderNumberId() + ","
                        + order.getCustomerName() + ","
                        + order.getState() + ","
                        + order.getTaxRate() + ","
                        + order.getProductType().getName() + ","
                        + order.getArea() + ","
                        + order.getProductType().getMaterialCostPer() + ","
                        + order.getProductType().getLaborCostPer() + ","
                        + order.getTotalMaterialCost() + ","
                        + order.getTotalLaborCost() + ","
                        + order.getTotalTax() + ","
                        + order.getTotalPrice());

                print.flush();
            }
            print.close();
        }

        //save the latest order ID
        try {
            print = new PrintWriter(new FileWriter("lastId.txt"));
        } catch (IOException e) {
            throw new FMDaoException("Could not write to order number generator");
        }
        print.println(currentId);
        print.flush();
        print.close();

        // save broken orders
        try {
            print = new PrintWriter(new FileWriter(brokenOrderFile));
        } catch (IOException e) {
            throw new FMDaoException("Could not write broken orders");
        }
        for (String line : brokenOrders) {
            print.println(line);
            print.flush();
        }
        print.close();

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
