/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.core.Debug;

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
    
    int fase;
    
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
    
    abstract void Guardar();
    
    public void Entrenar(){
        start();
    }
    
    abstract void Entrenamiento();
    
    //Preguntar A Profesor sobre Recalculo (usar for o simular for) y si es mejor hacer esta funcion independiente del aprendizaje
    
    @Override
    public void run(){
        
        ObtenerFicheroEntrenamiento();
        
        
        Entrenamiento();
        
        
        GuardarFicheroEntrenamiento();
        
        System.out.println("ENTRENAMIENTO REALIZADO :D");
    }
    
    void ObtenerFicheroEntrenamiento(){
        
        try{ 
        
            String  FicheroCasosEntrenamiento = System.getProperty("user.dir")+"/Aprendizaje/" + tablaAprendizaje +".arff";
            casosEntrenamiento  = new Instances ( new BufferedReader(new FileReader(FicheroCasosEntrenamiento)));
            casosEntrenamiento.setClassIndex(  casosEntrenamiento.numAttributes() - 1 );
        }catch(IOException e){
            System.err.println("Error al abrir la tabla de entrenamiento");
        }
    }
    
    void GuardarFicheroEntrenamiento(){
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/Aprendizaje/" + tablaAprendizaje +".arff"));){
                  
        
            writer.write(casosEntrenamiento.toString());
            writer.flush();
            writer.close();
            
        }catch(Exception e){
            System.err.println("Error al abrir la tabla de entrenamiento");
        }
        
 
        
        //System.out.println(casosEntrenamiento.toString());
        //System.out.println(System.getProperty("user.dir"));
        
        //Debug.saveToFile(System.getProperty("user.dir") + "/Aprendizaje/" + "a.dat", conocimiento);
       
    }
    
}
