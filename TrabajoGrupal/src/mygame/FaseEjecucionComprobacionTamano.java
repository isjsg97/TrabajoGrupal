/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author josag
 */
public class FaseEjecucionComprobacionTamano extends FaseEjecucion{
    
    float distanciaDelante;
    float distanciaAtras;
    
    Vector3f[] coches;
    
    
    //Cosas para situarse a la mitad de los coches (S epuede convertir en otra fase de aprendizaje en un futuro)
    float velocidad = 10;
    float angulo = 0;
    float tiempo;
    
    
    @Override
    public void PrepararEjecucion(Object... params){
        super.PrepararEjecucion(params[0], params[1]);
        
        coches = (Vector3f[])params[2];
    } 
    
    @Override
    public void Ejecutar() {
        
    }

    @Override
    public boolean FaseCompletada() {
        return false;
    }

    @Override
    public void run() {
        
        Vector3f delante;
        Vector3f atras = coches[0];
        for(int i = 1; i < coches.length; i++){
            
            delante = coches[i];
            
            CalcularDatosPosicionInicial(delante, atras);
            
            while(agente.Tiempo() > 0){
                try {
                    Thread.sleep(1/5);
                }catch (InterruptedException ex) {
                    Logger.getLogger(FaseEjecucionComprobacionTamano.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
            }
            
            if(EsTamanoCorrecto(delante, atras)){
                break;
            }else{
                System.out.println("Distancia: " + delante.distance(atras) + " resultado es: " + false);
            }
            
            atras = delante;
        }
        
        
        completado = true;
    }
    
    
    
    
    boolean EsTamanoCorrecto(Vector3f ddelante, Vector3f datras){
        int res = 0;
        
        float distdelante = agente.Spatial().getWorldTranslation().distance(ddelante);
        float distatras = agente.Spatial().getWorldTranslation().distance(datras);
        
        System.out.println("PoscionDelante: " + ddelante + ", PosicionAtras: " + datras);
        
        System.out.println("DistanciaDelante: " + distdelante + ", DistanciaAtras: " + distatras);
 
        try {
            Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
            casoAdecidir.setDataset(casosEntrenamiento);   
            casoAdecidir.setValue(0,  distdelante);
            casoAdecidir.setValue(1,  distatras);  //alcanzara disntancia de 2.5 deberia dar v=5   
            res = (int) conocimiento.classifyInstance(casoAdecidir);
        } catch (Exception ex) {
            Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return res == 1;
    }

    private void CalcularDatosPosicionInicial(Vector3f delante, Vector3f atras) {
        
        Vector3f pos = Operaciones.SumarVectores(atras, Operaciones.RestarVectores(delante, atras).mult(0.5f));
        pos.x = 1;
        
        float distancia = agente.Spatial().getWorldTranslation().distance(pos);
        
        tiempo = distancia / velocidad;
        
        
        System.out.println("Posicion coche: " + pos);
    }
    
}