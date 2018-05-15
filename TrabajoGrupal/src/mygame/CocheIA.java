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

/**
 *
 * @author josag
 */
public class CocheIA extends RigidBodyControl implements PhysicsTickListener, PhysicsCollisionListener{

    
    private Vector3f velocidad;
    private float velocidadmax;
    
    private Quaternion rotacion;
    
    public CocheIA(float velmax){
        velocidadmax = velmax;
    }
    
    public void Velocidad(Vector3f vel){
        velocidad = vel;
    }
    
    public Vector3f Velocidad(){
        return velocidad;
    }
    
    public void Rotacion(Quaternion rot){
        rotacion = rot;
    }
    
    public Quaternion Rotacion(){
        return rotacion;
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
