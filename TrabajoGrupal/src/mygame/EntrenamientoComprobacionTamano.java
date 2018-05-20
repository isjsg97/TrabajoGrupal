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
    
    Vector3f cocheDelante;
    Vector3f cocheAtras;
    
    Vector3f poscocheia;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;

    public EntrenamientoComprobacionTamano(CocheIA ag, Main m, String ta, int it, Classifier cono) {
        super(ag, m, ta, it, cono);
    }

    
    
  
    
    @Override
    void PreparacionDatos() {
        
        Random ran = new Random();
        
        //tamanoCoche * 2 ya que los puntos están a mitad de los coches y entonces hay que añadrile un tamanoCoche debido a la suma de las mitades de los dos coches
        float espacio = tamanoCoche  * 2 + ((ran.nextFloat() * 2) - 1) * 2; 
        
        cocheDelante = new Vector3f(0,0,espacio/2);
        cocheAtras = new Vector3f(0,0,-espacio/2);
        
        distanciaCocheDelante = cocheDelante.distance(poscocheia);
        distanciaCocheAtras = cocheAtras.distance(poscocheia);
    }

    @Override
    void PreparacionEscenario() {

    }

    @Override
    void PreparacionAgente() {
                
        /*Vector3f posicion = Operaciones.SumarVectores(cocheDelante, cocheAtras).mult(0.5f);
        
        posicion = Operaciones.SumarVectores(posicion, new Vector3f(1,0,0));
        
        agente.Spatial().setLocalTranslation(posicion);*/
        
        poscocheia = new Vector3f(1,0,0);
    }

    @Override
    boolean EsExito() {
        float distanciaNecesaria = tamanoCoche + espacioManiobra;
        float distanciaDisponible = cocheDelante.distance(cocheAtras) - tamanoCoche;
        
        boolean exito = distanciaDisponible >= distanciaNecesaria;
        
        
        
        System.out.println("DistanciaNecesaria: " + distanciaNecesaria);
        System.out.println("DistanciaDisponible: " + distanciaDisponible);
                
        System.out.println("Exito: " + exito);
        
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
