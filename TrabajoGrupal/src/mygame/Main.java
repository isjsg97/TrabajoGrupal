package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.*;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    
    private BulletAppState estadosFisicos = new BulletAppState();
    private RigidBodyControl fisicaCocheIA;
    

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        stateManager.attach(estadosFisicos);
        
        PonerIluminacion();
        PonerCielo();
        PonerTerreno();
        PonerCamara();
        
        Node cocheIA = CrearCoche(true);
        //fisicaCocheIA = cocheIA.getControl(RigidBodyControl.class);
        CocheIA cocheIAScript = CrearCocheIA(cocheIA);
        
        /*Entrenamiento entrenamiento = new EntrenamientoComprobacionTamano(cocheIAScript, this, "ComprobacionTamano", 100, ObtenerClasificador());
        entrenamiento.Entrenar();*/
        
        /**/
        
        EjecucionAntiguo ejecucion = new EjecucionAntiguo(10, ObtenerClasificador(), "ComprobacionTamano"); 
        ejecucion.Ejecutar();
        
        /*Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        rootNode.attachChild(CrearCoche());*/
        
        //C# UNITY MICROSOFT VISUAL STUDIO WINDOWS INTEL NVIDIA <3 <3 <3 <3
    }
    
    
    public CocheIA CrearCocheIA(Node coche){
        
        //Crear Rigidbody
        
        
        CocheIA res = new CocheIA(1, coche);
        estadosFisicos.getPhysicsSpace().addCollisionListener(res);
        coche.addControl(res);
        
        return res;
    }
    
    public Node CrearCoche(boolean esIA){
        
        Node res = new Node();
        
        Spatial buggy = assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        buggy.setLocalTranslation(0, 0.75f, 0);
        
        //geometrybuggy = (Geometry) assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        Material mat = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        //Material mat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //mat.setBoolean("UseMaterialColors",true);
        //mat.setColor ("Color", ColorRGBA.Blue);
        buggy.scale(1f);
        buggy.setMaterial(mat);
        //geometrybuggy.setLocalTranslation(0, -1f, 0);
        res.attachChild(buggy);
        
        
        if(!esIA){
        
            //Crear Rigidbody
            RigidBodyControl fisica = new RigidBodyControl(0); //creación la fisicaBola con masa 1 Kg
            res.addControl( fisica ); //asociación entre geometry y física de bola - sin material bola_geo.addControl( fisicaBola ); //asociación entre geometry y física de bola estadosFisicos.getPhysicsSpace().add( fisicaBola ); //integración de fisicaBola en entorno físico
            fisica.setRestitution(0.9f);
            res.addControl(fisica);
            estadosFisicos.getPhysicsSpace().add( fisica );
        }
        
        return res;
    }
    
    
    public Classifier ObtenerClasificador(){
        Classifier res; 
        
        //res = new M5P(); //Con este no va bien
        res = new MultilayerPerceptron();
        
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
    
    
    void PonerIluminacion(){
        DirectionalLight sun1 = new DirectionalLight();
        DirectionalLight sun2 = new DirectionalLight();
        sun1.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun2.setDirection((new Vector3f(0.5f, -0.5f, 0.5f)).normalizeLocal());
        sun1.setColor(ColorRGBA.White);
        sun2.setColor(ColorRGBA.Gray);
        rootNode.addLight(sun1);
        rootNode.addLight(sun2);
    }
    
    void PonerCielo(){
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
    
    void PonerTerreno(){
        Box b = new Box(10, 0.1f, 10);
        Geometry geom = new Geometry("Terreno", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);

        geom.setLocalTranslation(0, -0.1f, 0);
        
        rootNode.attachChild(geom);
    }
    
    void PonerCamara(){
        cam.setLocation(new Vector3f(0,30,0));
        //cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
        
        cam.setRotation(new Quaternion().fromAngles(Operaciones.DegtoRad(90), Operaciones.DegtoRad(-90), 0));
        
        this.setDisplayFps(false);
    }
    
   /* public static void Destruir(Spatial nodo){
        nodo.removeFromParent();
    }
    
    public static void Crear(Spatial nodo){
        rootNodeaux.attachChild(nodo);
    }*/

}
