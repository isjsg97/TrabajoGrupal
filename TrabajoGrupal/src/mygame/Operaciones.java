/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.Random;

/**
 *
 * @author MykexMP
 */
public class  Operaciones {
    
    public static float DegtoRad(float deg){
         
        return (float)Math.PI / 180 * deg;
    } 
    
    public static float RadtoDeg(float rad){
         
        return 180 / (float)Math.PI * rad;
    } 
    
    public static Vector3f RestarVectores(Vector3f vfinal, Vector3f vinicial){
         
        Vector3f res = new Vector3f(vfinal.x - vinicial.x, vfinal.y - vinicial.y, vfinal.z - vinicial.z);
            
        return res;
    }
     
    public static Vector3f SumarVectores(Vector3f vfinal, Vector3f vinicial){
         
        Vector3f res = new Vector3f(vfinal.x + vinicial.x, vfinal.y + vinicial.y, vfinal.z + vinicial.z);
            
        return res;
    }
    
    public static Vector3f RotarVectorY(Vector3f vector, float angulo){
        Vector3f res = new Vector3f(0,0,0);
        
        float rad = DegtoRad(angulo);
        
        res.x = vector.x * (float)Math.cos(rad) - vector.z * (float)Math.sin(rad);
        res.z = vector.x * (float)Math.sin(rad) + vector.z * (float)Math.cos(rad);
        res.y = vector.y;
        //y' = x sin Î¸ + y cos Î¸
        
        return res;
    }
    
    
    public static float EspacioAleatorio(){
        
        Random ran = new Random();
        
        float res = Datos.DistanciaNecesaria() + ((ran.nextFloat() * 1.5f) - 1f )* (2);
        
        return res;
    }
    
    public static float EspacioMinimoAleatorio(){
        
        Random ran = new Random();
        
        float res = Datos.DistanciaNecesaria() + (ran.nextFloat() * 2);
        
        return res;
        
        
    }
    
}
