/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import FMController.FMController;
import FMServiceLayer.InvalidInputException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
// dao shouldnt give hashmap
//remove delete order validation
//when you remove a date dont break it trying to fins another date
// read me for config file
// show whole order with display orders

/**
 *
 * @author camber
 */
public class App {

    public static void main(String[] args) throws InvalidInputException {
        /**
         * UserIO io = new UserIOImpl(); FMDao dao = new FMDaoImpl(); FMView
         * view = new FMViewImpl(io); ConfigDao config = new ConfigDaoImpl();
         * FMServiceLayer service = new FMServiceLayerImpl(dao, config);
         * FMController controller = new FMController(view, service);
         * controller.run(); *
         */

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FMController controller = ctx.getBean("controller", FMController.class);
        controller.run();
        
    }
}

// June 11