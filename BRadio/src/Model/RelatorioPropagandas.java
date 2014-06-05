/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.DAO.ConnectionFactory;
import Model.DAO.PropagandaDAO;
import bradio.AlertDialog;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author MatheuseJessica
 */
public class RelatorioPropagandas {

    public Document doc = null;
    public OutputStream os = null;

    public RelatorioPropagandas() throws FileNotFoundException, DocumentException, IOException {
    }

    public void relatorioEntreDatas(LocalDate d1, LocalDate d2) throws IOException {
        PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
        Collection<Propaganda> propagandas = pd.relatorioEntreDatas(d1, d2);
        long diferenca = ChronoUnit.DAYS.between(d1, d2);
        try {
            doc = new Document();
            os = new FileOutputStream("PropagandasEntreDatas.pdf");
            PdfWriter.getInstance(doc, os);
            doc.open();

            Font fTitulo = new Font(FontFamily.COURIER, 20, Font.BOLD);
            Font fTabela = FontFactory.getFont(FontFactory.COURIER, 12);
            Paragraph p = new Paragraph("Relatório de propagandas", fTitulo);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            doc.add(p);
            PdfPTable tabela = new PdfPTable(3);
            for (Propaganda prop : propagandas) {
                tabela.addCell(new Phrase(prop.getHora().toString(), fTabela));
                tabela.addCell(new Phrase(prop.getNome(), fTabela));
                tabela.addCell(new Phrase(prop.getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString(), fTabela));
            }
            doc.add(tabela);
            AlertDialog.show("Relatório gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
    
    public void relatorioDiasSemanaEntreDatas(LocalDate d1, LocalDate d2) throws IOException {
        PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
        Collection<Propaganda> propagandas = pd.getBetween(d1, d2);
        try {
            doc = new Document();
            os = new FileOutputStream("PropagandasEntreDatasSimplificado.pdf");
            PdfWriter.getInstance(doc, os);
            doc.open();

            Font fTitulo = new Font(FontFamily.COURIER, 20, Font.BOLD);
            Font fTabela = FontFactory.getFont(FontFactory.COURIER, 12);
            Paragraph p = new Paragraph("Relatório de propagandas", fTitulo);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            doc.add(p);
            PdfPTable tabela = new PdfPTable(4);
            for (Propaganda propaganda : propagandas) {
                ArrayList<Propaganda> dias = new ArrayList<>(pd.getHorariosPropaganda(propaganda));
                ArrayList<LocalTime> horarios = criaHorariosPropaganda(dias);
                for (LocalTime h : horarios) {
                    ArrayList<Propaganda> datas = new ArrayList<>(pd.getDatasPorHorario(propaganda, h));
                    tabela.addCell(new Phrase(h.toString(), fTabela));
                    tabela.addCell(new Phrase(propaganda.getNome(), fTabela));
                    tabela.addCell(new Phrase(criaDiasSemana(datas), fTabela));
                    tabela.addCell(new Phrase(datas.get(0).getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString() + " até " + datas.get(datas.size() - 1).getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString(), fTabela));
                }
            }
            doc.add(tabela);
            AlertDialog.show("Relatório gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    private String criaDiasSemana(Collection<Propaganda> p) {
        List<Integer> dias = new ArrayList<>();
        for (Propaganda propaganda : p) {
            if (!dias.contains(propaganda.getData().getDayOfWeek().getValue())) {
                dias.add(propaganda.getData().getDayOfWeek().getValue());
            }
        }
        Collections.sort(dias);
        StringBuilder sb = new StringBuilder();
        for (int j = 1; j <= 7; j++) {
            if (dias.contains(j)) {
                switch (j) {
                    case 1:
                        sb.append("D ");
                        break;
                    case 2:
                        sb.append("S ");
                        break;
                    case 3:
                        sb.append("T ");
                        break;
                    case 4:
                        sb.append("Q ");
                        break;
                    case 5:
                        sb.append("Q ");
                        break;
                    case 6:
                        sb.append("S ");
                        break;
                    case 7:
                        sb.append("S ");
                        break;
                }
            } else {
                sb.append("- ");
            }
        }
        return sb.toString();
    }
    
    private ArrayList<LocalTime> criaHorariosPropaganda(Collection<Propaganda> p){
        ArrayList<LocalTime> horarios = new ArrayList<>();
        for (Propaganda propaganda : p) {
            if(!horarios.contains(propaganda.getHora())){
                horarios.add(propaganda.getHora());
            }
        }
        return horarios;
    }

    public void relatorioDiaSemana() throws IOException {
        PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
        Collection<Propaganda> propagandas = pd.getAll();

        try {
            doc = new Document();
            os = new FileOutputStream("PropagandasAtivas.pdf");
            PdfWriter.getInstance(doc, os);
            doc.open();

            Font fTitulo = new Font(FontFamily.COURIER, 20, Font.BOLD);
            Paragraph p = new Paragraph("Relatório de propagandas", fTitulo);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            doc.add(p);
            PdfPTable tabela = new PdfPTable(4);
            Font fTabela = FontFactory.getFont(FontFactory.COURIER, 12);

            for (Propaganda propaganda : propagandas) {
                ArrayList<Propaganda> dias = new ArrayList<>(pd.getHorariosPropagandasAtivas(propaganda));
                //System.out.println(propaganda.getNome());
                //System.out.println("Dias: "+dias);
                ArrayList<LocalTime> horarios = criaHorariosPropaganda(dias);
                //System.out.println("Horarios: "+ horarios);
                for (LocalTime h : horarios) {
                    ArrayList<Propaganda> datas = new ArrayList<>(pd.getDatasPorHorario(propaganda, h));
                    tabela.addCell(new Phrase(h.toString(), fTabela));
                    tabela.addCell(new Phrase(propaganda.getNome(), fTabela));
                    tabela.addCell(new Phrase(criaDiasSemana(datas), fTabela));
                    tabela.addCell(new Phrase(datas.get(0).getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString() + " até " + datas.get(datas.size() - 1).getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString(), fTabela));
                }
            }
            doc.add(tabela);
            AlertDialog.show("Relatório gerado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                doc.close();
            }
            if (os != null) {
                os.close();
            }
            
        }
    }
}
