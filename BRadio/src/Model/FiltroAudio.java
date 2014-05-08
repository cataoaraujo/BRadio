/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Rodrigo
 */
public class FiltroAudio implements FileFilter {

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()){
            return true;
        }else{
            return f.getName().endsWith("mp3") || f.getName().endsWith("wav");
        }
    }



}
