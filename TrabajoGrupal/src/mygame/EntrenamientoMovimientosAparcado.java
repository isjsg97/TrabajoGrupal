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

    
    float tiempoEnManiobraEntrenando = 0.25f;
    float multTiempo = tiempoEnManiobraEntrenando / Datos.tiempoEnManiobraEjecucion;
    
    
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
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        
        Main.SetPosicion(cocheDelante, new Vector3f(0,0,espacio/2));
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
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
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        //agente.Spatial().setLocalTranslation(Datos.PosInicial());
        Main.SetPosicion(agente.Spatial(),Datos.PosInicial());
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        Main.SetRotacion(agente.Spatial(),Datos.RotInicial());
        
                
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
        
        //Rotacion
        float [] rotCocheDelanteArray = new float[3]; 
        rotCocheDelanteArray = cocheDelante.getWorldRotation().toAngles(rotCocheDelanteArray);
        
        float [] rotCocheAtrasArray = new float[3]; 
        rotCocheAtrasArray = cocheAtras.getWorldRotation().toAngles(rotCocheAtrasArray);
        
        float mediarot = (Operaciones.RadtoDeg(rotCocheDelanteArray[1]) + Operaciones.RadtoDeg(rotCocheAtrasArray[1])) / 2;
        
        float [] rotAgenteArray = new float[3]; 
        rotAgenteArray = agente.Spatial().getWorldRotation().toAngles(rotAgenteArray);
        
        float rotagente = Operaciones.RadtoDeg(rotAgenteArray[1]);
        
        //Posicion
        
        float mediaposx = (cocheDelante.getWorldTranslation().x + cocheAtras.getWorldTranslation().x) / 2;
        float miposx = agente.Spatial().getWorldTranslation().x;
        
        /*float mediaposz = (cocheDelante.getWorldTranslation().z + cocheAtras.getWorldTranslation().z) / 2;
        float miposz = agente.Spatial().getWorldTranslation().z;*/
        
        res = Math.abs(mediarot - rotagente) < 10 && Math.abs(mediaposx - miposx) < 0.15f;
        
        //System.out.println("Rotacion ideal: " + mediarot + ", Rotacion actual: " + rotagente + ", Resultado: " + res);
        
        
        
        res = res && agente.Colision() == null && !FueraAcera() && !Chocado();
        
        return res;
    }

    //Mirar ifs
    @Override
    void Guardar() {
        Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
        casoAdecidir.setDataset(casosEntrenamiento);
        
        casoAdecidir.setValue(0, distanciaCocheDelante);
        casoAdecidir.setValue(1, distanciaCocheAtras);
        
        //if(fase <= 1){
            casoAdecidir.setValue(2, velocidad1);
            casoAdecidir.setValue(3, angulo1);
            casoAdecidir.setValue(4, tiempo1);
        //}
        
       // if(fase <= 2){
            casoAdecidir.setValue(5, velocidad2);
            casoAdecidir.setValue(6, angulo2);
            casoAdecidir.setValue(7, tiempo2);
        //}
        
        //if(fase <= 3){
            casoAdecidir.setValue(8, velocidad3);
            casoAdecidir.setValue(9, angulo3);
            casoAdecidir.setValue(10, tiempo3);
        //}
        
        //if(fase <= 4){
            casoAdecidir.setValue(11, velocidad4);
            casoAdecidir.setValue(12, angulo4);
            casoAdecidir.setValue(13, tiempo4);
        //}

        int res = EsExito() ? 1 : 0; //Solo comprueba funcion Exito;
        
        casoAdecidir.setValue(14, res);         
        casosEntrenamiento.add(casoAdecidir);
    }

    
    boolean encontradoExito;
    boolean encontradMalo;
    
    
    boolean IteracionRealizada() {
        boolean res;
        
        res = encontradoExito && encontradMalo;
        
        return res;
    }
    
    @Override
    void Entrenamiento() {
        
        for(int iter = 0; iter < iteraciones; iter++){
            
            System.out.println("SIGUIENTE ITERACION");
            
            PreparacionEscenario();
            
            //Arreglar Posicion Inicial
            PreparacionAgente();
            
            PreparacionDatos();
            
            Fase1();
            
            encontradoExito = false;
            encontradMalo = false;
        }
        
    }
    
    
    //float distancia;
    
    Vector3f posAgenteInicialFase1;
    Quaternion rotAgenteInicialFase1;
    
    void PreFase1(){
        fase = 1;
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase1);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
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
        
        posAgenteInicialFase1 = Datos.PosInicial();
        rotAgenteInicialFase1 = Datos.RotInicial();
        
        for(float distancia = -Datos.tamanoCoche/2; distancia < Datos.tamanoCoche/4 && !IteracionRealizada(); distancia += 0.25f){
            PreFase1();
            
            //System.out.pr
            
            Vector3f posFinal = Operaciones.SumarVectores(cocheDelante.getWorldTranslation(), new Vector3f(0,0,distancia));
            posFinal.x = Datos.offsetPocAgenteX;
            
            float distAPuntoFinal = agente.Spatial().getWorldTranslation().distance(posFinal);
            
            velocidad1 = distAPuntoFinal / 1;
            tiempo1 = 1;
            angulo1 = 0;
            
            
            System.out.println("Velocidad1 : " + velocidad1);
            System.out.println("Tiempo1 : " + tiempo1);
            
            agente.Velocidad(velocidad1 / tiempoEnManiobraEntrenando);
            agente.Rotacion(angulo1);
            agente.Tiempo(tiempoEnManiobraEntrenando);
            
            
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
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase2);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
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
        
        for(float velocidad = 1f ; velocidad < 2f && !IteracionRealizada(); velocidad += 0.2f){
            //for(float angulo = 20; angulo < 30 && !IteracionRealizada(); angulo += 1){
                PreFase2();
                
                velocidad2 = -velocidad;
                tiempo2 = 1;
                angulo2 = -30;
                
                agente.Velocidad(velocidad2 / tiempoEnManiobraEntrenando);
                agente.Rotacion(angulo2);
                agente.Tiempo(tiempoEnManiobraEntrenando);
                
                while(agente.Tiempo() > 0 && agente.Colision() == null && !FueraAcera()){
                    
                     //System.out.println(agente.Spatial().getWorldTranslation());
                    
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
                    Fase3(velocidad);
                }
            //} 
        }
    }
    
    Vector3f posAgenteInicialFase3;
    Quaternion rotAgenteInicialFase3;
    void PreFase3(){
        fase = 3;
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
        Main.SetPosicion(agente.Spatial(), posAgenteInicialFase3);
        
        while(Main.cambiospendientes){
            try {
                Thread.sleep(Datos.tiempoEsperaThread);
            }catch (InterruptedException ex) {
                Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("La hebra ce mamó");
            }
        }
        
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
    
    void Fase3(float vel){ //Se alinea con los otros coches
        
        posAgenteInicialFase3 = agente.Spatial().getWorldTranslation().clone();
        rotAgenteInicialFase3 = agente.Spatial().getWorldRotation().clone();
        
        //for(float velocidad = 1f ; velocidad < 2f && !IteracionRealizada(); velocidad += 0.2f){
            //for(float angulo = 5; angulo < 30 && !IteracionRealizada(); angulo += 1){
                //PreFase3();
                
                
                //velocidad3 = -velocidad;
                velocidad3 = -vel;
                
                tiempo3 = 1;
                angulo3 = 30;
                
                agente.Velocidad(velocidad3 / tiempoEnManiobraEntrenando);
                agente.Rotacion(angulo3);
                agente.Tiempo(tiempoEnManiobraEntrenando);
                
                while(agente.Tiempo() > 0 && agente.Colision() == null && !FueraAcera() && !Chocado() && !EsExito()){
                    try {
                        Thread.sleep(Datos.tiempoEsperaThread);
                    }catch (InterruptedException ex) {
                        Logger.getLogger(EntrenamientoMovimientosAparcado.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("La hebra ce mamó");
                    }
                }
                //System.out.println("FLASE");
                System.out.println("Colision: ");
                
                agente.Tiempo(0);
                
                if(EsExito() && !encontradoExito){
                    
                    System.out.println("TRUE");
                    
                    Guardar();
                    encontradoExito = true;
                    
                }else if(!EsExito() && !encontradMalo && encontradoExito){
                    System.out.println("TRUE");
                    
                    Guardar();
                    encontradMalo = true;
                }
           // } 
        //}
    }
    
    
    boolean Chocado(){
        
        boolean res = false;
        
        float distanciaAtras = agente.Spatial().getWorldTranslation().distance(cocheAtras.getWorldTranslation());
        float distanciaDelante = agente.Spatial().getWorldTranslation().distance(cocheDelante.getWorldTranslation());
        
        float distanciaDelanteAtras = cocheAtras.getWorldTranslation().distance(cocheDelante.getWorldTranslation());
        
        boolean enmedio = distanciaDelanteAtras > distanciaAtras && distanciaDelanteAtras > distanciaDelante;
        
        if(enmedio){
            res = distanciaAtras < Datos.tamanoCoche || distanciaDelante < Datos.tamanoCoche;
        }else{
            res = true;
        }
       
        System.out.println("En medio: " + enmedio);
        //System.out.println();
        
        
        return res;
    }
    
    boolean FueraAcera(){
        
        boolean res = false;
        
        //float distanciaAtras = agente.Spatial().getWorldTranslation().distance(cocheAtras.getWorldTranslation());
        //float distanciaDelante = agente.Spatial().getWorldTranslation().distance(cocheDelante.getWorldTranslation());
        
        res = agente.Spatial().getWorldTranslation().x < -1;
        
        return res;
    }
}
