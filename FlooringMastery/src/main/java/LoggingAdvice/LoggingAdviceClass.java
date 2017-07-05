/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoggingAdvice;

import FMAuditDao.FMAuditDao;
import FMAuditDao.FMAuditDaoImpl;
import java.io.File;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author camber
 */
public class LoggingAdviceClass {

    private FMAuditDao auditDao;

    public LoggingAdviceClass(FMAuditDao makeAuditDao) {
        this.auditDao = makeAuditDao;
    }

    public void auditError(JoinPoint jp, Object returnValue) {
        Object[] file = jp.getArgs();
        File badFile = (File) file[0];
        String fileName = badFile.getName();
        System.out.println(fileName);
        String entry = ": Could not read file " + fileName + " check file name manually";
        auditDao.writeEntry(entry);
    }

}
// June 11