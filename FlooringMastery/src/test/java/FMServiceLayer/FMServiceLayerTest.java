/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMServiceLayer;

import FMDao.ConfigDao;
import FMDao.ConfigDaoStubImpl;
import FMDao.FMDao;
import FMDao.FMDaoStubImpl;
import FMDto.Order;
import FMDto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author camber
 */
public class FMServiceLayerTest {

    private FMDao dao;
    private ConfigDao config;
    private Order testOrder;
    private FMServiceLayer service;

    public FMServiceLayerTest() {
        this.dao = new FMDaoStubImpl();
        this.config = new ConfigDaoStubImpl();
        this.service = new FMServiceLayerImpl(dao, config);
        this.testOrder = new Order();
        testOrder.setArea(new BigDecimal("2.00"));
        testOrder.setCustomerName("Jessica");
        testOrder.setDate(LocalDate.of(1941, 12, 7));
        testOrder.setOrderNumberId(699);
        Product product = new Product();
        product.setName("obsidian");
        product.setLaborCostPer(new BigDecimal("3.00"));
        product.setMaterialCostPer(new BigDecimal("4.00"));
        testOrder.setProductType(product);
        testOrder.setState("KY");
        //testOrder.setTaxRate(new BigDecimal("4.00"));
        //testOrder.setTotalLaborCost(new BigDecimal("9.00"));
        //testOrder.setTotalTax(new BigDecimal("10.00"));
        // testOrder.setTotalPrice(new BigDecimal("4.00"));
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of completeOrder method, of class FMServiceLayer.
     */
    @Test
    public void testCompleteOrder() throws Exception {
        assertNotNull(service.completeOrder(testOrder).getTotalPrice());
    }

    public void testCompleteOrderBadState() throws Exception {
        boolean success = false;
        try {
            testOrder.setState("GA");
            service.completeOrder(testOrder);
        } catch (InvalidInputException e) {
            success = true;
        }

        assertTrue(success);
    }

    /**
     * Test of getOrders method, of class FMServiceLayer.
     */
    @Test
    public void testGetOrders() throws Exception {
        assertTrue(service.getOrders(LocalDate.of(1941, 12, 7)).size() == 1);
    }

    public void testGetOrdersBadDate() throws Exception {
        boolean success = false;
        try {
            service.getOrders(LocalDate.MAX);
        } catch (InvalidInputException e) {
            success = true;
        }

        assertTrue(success);
    }

    /**
     * Test of getSpecificOrder method, of class FMServiceLayer.
     */
    @Test
    public void testGetSpecificOrder() throws Exception {
        assertNotNull(service.getSpecificOrder(LocalDate.of(1947, 12, 7), 666));
    }

    @Test
    public void testGetSpecificOrderBadDate() throws Exception {
        assertTrue(service.getSpecificOrder(LocalDate.MAX, 666).getCustomerName().equals("Camber"));
    }

    public void testGetSpecificOrderBadNumber() throws Exception {
        boolean success = false;
        try {
            service.getSpecificOrder(LocalDate.MAX, 0);

        } catch (InvalidInputException e) {
            success = true;
        }
        assertTrue(success);
    }

    /**
     * Test of deleteOrderValidation method, of class FMServiceLayer.
     */
    @Test
    public void testDeleteOrderValidation() {
    }

    /**
     * Test of saveWork method, of class FMServiceLayer.
     */
    @Test
    public void testSaveWork() throws Exception {
    }

    /**
     * Test of load method, of class FMServiceLayer.
     */
    @Test
    public void testLoad() throws Exception {
    }

    /**
     * Test of getAllProducts method, of class FMServiceLayer.
     */
    @Test
    public void testGetAllProducts() {
        assertTrue(service.getAllProducts().size() == 1);
    }

    /**
     * Test of addOrder method, of class FMServiceLayer.
     */
    @Test
    public void testAddOrder() {
        assertNotNull(service.addOrder(testOrder));
    }

    /**
     * Test of RecalcEditedOrder method, of class FMServiceLayer.
     */
    @Test
    public void testRecalcEditedOrder() throws Exception {
        Order newOrder = new Order();
        newOrder.setArea(testOrder.getArea());
        newOrder.setCustomerName("Jim");
        newOrder.setState("GA");
        newOrder.setTaxRate(new BigDecimal("4.00"));
        newOrder.setProductType(testOrder.getProductType());
        assertNotNull(service.RecalcEditedOrder(testOrder, newOrder));

    }

    @Test
    public void testRecalcEditedOrderNewState() throws Exception {
        Order newOrder = new Order();
        newOrder.setArea(testOrder.getArea());
        newOrder.setCustomerName("Jim");
        newOrder.setState("VI");
        testOrder.setTaxRate(new BigDecimal("4.00"));
        newOrder.setTaxRate(testOrder.getTaxRate());
        newOrder.setProductType(testOrder.getProductType());
        assertNotNull(service.RecalcEditedOrder(testOrder, newOrder));

    }

    @Test
    public void testRecalcEditedOrderBadState() throws Exception {
        Order newOrder = new Order();
        newOrder.setArea(testOrder.getArea());
        newOrder.setCustomerName("Jim");
        newOrder.setState("DQ");
        testOrder.setTaxRate(new BigDecimal("4.00"));
        newOrder.setTaxRate(testOrder.getTaxRate());
        newOrder.setProductType(testOrder.getProductType());
        assertNotNull(service.RecalcEditedOrder(testOrder, newOrder));

    }

    /**
     * Test of removeOrder method, of class FMServiceLayer.
     */
    @Test
    public void testRemoveOrder() {
        assertNotNull(service.removeOrder(testOrder));
    }

    /**
     * Test of getAllStates method, of class FMServiceLayer.
     */
    @Test
    public void testGetAllStates() {
        assertTrue(service.getAllStates().size() == 2);
    }

}
// June 11