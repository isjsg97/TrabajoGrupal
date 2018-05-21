/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author Jose Santos
 */
public class EntrenamientoMovimientosAparcado extends Entrenamiento{

    float tamanoCoche = 2;
    
    Spatial cocheDelante;
    Spatial cocheAtras;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;
    
    int fase;
    
    float velocidad1;
    float angulo1;
    float tiempo1;
    
    float velocidad2;
    float angulo2;
    float tiempo2;
    
    float velocidad3;
    float angulo3;
    float tiempo3;
    
    float velocidad4;
    float angulo4;
    float tiempo4;
    
    
    
    public EntrenamientoMovimientosAparcado(CocheIA ag, Main m, String ta, int it, Classifier cono) {
        super(ag, m, ta, it, cono);
        
        fase = 0;
    }

    @Override
    void PreparacionEscenario() {
        
        cocheDelante = main.CrearCoche(false);
        cocheAtras = main.CrearCoche(false);
        
        Random ran = new Random();
        
        //tamanoCoche * 2 ya que los puntos están a mitad de los coches y entonces hay que añadrile un tamanoCoche debido a la suma de las mitades de los dos coches
        float espacio = tamanoCoche  * 2  + 1 + ran.nextFloat() * 2; 
        
        cocheDelante.setLocalTranslation(new Vector3f(0,0,espacio/2));
        cocheAtras.setLocalTranslation(new Vector3f(0,0,-espacio/2));
    }

    @Override
    void PreparacionAgente() {
        agente.Spatial().setLocalTranslation(new Vector3f(1,0,0));
    }

    @Override
    void PreparacionDatos() {
        distanciaCocheDelante = cocheDelante.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
        distanciaCocheAtras = cocheAtras.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
        
        
        
    }

    @Override
    boolean EsExito() {
        boolean res;
        
        res = agente.Tiempo() <= 0 && agente.Colision() == null;
        
        return res;
    }

    @Override
    void ReCalculo() {
        
    }

    @Override
    void GuardarExito() {
        
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);
        
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        
        if(fase <= 1){
            casoAdecidir.setValue(2, velocidad1);
            casoAdecidir.setValue(3, angulo1);
            casoAdecidir.setValue(4, tiempo1);
        }
        
        if(fase <= 2){
            casoAdecidir.setValue(5, velocidad2);
            casoAdecidir.setValue(6, angulo2);
            casoAdecidir.setValue(7, tiempo2);
        }
        
        if(fase <= 3){
            casoAdecidir.setValue(8, velocidad3);
            casoAdecidir.setValue(9, angulo3);
            casoAdecidir.setValue(10, tiempo3);
        }
        
        if(fase <= 4){
            casoAdecidir.setValue(11, velocidad4);
            casoAdecidir.setValue(12, angulo4);
            casoAdecidir.setValue(13, tiempo4);
        }

        
        casoAdecidir.setValue(14, 1);         
        casosEntrenamiento.add(casoAdecidir);
        
    }

    @Override
    void GuardarFracaso() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);
        
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        
        if(fase <= 1){
            casoAdecidir.setValue(2, velocidad1);
            casoAdecidir.setValue(3, angulo1);
            casoAdecidir.setValue(4, tiempo1);
        }
        
        if(fase <= 2){
            casoAdecidir.setValue(5, velocidad2);
            casoAdecidir.setValue(6, angulo2);
            casoAdecidir.setValue(7, tiempo2);
        }
        
        if(fase <= 3){
            casoAdecidir.setValue(8, velocidad3);
            casoAdecidir.setValue(9, angulo3);
            casoAdecidir.setValue(10, tiempo3);
        }
        
        if(fase <= 4){
            casoAdecidir.setValue(11, velocidad4);
            casoAdecidir.setValue(12, angulo4);
            casoAdecidir.setValue(13, tiempo4);
        }

        
        casoAdecidir.setValue(14, 0);         
        casosEntrenamiento.add(casoAdecidir);
    }
    
    @Override
    boolean FaseCompletada(){
        boolean res;
        
        res = agente.Tiempo() <= 0 || agente.Colision() != null;
        
        return res;
    }
    
    @Override
    int NumeroFases(){
        return 4;
    }
    

    @Override
    void Planificacion() {
        
    }

    @Override
    boolean FaseExito() {
        boolean res;
        
        res = agente.Tiempo() <= 0 && agente.Colision() == null;
        
        return res;
    }
    
}
