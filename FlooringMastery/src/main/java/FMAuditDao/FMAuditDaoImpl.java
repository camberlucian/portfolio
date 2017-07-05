/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FMAuditDao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 *
 * @author camber
 */
public class FMAuditDaoImpl implements FMAuditDao{

    @Override
    public void writeEntry(String entry) {
        PrintWriter print = null;
        
            try{
                print = new PrintWriter(new FileWriter("badFileAudit.txt", true));
            }catch(IOException e){
                
            }
            print.println(LocalDate.now() + entry);
            print.flush();
            print.close();
        }
    }
    

// June 11