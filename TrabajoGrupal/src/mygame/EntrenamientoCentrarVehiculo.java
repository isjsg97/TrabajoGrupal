/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author MykexMP
 */
public class EntrenamientoCentrarVehiculo extends Entrenamiento {
    
    
    float tamanoCoche = 2f;
    float espacioManiobra = 1f;
    
    Vector3f poscocheia;
    
    Vector3f cocheDelante;
    Vector3f cocheAtras;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;
    
    float velocidad;
    float angulo = 0;
    
    float tiempo = 2;

    
    float espacio;
    
    public EntrenamientoCentrarVehiculo(CocheIA ag, Main m, String ta, int it, Classifier cono) {
        super(ag, m, ta, it, cono);
    }
    
    @Override
    void PreparacionEscenario() {
        
    }

    @Override
    void PreparacionAgente() {  
        
        //Posicion CocheIA
        Random ran2 = new Random();       
        float t = ran2.nextFloat();
        float aux = (1-t)*espacio/2 - t*espacio/2;
        
        poscocheia = new Vector3f(0,0,aux);
    }

    @Override
    void PreparacionDatos() {
        
        Random ran = new Random();
        espacio = Operaciones.EspacioMinimoAleatorio(); //comprobar que sea mayor que espacio necesario
        
        cocheDelante = new Vector3f(0,0,espacio/2);
        cocheAtras = new Vector3f(0,0,-espacio/2);
        
        distanciaCocheDelante = cocheDelante.distance(poscocheia);
        distanciaCocheAtras = cocheAtras.distance(poscocheia);
        
        Vector3f posfinal = Operaciones.SumarVectores(cocheAtras, cocheDelante).mult(0.5f);
        //Vector3f poscoche = agente.Spatial().getWorldTranslation();
        
        float poszcoche = -espacio/2 + tamanoCoche + ran.nextFloat() * 2;
        Vector3f poscoche = Operaciones.SumarVectores(cocheAtras, new Vector3f(0,0, poszcoche));
        
        float distancia = posfinal.distance(poscoche);
        
        velocidad = distancia / tiempo;
        
        
        System.out.println("Velocidad: " + velocidad);
    }

    //Es Regresion esto no se usa
    @Override
    boolean EsExito() {
        return Math.abs(cocheDelante.distance(poscocheia) - cocheAtras.distance(poscocheia)) < 0.25f;
    }

    

    @Override
    void Guardar() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(2, angulo);
        casoAdecidir.setValue(3, tiempo);
        casoAdecidir.setValue(4, velocidad);       
        casosEntrenamiento.add(casoAdecidir);
    }


    @Override
    void Entrenamiento() {
        for(int i = 0; i < iteraciones; i++){
            
            PreparacionEscenario();
            
            PreparacionAgente();
            
            PreparacionDatos();
            
            Guardar();
  
        }
    }
    
}
