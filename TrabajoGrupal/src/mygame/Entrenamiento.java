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
    
    abstract void PreparacionFase();
    
    abstract void PreparacionDatos();
    
    abstract void Planificacion();
    
    abstract boolean EsExito();
    
    abstract void ReCalculo();
    
    abstract void GuardarExito();
    
    abstract void GuardarFracaso();
    
    abstract int NumeroFases();
    
    abstract boolean FaseCompletada();
    
    abstract boolean FaseExito();
    
    public void Entrenar(){
        start();
    }
    
    
    //Preguntar A Profesor sobre Recalculo (usar for o simular for) y si es mejor hacer esta funcion independiente del aprendizaje
    
    @Override
    public void run(){
        
        ObtenerFicheroEntrenamiento();
        
        
        
        for(int i = 0; i < iteraciones; i++){
            
            PreparacionEscenario();
            
            PreparacionAgente();
            
            PreparacionDatos();
  
            for(fase = 0; fase < NumeroFases(); fase++){
                
                PreparacionFase();
                
                Planificacion();
                
                while(!FaseCompletada()){
                    try {
                        Thread.sleep(1/5);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("La hebra ce mamó");
                    }
                }           
                
                if(!FaseExito()){
                    ReCalculo();
                }
                
            }
            
            if(EsExito() && fase == NumeroFases()){
                    GuardarExito();
           
            }else{
                GuardarFracaso();
                
                
            }
  
        }
        
        GuardarFicheroEntrenamiento();
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
