/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo
 */
public class GeradorLog {

    private static final Logger loggerDefault = Logger.getLogger("default");
    private static final Logger loggerFull = Logger.getLogger("default.full");
    private static GeradorLog instance;

    private GeradorLog() throws IOException {
        loggerDefault.setLevel(Level.SEVERE);

       
        loggerFull.setLevel(Level.ALL);
        loggerFull.setUseParentHandlers(false);
        
        
        Handler handlerFull2 = new FileHandler("log.html");
        handlerFull2.setFormatter(new FormatoHTML());
        handlerFull2.setLevel(Level.ALL);
        loggerFull.addHandler(handlerFull2);
    }

    private static void init(){
        if (instance == null) {
            try {
                instance = new GeradorLog();
            } catch (IOException ex) {
                Logger.getLogger(GeradorLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Logger getLoggerFull() {
        init();
        return loggerFull;
    }

}
