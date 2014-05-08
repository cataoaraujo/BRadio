/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author MatheuseJessica
 */
public class Login {
    private Programa programa;
    private static Login login = null;

    private Login() {
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    } 
    
    public static Login getInstance(){
        if(login == null)
            login = new Login();
        return login;
    }
}
