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
public class ConfigDaoStubImpl implements ConfigDao {

    private final String CONFIG_FILE = "bootConfig.txt";

    @Override
    public boolean readConfig() throws FMDaoException {
        return true;

    }

}
// June 11
