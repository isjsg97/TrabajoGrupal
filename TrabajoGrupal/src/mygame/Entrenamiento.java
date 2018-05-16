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
    
    public Entrenamiento(CocheIA ag){
        agente = ag;
    }
    
    
    abstract void PreparacionEscenario();
    
    abstract void PreparacionAgente();
    
    abstract boolean EsExito();
    
    abstract void GuardarExito();
    
    abstract void GuardarFracaso();
    
    public void Entrenar(){
        
    }
    
}
