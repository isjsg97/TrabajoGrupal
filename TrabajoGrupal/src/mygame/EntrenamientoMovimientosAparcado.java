/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author Jose Santos
 */
public class EntrenamientoMovimientosAparcado extends Entrenamiento{

    
    float tiempoEnManiobraEntrenando = 0.5f;
    float tiempoEnManiobraEjecucion = 5;
    float multTiempo = tiempoEnManiobraEntrenando / tiempoEnManiobraEjecucion;
    
    
    Spatial cocheDelante;
    Spatial cocheAtras;
    
    float distanciaCocheDelante;
    float distanciaCocheAtras;
    
    int fase;
    
    float velocidad1;
    float angulo1;
    float tiempo1;
    
    float velocidad2;
    float angulo2;
    float tiempo2;
    
    float velocidad3;
    float angulo3;
    float tiempo3;
    
    float velocidad4 = 0; //Se acabo, se para
    float angulo4 = 0;
    float tiempo4 = 0;
    
    
    
    public EntrenamientoMovimientosAparcado(CocheIA ag, Main m, String ta, int it, Classifier cono, Spatial cDelante, Spatial cAtras) {
        super(ag, m, ta, it, cono);
        
        fase = 0;
        
        cocheDelante = cDelante;
        cocheAtras = cAtras;
    }

    @Override
    void PreparacionEscenario() {
        
        
        
        Random ran = new Random();
        
        //tamanoCoche * 2 ya que los puntos están a mitad de los coches y entonces hay que añadrile un tamanoCoche debido a la suma de las mitades de los dos coches
        float espacio = Operaciones.EspacioMinimoAleatorio(); 
        
        Main.SetPosicion(cocheDelante, new Vector3f(0,0,espacio/2));
        Main.SetPosicion(cocheAtras, new Vector3f(0,0,-espacio/2));
        
        //cocheDelante.setLocalTranslation(new Vector3f(0,0,espacio/2));
        //cocheAtras.setLocalTranslation(new Vector3f(0,0,-espacio/2));
        
                
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
    }

    @Override
    void PreparacionAgente() {
        //agente.Spatial().setLocalTranslation(Datos.PosInicial());
        Main.SetPosicion(agente.Spatial(),Datos.PosInicial());
        Main.SetRotacion(agente.Spatial(),new Quaternion().fromAngles(0, 0, 0));
        
                
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
    }

    @Override
    void PreparacionDatos() {
        distanciaCocheDelante = cocheDelante.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
        distanciaCocheAtras = cocheAtras.getWorldTranslation().distance(agente.Spatial().getWorldTranslation());
        
        
        
    }

    @Override
    boolean EsExito() {
        boolean res;
        
        //res = agente.Tiempo() <= 0 && agente.Colision() == null;
        
        float [] rotCocheDelanteArray = new float[3]; 
        rotCocheDelanteArray = cocheDelante.getWorldRotation().toAngles(rotCocheDelanteArray);
        
        float [] rotCocheAtrasArray = new float[3]; 
        rotCocheAtrasArray = cocheAtras.getWorldRotation().toAngles(rotCocheAtrasArray);
        
        float mediarot = (Operaciones.RadtoDeg(rotCocheDelanteArray[1]) + Operaciones.RadtoDeg(rotCocheAtrasArray[1])) / 2;
        
        float [] rotAgenteArray = new float[3]; 
        rotAgenteArray = agente.Spatial().getWorldRotation().toAngles(rotAgenteArray);
        
        float rotagente = Operaciones.RadtoDeg(rotAgenteArray[1]);
        
        float mediaposx = (cocheDelante.getWorldTranslation().x + cocheAtras.getWorldTranslation().x) / 2;
        float miposx = agente.Spatial().getWorldTranslation().x;
        
        res = Math.abs(mediarot - rotagente) < 10 && Math.abs(mediaposx - miposx) < 0.5f;
        
        System.out.println("Rotacion ideal: " + mediarot + ", Rotacion actual: " + rotagente + ", Resultado: " + res);
        
        return res;
    }

    
    void ReCalculo() {
        
    }

    
    void GuardarExito() {
        
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);
        
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        
        if(fase <= 1){
            casoAdecidir.setValue(2, velocidad1);
            casoAdecidir.setValue(3, angulo1);
            casoAdecidir.setValue(4, tiempo1);
        }
        
        if(fase <= 2){
            casoAdecidir.setValue(5, velocidad2);
            casoAdecidir.setValue(6, angulo2);
            casoAdecidir.setValue(7, tiempo2);
        }
        
        if(fase <= 3){
            casoAdecidir.setValue(8, velocidad3);
            casoAdecidir.setValue(9, angulo3);
            casoAdecidir.setValue(10, tiempo3);
        }
        
        if(fase <= 4){
            casoAdecidir.setValue(11, velocidad4);
            casoAdecidir.setValue(12, angulo4);
            casoAdecidir.setValue(13, tiempo4);
        }

        
        casoAdecidir.setValue(14, 1);         
        casosEntrenamiento.add(casoAdecidir);
        
    }

   //Mirar Condiciones ifs
    void GuardarFracaso() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);
        
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        
        if(fase <= 1){
            casoAdecidir.setValue(2, velocidad1);
            casoAdecidir.setValue(3, angulo1);
            casoAdecidir.setValue(4, tiempo1);
        }
        
        if(fase <= 2){
            casoAdecidir.setValue(5, velocidad2);
            casoAdecidir.setValue(6, angulo2);
            casoAdecidir.setValue(7, tiempo2);
        }
        
        if(fase <= 3){
            casoAdecidir.setValue(8, velocidad3);
            casoAdecidir.setValue(9, angulo3);
            casoAdecidir.setValue(10, tiempo3);
        }
        
        if(fase <= 4){
            casoAdecidir.setValue(11, velocidad4);
            casoAdecidir.setValue(12, angulo4);
            casoAdecidir.setValue(13, tiempo4);
        }

        
        casoAdecidir.setValue(14, 0);         
        casosEntrenamiento.add(casoAdecidir);
    }
    
    
    boolean FaseCompletada(){
        boolean res;
        
        res = agente.Tiempo() <= 0 || agente.Colision() != null;
        
        return res;
    }
    
    
    
    

    
    void Planificacion() {
        
    }

    
    boolean FaseExito() {
        boolean res;
        
        res = agente.Tiempo() <= 0 && agente.Colision() == null;
        
        return res;
    }
    
    
    void PreparacionFase() {
        
    }

    @Override
    void Guardar() {
        
    }

    @Override
    void Entrenamiento() {
        
        for(int iter = 0; iter < iteraciones; iter++){
            
            System.out.println("SIGUIENTE ITERACION");
            
            PreparacionEscenario();
            
            //Arreglar Posicion Inicial
            PreparacionAgente();
            
            Fase1();
            
            for(fase = 0; fase < 4; fase++){
                
                
                try {
                    Thread.sleep(Datos.tiempoEsperaThread);
                }catch (InterruptedException ex) {
                    Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
                
            }
        }
        
    }
    
    
    //float distancia;
    
    Vector3f posAgenteInicialFase1;
    Quaternion rotAgenteInicialFase1;
    
    void PreFase1(){
        fase = 1;
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase1);
        Main.SetRotacion(agente.Spatial(), rotAgenteInicialFase1);
        agente.Tiempo(0);
        agente.Colision(null);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
    }
    
    void Fase1(){ //Se situa el agente al lado del coche de delante
        
        posAgenteInicialFase1 = agente.Spatial().getWorldTranslation().clone();
        rotAgenteInicialFase1 = agente.Spatial().getWorldRotation().clone();
        
        for(float distancia = -Datos.tamanoCoche/2; distancia < Datos.tamanoCoche/2; distancia += 0.5f){
            PreFase1();
            
            //System.out.pr
            
            Vector3f posFinal = Operaciones.SumarVectores(cocheDelante.getWorldTranslation(), new Vector3f(0,0,distancia));
            posFinal.x = Datos.offsetPocAgenteX;
            
            float distAPuntoFinal = agente.Spatial().getWorldTranslation().distance(posFinal);
            
            velocidad1 = distAPuntoFinal / tiempoEnManiobraEntrenando;
            tiempo1 = tiempoEnManiobraEntrenando;
            angulo1 = 0;
            
            
            System.out.println("Velocidad1 : " + velocidad1);
            System.out.println("Tiempo1 : " + tiempo1);
            
            agente.Velocidad(velocidad1);
            agente.Rotacion(angulo1);
            agente.Tiempo(tiempo1);
            
            
            while(agente.Tiempo() > 0 && agente.Colision() == null){
                try {
                    Thread.sleep(Datos.tiempoEsperaThread);
                }catch (InterruptedException ex) {
                    Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
            }
            
            Fase2();
            
        }
    }
    
    Vector3f posAgenteInicialFase2;
    Quaternion rotAgenteInicialFase2;
    void PreFase2(){
        fase = 2;
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase2);
        Main.SetRotacion(agente.Spatial(), rotAgenteInicialFase2);
        agente.Tiempo(0);
        agente.Colision(null);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
    }
    
    void Fase2(){ //Mete el culo del coche
        
        posAgenteInicialFase2 = agente.Spatial().getWorldTranslation().clone();
        rotAgenteInicialFase2 = agente.Spatial().getWorldRotation().clone();
        
        for(float velocidad = 0.5f ; velocidad < 1; velocidad += 0.5f){
            for(float angulo = 5; angulo < 30; angulo += 5){
                PreFase2();
                
                velocidad2 = -velocidad / multTiempo;
                tiempo2 = tiempoEnManiobraEntrenando;
                angulo2 = -angulo;
                
                agente.Velocidad(velocidad2);
                agente.Rotacion(angulo2);
                agente.Tiempo(tiempo2);
                
                while(agente.Tiempo() > 0 && agente.Colision() == null && agente.Spatial().getWorldTranslation().x > -1){
                    
                     System.out.println(agente.Spatial().getWorldTranslation());
                    
                    if(agente.Spatial().getWorldTranslation().x <= -1){
                        System.out.println("ME HE PASADO");
                        
                    }
                    
                    try {
                        Thread.sleep(Datos.tiempoEsperaThread);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("La hebra ce mamó");
                    }
                }
                
                agente.Tiempo(0);
                
                if(agente.Colision() == null){
                    Fase3();
                }
            } 
        }
    }
    
    Vector3f posAgenteInicialFase3;
    Quaternion rotAgenteInicialFase3;
    void PreFase3(){
        fase = 3;
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase3);
        Main.SetRotacion(agente.Spatial(), rotAgenteInicialFase3);
        agente.Tiempo(0);
        agente.Colision(null);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
    }
    
    void Fase3(){ //Se alinea con los otros coches
        
        posAgenteInicialFase3 = agente.Spatial().getWorldTranslation().clone();
        rotAgenteInicialFase3 = agente.Spatial().getWorldRotation().clone();
        
        for(float velocidad = 0.5f ; velocidad < 1.5f; velocidad += 0.5f){
            for(float angulo = 5; angulo < 30; angulo += 5){
                PreFase3();
                
                velocidad3 = -velocidad / multTiempo;
                tiempo3 = tiempoEnManiobraEntrenando;
                angulo3 = angulo;
                
                agente.Velocidad(velocidad3);
                agente.Rotacion(angulo3);
                agente.Tiempo(tiempo3);
                
                while(agente.Tiempo() > 0 && agente.Colision() == null){
                    try {
                        Thread.sleep(Datos.tiempoEsperaThread);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("La hebra ce mamó");
                    }
                }
                
                if(agente.Colision() == null && EsExito()){
                    
                    System.out.print("TRUE");
                    agente.Tiempo(0);
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("La hebra ce mamó");
                    }
                }
            } 
        }
    }
}
