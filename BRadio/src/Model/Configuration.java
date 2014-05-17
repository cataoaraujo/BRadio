/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Logger.GeradorLog;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rodrigo
 */
public final class Configuration {

    private String ipDataBase = "localhost";
    private int height = 720;
    private int width = 1280;
    private int tempoAtualizacaoPropagandas = 1;
    private boolean fullScreen = false;

    private static Configuration instance = null;


    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
            
        }
        return instance;
    }

    public void readConfiguration() {
        Gson gson = new Gson();
        try {
            if ((new File("configuration.json")).isFile()) {
                BufferedReader br = new BufferedReader(new FileReader("configuration.json"));

                //convert the json string back to object
                Configuration conf = gson.fromJson(br, Configuration.class);
                Configuration.getInstance().setFullScreen(conf.fullScreen);
                Configuration.getInstance().setHeight(conf.height);
                Configuration.getInstance().setWidth(conf.width);
                Configuration.getInstance().setIpDataBase(conf.ipDataBase);
                Configuration.getInstance().setTempoAtualizacaoPropagandas(conf.tempoAtualizacaoPropagandas);
                //System.out.println("Configuração carregada!");
                //System.out.println(conf);
            } else {

                System.out.println("Configuração não existe!");
                String json = gson.toJson(this);
                //System.out.println(json);
                try (FileWriter writer = new FileWriter("configuration.json")) {
                    writer.write(json);
                }

            }
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            GeradorLog.getLoggerFull().config("Não foi possivel criar arquivo de Configuração.");
        }
    }

    public String getIpDataBase() {
        if (ipDataBase == null || "".equals(ipDataBase)) {
            return "localhost";
        }
        return ipDataBase;
    }

    public void setIpDataBase(String ipDataBase) {
        this.ipDataBase = ipDataBase;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTempoAtualizacaoPropagandas() {
        return tempoAtualizacaoPropagandas;
    }

    public void setTempoAtualizacaoPropagandas(int tempoAtualizacaoPropagandas) {
        this.tempoAtualizacaoPropagandas = tempoAtualizacaoPropagandas;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public boolean getFullScreen() {
        return fullScreen;
    }

    @Override
    public String toString() {
        return "Configuration{" + "ipDataBase=" + ipDataBase + ", height=" + height + ", width=" + width + ", tempoAtualizacaoPropagandas=" + tempoAtualizacaoPropagandas + ", fullScreen=" + fullScreen + '}';
    }

}
