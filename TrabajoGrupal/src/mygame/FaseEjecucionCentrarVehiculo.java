/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author josag
 */
public class FaseEjecucionCentrarVehiculo extends FaseEjecucion{
    
    float distanciaDelante;
    float distanciaAtras;
    
    Vector3f cocheDelante;
    Vector3f cocheAtras;
    
    
    //Cosas para situarse a la mitad de los coches (S epuede convertir en otra fase de aprendizaje en un futuro)
    float velocidad;
    float angulo;
    float tiempo;
    
    
    @Override
    public void PrepararEjecucion(Object... params){
        super.PrepararEjecucion(params[0], params[1], params[2]);
        
        System.out.println(params.length);
        
        cocheDelante = (Vector3f)params[3];
        cocheAtras = (Vector3f)params[4];
    } 

    @Override
    public boolean FaseCompletada() {
        return completado;
    }

    @Override
    public void Ejecucion() {
        
        System.out.println("ENTRO");
        
        ConseguirDatos();
        
        PonerDatosCoche();
        
        while(agente.Tiempo() > 0){
            System.out.println("No muero :D");
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(FaseEjecucionComprobacionTamano.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        //System.out.println("No muero ^_^");
        
        completado = true;
    }
    
    
    void ConseguirDatos(){
        
        distanciaDelante = agente.Spatial().getWorldTranslation().distance(cocheDelante);
        distanciaAtras = agente.Spatial().getWorldTranslation().distance(cocheAtras);
        
        //System.out.println("PoscionDelante: " + distanciaCocheDelante + ", PosicionAtras: " + distanciaCocheAtras);
        
        //System.out.println("DistanciaDelante: " + distanciaCocheDelante + ", DistanciaAtras: " + distanciaCocheAtras);
        
        float anguloaux = 0;
        float tiempoaux = 1;
        float velocidadaux = 0;
        
        try {
            Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
            casoAdecidir.setDataset(casosEntrenamiento);   
            casoAdecidir.setValue(0,  distanciaDelante);
            casoAdecidir.setValue(1,  distanciaAtras);  //alcanzara disntancia de 2.5 deberia dar v=5  
            casoAdecidir.setValue(2,  anguloaux);
            casoAdecidir.setValue(3,  tiempoaux);

            velocidadaux = (float) conocimiento.classifyInstance(casoAdecidir);
        } catch (Exception ex) {
            Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        angulo = anguloaux;
        tiempo = tiempoaux * Datos.tiempoEnManiobraEjecucion;
        velocidad = velocidadaux / Datos.tiempoEnManiobraEjecucion;
    }    
    
    void PonerDatosCoche(){
        agente.Velocidad(velocidad);
        agente.Rotacion(angulo);
        agente.Tiempo(tiempo);
    }

    @Override
    public Object[] Datos() {
        return null;
    }
    
}
