/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author josag
 */
public abstract class Entrenamiento {
    
    CocheIA agente;
    Main main;
    
    public Entrenamiento(CocheIA ag, Main m){
        agente = ag;
        main = m;
    }
    
    
    abstract void PreparacionEscenario();
    
    abstract void PreparacionAgente();
    
    abstract void PreparacionDatos();
    
    abstract boolean EsExito();
    
    abstract void ReCalculo();
    
    abstract void GuardarExito();
    
    abstract void GuardarFracaso();
    
    public void Entrenar(){
        
    }
    
}
