/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author Rodrigo
 */
public class FormatoHTML extends Formatter {

    @Override
    public String getHead(Handler h) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head><meta charset=\"utf-8\"><title>Log HTML</title></head>");
        sb.append("<body>");
        sb.append("<table style=\"width: 100%; text-align:left;\">");
        sb.append("<tr style=\"background: gray;\"><th>Data</th><th>Nivel</th><th style=\"color: yellow;\">Mensagem</th></tr>");
        return sb.toString();
    }

    @Override
    public String getTail(Handler h) {
        StringBuilder sb = new StringBuilder();
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<td>");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
        Date date = new Date(record.getMillis());
        sb.append(sdf.format(date));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(record.getLevel().getName());
        sb.append("</td>");
        sb.append("<td style=\"color: red;\">");
        sb.append(record.getMessage());
        sb.append("</td>");
        sb.append("</tr>");
        return sb.toString();

    }

}
