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
import com.jme3.scene.Spatial;

/**
 *
 * @author Jose Santos
 */
public class Coche extends RigidBodyControl implements PhysicsTickListener, PhysicsCollisionListener{

    
    private float velocidadmax;
    
    private float rotRuedas;
    private float velocidad;
    private float tiempo;
    
    private PhysicsCollisionEvent colision;
    
    private Spatial spatial;
    
    public Coche(float velmax, Spatial spat){
        velocidadmax = velmax;
        spatial = spat;
        
        tiempo = 0;
        
        colision = null;
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
    
    public void Colision(PhysicsCollisionEvent col){
        colision = col;
    }
    public PhysicsCollisionEvent Colision(){
        return colision;
    }
    
    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
        if(tiempo > 0 && colision == null){
            Avanzar(tpf);
            tiempo -= tpf;
            SincronizarRigidboyTransform();
        }
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        Colision(event);
    }
    
    
    
    void Avanzar(float tpf){
        
    }
    
    void SincronizarRigidboyTransform(){
        
    }
}
