/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Instances;
import weka.classifiers.Classifier;

/**
 *
 * @author josag
 */
public abstract class Entrenamiento extends Thread{
    
    CocheIA agente;
    Main main;
    String tablaAprendizaje;
    int iteraciones;
    
    Instances  casosEntrenamiento;
    Classifier conocimiento;
    
    public Entrenamiento(CocheIA ag, Main m, String ta, int it, Classifier cono){
        agente = ag;
        main = m;
        tablaAprendizaje = ta;
        iteraciones = it;
        
        conocimiento = cono;
        
    }
    
    
    abstract void PreparacionEscenario();
    
    abstract void PreparacionAgente();  
    
    abstract void PreparacionDatos();
    
    abstract boolean EsExito();
    
    abstract void ReCalculo();
    
    abstract void GuardarExito();
    
    abstract void GuardarFracaso();
    
    public void Entrenar(){
        start();
    }
    
    @Override
    public void run(){
        
        ObtenerFicheroEntrenamiento();
        
        PreparacionEscenario();
        
        for(int i = 0; i < iteraciones; i++){
            
            PreparacionAgente(); 
            PreparacionDatos();
            
            if(EsExito()){
                GuardarExito();
                PreparacionEscenario();
            }else{
                GuardarFracaso();
                ReCalculo();
            }
        }
    }
    
    void ObtenerFicheroEntrenamiento(){
        
        try{ 
        
            String  FicheroCasosEntrenamiento =System.getProperty("user.dir")+"/lanzamientos.arff";
            casosEntrenamiento  = new Instances ( new BufferedReader(new FileReader(FicheroCasosEntrenamiento)));
            casosEntrenamiento.setClassIndex(  casosEntrenamiento.numAttributes() - 1 );
        }catch(IOException e){
            System.err.println("Error al abrir la tabla de entrenamiento");
        }
    }
    
}
