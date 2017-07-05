/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMDao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author camber
 */
public class ConfigDaoImpl implements ConfigDao {

    private final String CONFIG_FILE = "bootConfig.txt";

    @Override
    public boolean readConfig() throws FMDaoException {
        boolean training = true;
        Scanner scan;
        try {
            scan = new Scanner(new FileReader(CONFIG_FILE));
        } catch (FileNotFoundException e) {
            throw new FMDaoException("Could Not load from the config file. please enter either p or t in the first line");
        }
        String configuration = scan.nextLine();
        if (configuration.equals("p")) {
            training = false;
        }
        return training;
    }

}
// June 11
