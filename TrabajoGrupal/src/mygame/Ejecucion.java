/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Jose Santos
 */
public class Ejecucion extends Thread{
    
    CocheIA agente;
    
    Vector3f[] coches;
    
    Classifier conocimiento;
    
    String tablaComprobacionTamano;
    
    Vector3f poscoche = new Vector3f(1,0,0);
    
    
    FaseEjecucion[] fasesEjecucion;
    String[] tablas;
    
    Main main;
    
    public Ejecucion(Main m, Classifier cono, FaseEjecucion[] fases, String[] tabs, CocheIA agent, Vector3f[] cars){
        
        conocimiento = cono;
        
        fasesEjecucion = fases;
        tablas = tabs;
        
        main = m;
        
        coches = cars;
        agente = agent;
    }
    
    
    
    
    Instances ObtenerFicheroEntrenamiento(String file){
        
        Instances casosEntrenamiento = null;
        
        try{ 
            
            String  FicheroCasosEntrenamiento = System.getProperty("user.dir")+"/Aprendizaje/" + file +".arff";
            casosEntrenamiento  = new Instances ( new BufferedReader(new FileReader(FicheroCasosEntrenamiento)));
            casosEntrenamiento.setClassIndex(  casosEntrenamiento.numAttributes() - 1 );
            
            conocimiento.buildClassifier(casosEntrenamiento);
        }catch(IOException e){
            System.err.println("Error al abrir la tabla de entrenamiento");
        } catch (Exception ex) {
            Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return casosEntrenamiento;
    }
    
    
    public void Ejecutar(){
        start();
    }
    
    @Override
    public void run(){
        
        //ConseguirListaCoches();
        
        Vector3f cocheDelante = new Vector3f(0,0,0);
        Vector3f cocheAtras = new Vector3f(0,0,0);
        
        
        for(int i = 0; i < fasesEjecucion.length; i++){
            
            String tabla = tablas[i];
            FaseEjecucion fase = fasesEjecucion[i];
            Instances casosEntrenamiento = ObtenerFicheroEntrenamiento(tabla);
            
            
            try {
                conocimiento.buildClassifier(casosEntrenamiento);
            } catch (Exception ex) {
                Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(i == 0){
                
                fase.PrepararEjecucion(agente, conocimiento, casosEntrenamiento, coches);
                
                
                
            }else{
                
                fase.PrepararEjecucion(agente, conocimiento, casosEntrenamiento, cocheDelante, cocheAtras);
                
            }
            
            //fase.Ejecutar(); //No va con esto y no tiene explicación alguna, cosas raras de Java como siempre
            fase.start();
            
            while(!fase.FaseCompletada()){
                
                //System.out.println("Matame si puedes chaval");
                
                try {
                    Thread.sleep(Datos.tiempoEsperaThread);
                }catch (InterruptedException ex) {
                    Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
            }
            
            agente.Tiempo(0);
            
            if(i == 0){
                
                Object[] datos = fase.Datos();
                
                cocheDelante = (Vector3f)datos[0];
                cocheAtras = (Vector3f)datos[1];
                
            }else{

                
            }
            
            
            
        }
        
        System.out.println("FIN EJECUCION :D");
        
    }
    
    
    
}
