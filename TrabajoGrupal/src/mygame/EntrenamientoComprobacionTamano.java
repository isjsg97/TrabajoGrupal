/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author Jose Santos
 */
public class EntrenamientoComprobacionTamano extends Entrenamiento{

    
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
        distanciaCocheDelante = cocheDelante.distance(poscocheia);
        distanciaCocheAtras = cocheAtras.distance(poscocheia);
    }

    @Override
    void PreparacionEscenario() {
        
        Random ran = new Random();
        
        //tamanoCoche * 2 ya que los puntos están a mitad de los coches y entonces hay que añadrile un tamanoCoche debido a la suma de las mitades de los dos coches
        float espacio = Operaciones.EspacioAleatorio(); 
        
        cocheDelante = new Vector3f(0,0,espacio/2);
        cocheAtras = new Vector3f(0,0,-espacio/2);
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
        float distanciaNecesaria = Datos.DistanciaNecesaria();
        float distanciaDisponible = cocheDelante.distance(cocheAtras);
        
        boolean exito = distanciaDisponible >= distanciaNecesaria;
        
        return exito;
    }

    @Override
    void Guardar() {
        
        int res = EsExito() ? 1 : 0;
        
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);   
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        casoAdecidir.setValue(2, res);         
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
