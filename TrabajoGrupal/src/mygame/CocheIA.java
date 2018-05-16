/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author josag
 */
public class CocheIA extends RigidBodyControl implements PhysicsTickListener, PhysicsCollisionListener{

    
    private float velocidadmax;
    
    private float rotRuedas;
    private float velocidad;
    private float tiempo;
    
    private Spatial spatial;
    
    public CocheIA(float velmax, Spatial spat){
        velocidadmax = velmax;
        spatial = spat;
    }
    
    public void Velocidad(float vel){
        velocidad = vel;
    }
    
    public float Velocidad(){
        return velocidad;
    }
    
    public void Rotacion(float rot){
        rotRuedas = rot;
    }
    
    public float Rotacion(){
        return rotRuedas;
    }
    
    public void Tiempo(float time){
        tiempo = time;
    }
    
    public float Tiempo(){
        return tiempo;
    }
    
    public Spatial Spatial(){
        return spatial;
    }
    
    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    void Avanzar(){
        
    }
}
