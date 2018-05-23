/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;

/**
 *
 * @author Jose Santos
 */
public class Datos {
    
    public static float tamanoCoche = 2;
    public static float espacioManiobra = 1;
    public static float offsetPocAgenteX = 1.5f;
    public static int tiempoEsperaThread = 1000 / 4;
    
    public static Vector3f PosInicial(){
        return new Vector3f(offsetPocAgenteX,0,0);
    }
    
    public static float DistanciaNecesaria(){
        float res = tamanoCoche * 2 + espacioManiobra;
        
        return res;
    }
    
}
