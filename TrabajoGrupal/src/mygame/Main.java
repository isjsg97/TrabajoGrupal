package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        rootNode.attachChild(CrearNave());
        
        //C# UNITY MICROSOFT VISUAL STUDIO WINDOWS INTEL NVIDIA <3 <3 <3 <3
    }

    
    public Node CrearNave(){
        
        Node res = new Node();
        
        Spatial buggy = assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        buggy.setLocalTranslation(0, 0.75f, 0);
        
        //geometrybuggy = (Geometry) assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        Material mat = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        buggy.scale(3f);
        buggy.setMaterial(mat);
        //geometrybuggy.setLocalTranslation(0, -1f, 0);
        res.attachChild(buggy);
        
        return res;
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
