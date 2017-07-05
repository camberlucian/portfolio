/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VMDao;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author camber
 */
public class VMDaoTest {
    
    ApplicationContext ctx;
    VMDao dao;
    Snack snack;
    
    public VMDaoTest() {
        ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        dao = ctx.getBean("dao", VMDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        snack = new Snack();
        snack.setName("Chips");
        snack.setQuantity(2);
        snack.setPrice(new BigDecimal("2.00"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSnack method, of class VMDao.
     */
    @Test
    public void testGetSnack() {
        dao.addSnack(snack);
        assertNotNull(dao.getSnack(1));
    }

    /**
     * Test of getAllSnacks method, of class VMDao.
     */
    @Test
    public void testGetAllSnacks() {
        assertTrue(dao.getAllSnacks().size() == 9);
    }

    /**
     * Test of addSnack method, of class VMDao.
     */
    @Test
    public void testAddSnack() {
        Snack newSnack = new Snack();
        newSnack.setName("choco");
        int firstSize = dao.getAllSnacks().size();
        dao.addSnack(newSnack);
        assertTrue(dao.getAllSnacks().size() > firstSize);
    }

    /**
     * Test of updateSnack method, of class VMDao.
     */
    @Test
    public void testUpdateSnack() {
        Snack newSnack = new Snack();
        newSnack.setName("mega chips");
        newSnack.setPrice(new BigDecimal("4.00"));
        newSnack.setQuantity(33);
        newSnack.setSnackId(44);
        dao.updateSnack(newSnack);
        newSnack.setName("ultra chips");
        dao.updateSnack(newSnack);
        assertTrue(dao.getSnack(44).getName().equals("ultra chips"));
        
    }

    /**
     * Test of deleteSnack method, of class VMDao.
     */
    @Test
    public void testDeleteSnack() {
        Snack newSnack = new Snack();
        newSnack.setName("mega chips");
        newSnack.setPrice(new BigDecimal("4.00"));
        newSnack.setQuantity(33);
        newSnack.setSnackId(49);
        dao.updateSnack(newSnack);
        int firstSize = dao.getAllSnacks().size();
        dao.deleteSnack(49);
        assertTrue(dao.getAllSnacks().size() == firstSize-1);
        
    }

    
    
}
// june 25