/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 *
 * @author josag
 */
public abstract class FaseEjecucion extends Thread{
    
    CocheIA agente;
    Classifier conocimiento;
    Instances casosEntrenamiento;
    
    boolean completado = false;
    
    /*public void PrepararEjecucion(CocheIA ag, Classifier cono){
        agente = ag;
        conocimiento = cono;
    }*/
    
    public void PrepararEjecucion(Object... params){
        agente = (CocheIA) params[0];
        conocimiento = (Classifier) params[1];
    }
    
    
    public abstract boolean FaseCompletada();
    
    //GetDatos
    
    public void Ejecutar(){
        start();
    }
    
    abstract void Ejecucion();
    
    @Override
    public void run(){
        Ejecucion();
    }
    
}
