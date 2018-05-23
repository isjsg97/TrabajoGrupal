/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Jose Santos
 */
public class CambioTransform {
    
    Spatial spatial;
    Vector3f pos;
    Quaternion rot;
    
    public CambioTransform(Spatial s, Vector3f p, Quaternion r){
        spatial = s;
        pos = p;
        rot = r;
    }
    
    public void Cambiar(){
        
        RigidBodyControl rg = spatial.getControl(RigidBodyControl.class);
        
        if(pos != null){
            spatial.setLocalTranslation(pos);
            
            
             
            if(rg!= null){
                rg.setPhysicsLocation(pos);
            }
        }
        
        if(rot != null){
            spatial.setLocalRotation(rot);
            
            if(rg!= null){
                rg.setPhysicsRotation(rot);
            }
        }
    }
    
}
