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
 * @author Jose Santos
 */
public class Coche extends RigidBodyControl implements PhysicsTickListener, PhysicsCollisionListener{

    boolean fisica;
    
    private float rotRuedas;
    private float velocidad;
    private float tiempo;
    
    private PhysicsCollisionEvent colision;
    
    private Spatial spatial;
    
    public Coche(float masa, Spatial spat){
        super(masa);
        spatial = spat;
        
        tiempo = 0;
        
        colision = null;
        fisica = false;
        
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
        
        if(col != null)
        System.out.println(col.getNodeB().getName());
    }
    public PhysicsCollisionEvent Colision(){
        return colision;
    }
    
    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
        //System.out.println("TICK");
        if(tiempo > 0 && colision == null && !fisica){
            Avanzar(tpf);
            tiempo -= tpf;
            SincronizarRigidboyTransform();
        }else{
            //this.setLinearVelocity(new Vector3f(0,0,0));
        }
        
        //System.out.println("Velocidad: " + this.getLinearVelocity());
        
        //fisica = !fisica;
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        
        if(!event.getNodeB().getName().equals("Coche")){
            
            //System.out.println("A: " + event.getNodeA().getName());
            //System.out.println("B: " + event.getNodeB().getName());
            
            return;
        }
        
        /*if(event.get){
            
        }*/
        System.out.println("CHOQUE");
        Colision(event);
    }
    
    
    
    void Avanzar(float tpf){
        
       RigidBodyControl fisicaCoche = this; 
         
        Vector3f mivel = new Vector3f(0,0,velocidad);
        float[] ang = new float[3];
        float roty = spatial.getWorldRotation().toAngles(ang)[1];
        
        //System.out.println("Rot y: " + roty);
        //System.out.println("Rot y quaternion: " + spatial.getWorldRotation());
        
        mivel = Operaciones.RotarVectorY(mivel, Operaciones.RadtoDeg(-roty));
        
        //fisicaCoche.setLinearVelocity(mivel.mult(tpf));
        
        fisicaCoche.setPhysicsLocation(Operaciones.SumarVectores(fisicaCoche.getPhysicsLocation(), mivel.mult(tpf)));
        
        float rotacion = velocidad * (float)Math.sin(Operaciones.DegtoRad(rotRuedas)) * tpf;
        spatial.rotate(0, rotacion, 0);
        
        Quaternion rotCoche = fisicaCoche.getPhysicsRotation();
        
        float [] rotCocheArray = new float[3];
        
        rotCocheArray = rotCoche.toAngles(rotCocheArray);       
        fisicaCoche.setPhysicsRotation(new Quaternion().fromAngles(0,rotCocheArray[1]+rotacion,0));
        
    }
    
    void SincronizarRigidboyTransform(){
        
        //Obtengo el transform del RigidBody
        Quaternion rotRigid = getPhysicsRotation();
        Vector3f posRigid = getPhysicsLocation();
        
        //Sincronizo el Spatial con su RigidBody
        spatial.setLocalRotation(rotRigid);
        spatial.setLocalTranslation(posRigid);
    }
}
