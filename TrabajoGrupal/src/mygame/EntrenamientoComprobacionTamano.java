/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author Jose Santos
 */
public class EntrenamientoComprobacionTamano extends Entrenamiento{

    private float tamanoCoche = 2f;
    private float espacioManiobra = 1f;
    
    Node cocheDelante;
    Node cocheAtras;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;

    public EntrenamientoComprobacionTamano(CocheIA ag, Main m, String ta, int it, Classifier cono) {
        super(ag, m, ta, it, cono);
    }

    
    
  
    
    @Override
    void PreparacionDatos() {
        distanciaCocheDelante = cocheDelante.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
        distanciaCocheAtras = cocheAtras.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
    }

    @Override
    void PreparacionEscenario() {
        cocheDelante = main.CrearCoche();
        cocheAtras = main.CrearCoche();
        
        Random ran = new Random();
        
        float espacio = tamanoCoche + ran.nextFloat() * 2;
        
        cocheDelante.setLocalTranslation(Operaciones.SumarVectores(cocheDelante.getWorldTranslation(), new Vector3f(0,0,espacio/2)));
        cocheAtras.setLocalTranslation(Operaciones.SumarVectores(cocheDelante.getWorldTranslation(), new Vector3f(0,0,-espacio/2)));

    }

    @Override
    void PreparacionAgente() {
                
        Vector3f posicion = Operaciones.SumarVectores(cocheDelante.getWorldTranslation(), cocheAtras.getWorldTranslation()).mult(0.5f);
        
        posicion = Operaciones.SumarVectores(posicion, new Vector3f(1,0,0));
        
        agente.Spatial().setLocalTranslation(posicion);
    }

    @Override
    boolean EsExito() {
        float distanciaNecesaria = tamanoCoche + espacioManiobra;
        float distanciaDisponible = cocheDelante.getWorldTranslation().distance(cocheAtras.getWorldTranslation()) - tamanoCoche;
        
        boolean exito = distanciaDisponible >= distanciaNecesaria;
        
        return exito;
    }

    @Override
    void GuardarExito() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(2, 1);         
        casosEntrenamiento.add(casoAdecidir);
    }

    @Override
    void GuardarFracaso() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(2, 0);         
        casosEntrenamiento.add(casoAdecidir);
    } 

    @Override
    void ReCalculo() {
       
    }
    
}
