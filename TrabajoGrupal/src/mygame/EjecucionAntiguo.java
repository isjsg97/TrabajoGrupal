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
public class EjecucionAntiguo extends Thread{
    
    CocheIA cocheIA;
    
    Vector3f[] coches;
    private float tamanoCoche = 2f;
    private float espacioManiobra = 1f;
    
    
    Instances casosEntrenamiento;
    Classifier conocimiento;
    
    String tablaComprobacionTamano;
    
    Vector3f poscoche = new Vector3f(1,0,0);
    
    public EjecucionAntiguo(int numc, Classifier cono, String tct){
        coches = new Vector3f[numc];
        
        conocimiento = cono;
        tablaComprobacionTamano = tct;
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
        }
        
        float despl = tamanoCoche * 2 + espacioManiobra + 1;
            
        Vector3f vdespl = new Vector3f(0,0,despl);
            
        pos = Operaciones.SumarVectores(pos, vdespl);
            
        coches[coches.length - 1] = pos.clone();
    }
    
    void ObtenerFicheroEntrenamiento(String file){
        
        /*System.out.println("Obtener Fichero :" + conocimiento);
        System.out.println(conocimiento == null);*/
        
        try{ 
            
            String  FicheroCasosEntrenamiento = System.getProperty("user.dir")+"/Aprendizaje/" + file +".arff";
            casosEntrenamiento  = new Instances ( new BufferedReader(new FileReader(FicheroCasosEntrenamiento)));
            casosEntrenamiento.setClassIndex(  casosEntrenamiento.numAttributes() - 1 );
            
            conocimiento.buildClassifier(casosEntrenamiento);
        }catch(IOException e){
            System.err.println("Error al abrir la tabla de entrenamiento");
        } catch (Exception ex) {
            Logger.getLogger(EjecucionAntiguo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void Ejecutar(){
        start();
    }
    
    @Override
    public void run(){
        
        ObtenerFicheroEntrenamiento(tablaComprobacionTamano);
        
        ConseguirListaCoches();
        
        Vector3f delante;
        Vector3f atras = coches[0];
        for(int i = 1; i < coches.length; i++){
            
            delante = coches[i];
            
            poscoche = Operaciones.SumarVectores(atras, Operaciones.RestarVectores(delante, atras).mult(0.5f));
            poscoche.x = 1;
            
            System.out.println("Posicion coche: " + poscoche);
            
            if(EsTamanoCorrecto(delante, atras)){
                System.out.println("Distancia: " + delante.distance(atras) + " resultado es: " + true);
            }else{
                System.out.println("Distancia: " + delante.distance(atras) + " resultado es: " + false);
            }
            
            atras = delante;
        }
        
        
    }
    
    
    boolean EsTamanoCorrecto(Vector3f ddelante, Vector3f datras){
        int res = 0;
        
        float distdelante = poscoche.distance(ddelante);
        float distatras = poscoche.distance(datras);
        
        System.out.println("PoscionDelante: " + ddelante + ", PosicionAtras: " + datras);
        
        System.out.println("DistanciaDelante: " + distdelante + ", DistanciaAtras: " + distatras);
 
        //System.out.println(conocimiento);
        
        try {
            Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
            casoAdecidir.setDataset(casosEntrenamiento);   
            casoAdecidir.setValue(0,  distdelante);
            casoAdecidir.setValue(1,  distatras);  //alcanzara disntancia de 2.5 deberia dar v=5   */
            
            //casoAdecidir.setValue(0,  3.147669f);
            //casoAdecidir.setValue(1,  3.147669f);
            res = (int) conocimiento.classifyInstance(casoAdecidir);
            
            System.out.println("Resultado: " + res);
        } catch (Exception ex) {
            Logger.getLogger(EjecucionAntiguo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res == 1;
    }
}
