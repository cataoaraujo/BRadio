/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author Rodrigo
 */
public class FormatoTXT extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getLevel());
        sb.append(" ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        Date date = new Date(record.getMillis());
        sb.append(sdf.format(date));
        sb.append(" ");
        sb.append(record.getMessage());
        sb.append("\n");
        return sb.toString();
    }

}
