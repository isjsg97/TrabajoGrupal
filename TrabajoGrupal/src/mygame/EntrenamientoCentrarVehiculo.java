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
    
    
    private float tamanoCoche = 2f;
    
    Vector3f poscocheia;
    
    Vector3f cocheDelante;
    Vector3f cocheAtras;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;
    
    float velocidad1;
    float angulo1;
    float tiempo1;
    
    float velocidad2;
    float angulo2;
    float tiempo2;
    
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
        espacio = tamanoCoche  * 2 + (ran.nextFloat() * 2)+ 1; //comprobar que sea mayor que distancia maniobra
        
        cocheDelante = new Vector3f(0,0,espacio/2);
        cocheAtras = new Vector3f(0,0,-espacio/2);
        
        distanciaCocheDelante = cocheDelante.distance(poscocheia);
        distanciaCocheAtras = cocheAtras.distance(poscocheia);
    }

    @Override
    boolean EsExito() {
        return cocheDelante.distance(poscocheia) == cocheAtras.distance(poscocheia);
    }

    @Override
    void GuardarExito() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(2, velocidad1);
        casoAdecidir.setValue(3, angulo1);
        casoAdecidir.setValue(4, tiempo1);
        casoAdecidir.setValue(5, velocidad2);
        casoAdecidir.setValue(6, angulo2);
        casoAdecidir.setValue(7, tiempo2);
        casoAdecidir.setValue(8, 1);       
        casosEntrenamiento.add(casoAdecidir);
    }

    @Override
    void GuardarFracaso() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(3, angulo1);
        casoAdecidir.setValue(4, tiempo1);
        casoAdecidir.setValue(5, velocidad2);
        casoAdecidir.setValue(6, angulo2);
        casoAdecidir.setValue(7, tiempo2);
        casoAdecidir.setValue(8, 0);        
        casosEntrenamiento.add(casoAdecidir);
    }

    @Override
    void Planificacion() {
        
    }
    
    @Override
    void ReCalculo() {
        
    }
    
    @Override
    int NumeroFases() {
        return 1;
    }

    @Override
    boolean FaseCompletada() {
        return true;
    }

    @Override
    boolean FaseExito() {
        return true;
    }

    @Override
    void PreparacionFase() {
        
    }
    
}
