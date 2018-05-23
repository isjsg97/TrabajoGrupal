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
    
    CocheIA cocheIA;
    
    Vector3f[] coches;
    private float tamanoCoche = 2f;
    private float espacioManiobra = 1f;
    
    
    
    Classifier conocimiento;
    
    String tablaComprobacionTamano;
    
    Vector3f poscoche = new Vector3f(1,0,0);
    
    
    FaseEjecucion[] fasesEjecucion;
    String[] tablas;
    
    Main main;
    
    public Ejecucion(int numc, Main m, Classifier cono, FaseEjecucion[] fases, String[] tabs){
        coches = new Vector3f[numc];
        
        conocimiento = cono;
        
        fasesEjecucion = fases;
        tablas = tabs;
        
        main = m;
    }
    
    
    void ConseguirListaCoches(){
        
        Vector3f pos = new Vector3f(0,0,0);
        Random ran = new Random();
        
        for(int i = 0; i < coches.length-1; i++){
            
            float despl = tamanoCoche * 2f + ran.nextFloat() * (espacioManiobra + 2);
            //float despl = tamanoCoche  + ran.nextFloat() * (espacioManiobra);
            
            Vector3f vdespl = new Vector3f(0,0,despl);
            
            pos = Operaciones.SumarVectores(pos, vdespl);
            
            coches[i] = pos.clone();
            
            main.CrearCoche(false, pos);
        }
        
        float despl = tamanoCoche * 2 + espacioManiobra + 1;
            
        Vector3f vdespl = new Vector3f(0,0,despl);
            
        pos = Operaciones.SumarVectores(pos, vdespl);
            
        coches[coches.length - 1] = pos.clone();
        
        main.CrearCoche(false, pos);
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
        
        ConseguirListaCoches();
        
        
        cocheIA = main.CrearCocheIA(new Vector3f(1,0,0));
        
        for(int i = 0; i < fasesEjecucion.length; i++){
            
            String tabla = tablas[i];
            FaseEjecucion fase = fasesEjecucion[i];
            Instances casosEntrenamiento = ObtenerFicheroEntrenamiento(tabla);
            
            if(i == 0){
                
                fase.PrepararEjecucion(cocheIA, conocimiento, casosEntrenamiento, coches);
                
            }else{
                
                fase.PrepararEjecucion(cocheIA, conocimiento, casosEntrenamiento);
                
            }
            
            fase.Ejecutar();
            
            while(!fase.FaseCompletada()){
                try {
                    Thread.sleep(1/5);
                }catch (InterruptedException ex) {
                    Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
            }
        }
        
        
    }
    
    
    
}
