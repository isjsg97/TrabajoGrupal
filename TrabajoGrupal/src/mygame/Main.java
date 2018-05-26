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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
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
    
    
    CocheIA agente;

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
        
      
        //fisicaCocheIA = cocheIA.getControl(RigidBodyControl.class);
        
        
        /*CocheIA cocheIAScript = CrearCocheIA(new Vector3f(1,4,0));
        //CrearCocheIA(new Vector3f(1,0,0));/*
        Entrenamiento entrenamiento = new EntrenamientoComprobacionTamano(cocheIAScript, this, "ComprobacionTamano", 100, ObtenerClasificador());
        entrenamiento.Entrenar();
        
        /**/
        
        //Entrenar(1);
        Ejecutar();
        
        /*Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        rootNode.attachChild(CrearCoche());*/
        
        
        //CrearCoche(false, new Vector3f(0,0,0));
        //CrearCoche(false, new Vector3f(0,0,2));
        
        
        //C# UNITY MICROSOFT VISUAL STUDIO WINDOWS INTEL NVIDIA <3 <3 <3 <3
    }
    
    
    public CocheIA CrearCocheIA(Vector3f pos){
        
        //Crear Rigidbody
        
        Spatial coche = CrearCoche(true, pos);
        
        CocheIA res = new CocheIA(1, coche);
        estadosFisicos.getPhysicsSpace().addCollisionListener(res);
         
        coche.addControl(res);
        estadosFisicos.getPhysicsSpace().add(res);
        
        return res;
    }
    
    public Spatial CrearCoche(boolean esIA, Vector3f pos){
        
        System.out.println("Creo coche en: " + pos);
        
        //Spatial res = new Node("Coche");
        
        Spatial buggy = assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        Spatial res = buggy;
        res.setName("Coche");
        
        buggy.setLocalTranslation(0, 0.15f, 0);
        
        //geometrybuggy = (Geometry) assetManager.loadModel("/Models/Buggy/Buggy.j3o");
        
        Material mat = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        //Material mat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //mat.setBoolean("UseMaterialColors",true);
        //mat.setColor ("Color", ColorRGBA.Blue);
        buggy.scale(0.30f);
        buggy.setMaterial(mat);
        //geometrybuggy.setLocalTranslation(0, -1f, 0);
        //res.attachChild(buggy);
        
        
        res.setLocalTranslation(pos);
        
        rootNode.attachChild(res);
        
        if(!esIA){
        
            //Crear Rigidbody
            RigidBodyControl fisica = new RigidBodyControl(10000); //creación la fisicaBola con masa 1 Kg
            res.addControl( fisica ); //asociación entre geometry y física de bola - sin material bola_geo.addControl( fisicaBola ); //asociación entre geometry y física de bola estadosFisicos.getPhysicsSpace().add( fisicaBola ); //integración de fisicaBola en entorno físico
            fisica.setRestitution(0.9f);
            res.addControl(fisica);
            estadosFisicos.getPhysicsSpace().add( fisica );
            
            fisica.setPhysicsLocation(pos);
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
        Box b = new Box(100, 0.1f, 100);
        Geometry geom = new Geometry("Terreno", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);

        geom.setLocalTranslation(0, -0.1f, 0);
        
        
        RigidBodyControl fisica = new RigidBodyControl(0); //creación la fisicaBola con masa 1 Kg
        geom.addControl( fisica ); //asociación entre geometry y física de bola - sin material bola_geo.addControl( fisicaBola ); //asociación entre geometry y física de bola estadosFisicos.getPhysicsSpace().add( fisicaBola ); //integración de fisicaBola en entorno físico
        fisica.setRestitution(0.9f);
        geom.addControl(fisica);
        estadosFisicos.getPhysicsSpace().add( fisica );
            
        fisica.setPhysicsLocation(geom.getWorldTranslation());
        
        rootNode.attachChild(geom);
    }
    
    void PonerCamara(){
        //cam.setLocation(new Vector3f(0,10,0));
        cam.setLocation(new Vector3f(2,0.1f,0));
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
        
        cam.setRotation(new Quaternion().fromAngles(Operaciones.DegtoRad(90), Operaciones.DegtoRad(-90), 0));
        
        this.setDisplayFps(false);
    }
    
    void CamaraSeguirAgente(){
        Vector3f offset = new Vector3f(Datos.offsetPocAgenteX/2, 0, 0);
        Vector3f posFinal = Operaciones.SumarVectores(offset, agente.Spatial().getWorldTranslation());
        posFinal.y = 10;
        
        cam.setLocation(posFinal);
    }
    
    public static void Destruir(Spatial nodo){
        nodo.removeFromParent();
    }
    
   /*
    public static void Crear(Spatial nodo){
        rootNodeaux.attachChild(nodo);
    }*/
    
    
    @Override
    public void simpleUpdate(float tpf) {
        if(agente != null){
            agente.physicsTick(estadosFisicos.getPhysicsSpace(), tpf);
            CamaraSeguirAgente();
        }
        
        if(cambiospendientes){
            
            
            /*Iterator<CambioTransform> iter = cambios.iterator();

            while (iter.hasNext()) {
                CambioTransform cambio = iter.next();
                
                cambio.Cambiar();
                
                iter.remove();
            }*/
            
           for(CambioTransform cambio : cambios){
                cambio.Cambiar();
            }
            
            cambios.clear();
            
            cambiospendientes = false;
        }
        
    }
    
    public static boolean cambiospendientes = false;
    static List<CambioTransform> cambios = new Vector<>();
    
    public static void SetPosicion(Spatial s, Vector3f pos){
        cambios.add(new CambioTransform(s, pos, null));
        
        cambiospendientes = true;
    }
    
    public static void SetRotacion(Spatial s, Quaternion rot){ 
        cambios.add(new CambioTransform(s, null, rot));
        
        cambiospendientes = true;
    }
    
    //---------------------------------ENTRENAMIENTO-----------------------------------------
    void Entrenar(int num){
        Entrenamiento entrenamiento;
        
        if(num == 0){
            agente = CrearCocheIA(Datos.PosInicial());
            entrenamiento = new EntrenamientoComprobacionTamano(agente, this, "ComprobacionTamano", 100, ObtenerClasificador());
        }else if(num == 1){
            agente = CrearCocheIA(Datos.PosInicial());
            Spatial cocheDelante = CrearCoche(false, new Vector3f(0,0,0));
            Spatial cocheAtras = CrearCoche(false, new Vector3f(0,0,0));
            
            entrenamiento = new EntrenamientoMovimientosAparcado(agente, this, "MovimientosAparcado", 10, ObtenerClasificador(), cocheDelante, cocheAtras);
        }else{
            agente = CrearCocheIA(Datos.PosInicial());
            entrenamiento = new EntrenamientoCentrarVehiculo(agente, this, "MovimientosCentrar", 100, ObtenerClasificador());
        }
        
        entrenamiento.Entrenar();
    }

   //------------------------------------------EJECUCION-------------------------------------
    Vector3f[] coches;
   
    void Ejecutar(){
        
        ConseguirListaCoches(10);
        
        agente = CrearCocheIA(Datos.PosInicial());
        
        List<FaseEjecucion> fases = new ArrayList<>();
        fases.add(new FaseEjecucionComprobacionTamano());
        fases.add(new FaseEjecucionMovimientosAparcado());
        
        FaseEjecucion[] fasearray = new FaseEjecucion[fases.size()];
        fasearray = fases.toArray(fasearray);

        
        
        List<String> tablas = new ArrayList<>();
        tablas.add("ComprobacionTamano");
        tablas.add("MovimientosAparcado");
        
        
        String[] tablasarray = new String[tablas.size()];
        tablasarray = tablas.toArray(tablasarray);
        
        Ejecucion ejecucion = new Ejecucion(this, ObtenerClasificador(), fasearray, tablasarray, agente, coches);
        //EjecucionAntiguo ejecucion = new EjecucionAntiguo(10, ObtenerClasificador(), "ComprobacionTamano"); 
       
        ejecucion.Ejecutar();
    }
   
    void ConseguirListaCoches(int num){
        
        coches = new Vector3f[num];
        
        Vector3f pos = new Vector3f(0,0,0);
        
        for(int i = 0; i < coches.length-1; i++){
            
            float despl = Operaciones.EspacioAleatorio();
            //float despl = tamanoCoche  + ran.nextFloat() * (espacioManiobra);
            
            Vector3f vdespl = new Vector3f(0,0,despl);
            
            pos = Operaciones.SumarVectores(pos, vdespl);
            
            coches[i] = pos.clone();
            
            CrearCoche(false, pos);
        }
        
        float despl = Operaciones.EspacioMinimoAleatorio();
            
        Vector3f vdespl = new Vector3f(0,0,despl);
            
        pos = Operaciones.SumarVectores(pos, vdespl);
            
        coches[coches.length - 1] = pos.clone();
        
        CrearCoche(false, pos);
    }
}
