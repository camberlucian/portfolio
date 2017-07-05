/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDao;

import FMDto.Order;
import FMDto.Product;
import FMServiceLayer.InvalidInputException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author camber
 */
public class FMDaoTest {

    private FMDao dao;
    private Order testOrder;

    public FMDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.dao = ctx.getBean("testDao", FMDao.class);

        this.testOrder = new Order();
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
        testOrder.setTaxRate(new BigDecimal("4.00"));
        testOrder.setTotalLaborCost(new BigDecimal("9.00"));
        testOrder.setTotalTax(new BigDecimal("10.00"));
        testOrder.setTotalPrice(new BigDecimal("4.00"));

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
     * Test of addOrder method, of class FMDao.
     */
    @Test
    public void testAddOrder() {
        assertNotNull(dao.addOrder(testOrder));
    }

    @Test
    public void testAddOrderBigNumber() throws Exception {
        testOrder.setOrderNumberId(999999999);
        dao.addOrder(testOrder);
        assertNotNull(dao.getOrder(testOrder.getOrderNumberId(), testOrder.getDate()));
    }

    @Test
    public void testAddOrderNegNumberBigDate() throws Exception {
        testOrder.setOrderNumberId(-999999999);
        testOrder.setDate(LocalDate.MAX);
        dao.addOrder(testOrder);
        assertNotNull(dao.getOrder(testOrder.getOrderNumberId(), testOrder.getDate()));
    }

    /**
     * Test of getOrder method, of class FMDao.
     */
    @Test
    public void testGetOrder() throws Exception {
        dao.addOrder(testOrder);
        assertNotNull(dao.getOrder(testOrder.getOrderNumberId(), testOrder.getDate()));

    }

    @Test
    public void testGetOrderWrongDate() throws Exception {
        dao.addOrder(testOrder);
        assertNotNull(dao.getOrder(testOrder.getOrderNumberId(), LocalDate.of(2017, 6, 9)));

    }

    @Test
    public void testGetOrderBadDate() throws Exception {
        dao.addOrder(testOrder);
        assertNotNull(dao.getOrder(testOrder.getOrderNumberId(), LocalDate.MAX));

    }

    @Test
    public void testGetOrderWrongNumberRightDate() throws Exception {
        dao.addOrder(testOrder);
        boolean success = false;
        try {
            dao.getOrder(-2, testOrder.getDate());
        } catch (InvalidInputException e) {
            success = true;
        }

        assertTrue(success);

    }

    @Test
    public void testGetOrderBadInfo() throws Exception {
        dao.addOrder(testOrder);
        boolean success = false;
        try {
            dao.getOrder(23, LocalDate.MAX);
        } catch (InvalidInputException e) {
            success = true;
        }

        assertTrue(success);

    }

    /**
     * Test of removeOrder method, of class FMDao.
     */
    @Test
    public void testRemoveOrder() {
        dao.addOrder(testOrder);
        dao.removeOrder(testOrder.getOrderNumberId(), testOrder.getDate());
        boolean success = false;
        try {
            dao.getOrder(testOrder.getOrderNumberId(), testOrder.getDate());
        } catch (InvalidInputException e) {
            success = true;
        }

        assertTrue(success);
    }

    /**
     * Test of getOrdersByDate method, of class FMDao.
     */
    @Test
    public void testGetOrdersByDate() {
        dao.addOrder(testOrder);
        assertTrue(dao.getOrdersByDate(testOrder.getDate()).size() == 1);
    }

    /**
     * Test of getOrdersByName method, of class FMDao.
     */
    @Test
    public void testGetOrdersByName() {
    }

    /**
     * Test of load method, of class FMDao.
     */
    @Test
    public void testLoad() throws Exception {
        dao.load();
        assertTrue(dao.getCurrentId() > 0);
    }

    /**
     * Test of save method, of class FMDao.
     */
    @Test
    public void testSave() throws Exception {
    }

    /**
     * Test of getCurrentId method, of class FMDao.
     */
    @Test
    public void testGetCurrentId() throws Exception {
        dao.load();
        assertTrue(dao.getCurrentId() > 0);
    }

    /**
     * Test of setCurrentId method, of class FMDao.
     */
    @Test
    public void testSetCurrentId() {
    }

    /**
     * Test of getAllProducts method, of class FMDao.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        dao.load();
        List<Product> products = dao.getAllProducts();
        assertTrue(products.size() > 0);
    }

    /**
     * Test of getProduct method, of class FMDao.
     */
    @Test
    public void testGetProduct() throws Exception {
        dao.load();
        assertNotNull(dao.getProduct("Wood").getName());
    }

    @Test
    public void testGetProductBadProduct() throws Exception {
        boolean success = false;
        try {
            dao.getProduct("jim beam");
        } catch (InvalidInputException e) {
            success = true;
        }
        assertTrue(success);
    }

    /**
     * Test of addProduct method, of class FMDao.
     */
    @Test
    public void testAddProduct() {
    }

    /**
     * Test of removeProduct method, of class FMDao.
     */
    @Test
    public void testRemoveProduct() {
    }

    /**
     * Test of getState method, of class FMDao.
     */
    @Test
    public void testGetState() throws Exception {
        dao.load();
        assertTrue(dao.getState("KY").compareTo(new BigDecimal("100.00")) < 0);
    }

    @Test
    public void testGetStateBadState() throws Exception {
        dao.load();
        boolean success = false;
        try {
            assertTrue(dao.getState("JJ").compareTo(new BigDecimal("100.00")) < 0);
        } catch (InvalidInputException | NumberFormatException e) {
            success = true;
        }
        assertTrue(success);
    }

    /**
     * Test of getAllStates method, of class FMDao.
     */
    @Test
    public void testGetAllStates() throws Exception {
        dao.load();
        assertTrue(dao.getAllStates().size() == 58);
    }

    /**
     * Test of addState method, of class FMDao.
     */
    @Test
    public void testAddState() {
    }

    /**
     * Test of removeState method, of class FMDao.
     */
    @Test
    public void testRemoveState() {
    }

    /**
     * Test of hasProduct method, of class FMDao.
     */
    @Test
    public void testHasProduct() {
    }

}
// June 11