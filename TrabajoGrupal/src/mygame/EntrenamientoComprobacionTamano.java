/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author Jose Santos
 */
public class EntrenamientoComprobacionTamano extends Entrenamiento{

    private float tamanoCoche = 2f;
    private float espacioManiobra = 1f;
    
    Node cocheDelante;
    Node cocheAtras;
    
    public EntrenamientoComprobacionTamano(CocheIA ag, Main m) {
        super(ag, m);
    }
    
    @Override
    void PreparacionDatos() {
        
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
        
    }

    @Override
    void GuardarFracaso() {
        
    } 

    @Override
    void ReCalculo() {
       
    }
    
}
