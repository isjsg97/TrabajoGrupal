/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.core.Instance;

/**
 *
 * @author josag
 */
public class FaseEjecucionMovimientosAparcado extends FaseEjecucion{
    
    Vector3f cocheDelante;
    Vector3f cocheAtras;
    
    
    //Cosas para situarse a la mitad de los coches (S epuede convertir en otra fase de aprendizaje en un futuro)
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
    
    float velocidad;
    float angulo;
    float tiempo;
    
    
    @Override
    public void PrepararEjecucion(Object... params){
        super.PrepararEjecucion(params[0], params[1], params[2]);
        
        System.out.println(params.length);
        
        cocheDelante = (Vector3f)params[3];
        cocheAtras = (Vector3f)params[4];
    }

    @Override
    public boolean FaseCompletada() {
        return completado;
    }

    @Override
    public void Ejecucion() {
        
        System.out.println("ENTRO");
        
        ConseguirDatos();
        
        for(int i = 0; i < 4; i++){
            
            ObtenerDatosFase(i);
            
            PonerDatosCoche();
            
            while(agente.Tiempo() > 0){
                //System.out.println("No muero :D");
                try {
                    Thread.sleep(Datos.tiempoEsperaThread);
                }catch (InterruptedException ex) {
                    Logger.getLogger(FaseEjecucionComprobacionTamano.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("La hebra ce mamó");
                }
            }
            
            //System.out.println("No muero :)");
        }
        
        //System.out.println("No muero ^_^");
        
        completado = true;
    }
    
    
    void ConseguirDatos(){
        int res = 0;
        
        distanciaCocheDelante = agente.Spatial().getWorldTranslation().distance(cocheDelante);
        distanciaCocheAtras = agente.Spatial().getWorldTranslation().distance(cocheAtras);
        
        //System.out.println("PoscionDelante: " + distanciaCocheDelante + ", PosicionAtras: " + distanciaCocheAtras);
        
        //System.out.println("DistanciaDelante: " + distanciaCocheDelante + ", DistanciaAtras: " + distanciaCocheAtras);
 
        boolean encontrado = false;
        
        
        /*try {
            Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
            casoAdecidir.setDataset(casosEntrenamiento);   
            casoAdecidir.setValue(0,  distdelante);
            casoAdecidir.setValue(1,  distatras);  //alcanzara disntancia de 2.5 deberia dar v=5  
            casoAdecidir.setValue(,  distatras);
            casoAdecidir.setValue(1,  distatras);
            casoAdecidir.setValue(1,  distatras);
            casoAdecidir.setValue(1,  distatras);
            casoAdecidir.setValue(1,  distatras);
            casoAdecidir.setValue(1,  distatras);
            res = (int) conocimiento.classifyInstance(casoAdecidir);
        } catch (Exception ex) {
            Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        for(float precision = 0.00015f; !encontrado; precision += 0.00015f){
        for(float distancia = -Datos.tamanoCoche/2; distancia < Datos.tamanoCoche/4 && !encontrado; distancia += 0.25f){
            
            for(float vel2 = 1.5f ; vel2 < 2.5f && !encontrado; vel2 += 0.2f){
                for(float ang2 = 25; ang2 < 35 && !encontrado; ang2 += 1){
                    for(float vel3 = 1.5f ; vel3 < 2.5f && !encontrado; vel3 += 0.2f){
                        for(float ang3 = 25; ang3 < 35 && !encontrado; ang3 += 1){
                        
                            
            
                                Vector3f posFinal = Operaciones.SumarVectores(cocheDelante, new Vector3f(0, 0, distancia));
                                posFinal.x = Datos.offsetPocAgenteX;

                                float distAPuntoFinal = agente.Spatial().getWorldTranslation().distance(posFinal);

                                float velocidad1aux = distAPuntoFinal / 1;
                                float angulo1aux = 0;
                                float tiempo1uax = 1;

                                float velocidad2aux = -vel2;
                                //float velocidad2aux = -2f;
                                float angulo2aux = -ang2;
                                //float angulo2aux = -25;
                                float tiempo2aux = 1;

                                float velocidad3aux = -vel3;
                                //float velocidad3aux = -2f;
                                float angulo3aux = ang3;
                                //float angulo3aux = 25;
                                float tiempo3aux = 1;

                                float velocidad4aux = 0;
                                float angulo4aux = 0;
                                float tiempo4aux = 0;

                                try {
                                    Instance casoAdecidir = new Instance(casosEntrenamiento.numAttributes());
                                    casoAdecidir.setDataset(casosEntrenamiento);
                                    casoAdecidir.setValue(0, distanciaCocheDelante);
                                    casoAdecidir.setValue(1, distanciaCocheAtras);  //alcanzara disntancia de 2.5 deberia dar v=5  

                                    casoAdecidir.setValue(2, velocidad1aux);
                                    casoAdecidir.setValue(3, angulo1aux);
                                    casoAdecidir.setValue(4, tiempo1uax);

                                    casoAdecidir.setValue(5, velocidad2aux);
                                    casoAdecidir.setValue(6, angulo2aux);
                                    casoAdecidir.setValue(7, tiempo2aux);

                                    casoAdecidir.setValue(8, velocidad3aux);
                                    casoAdecidir.setValue(9, angulo3aux);
                                    casoAdecidir.setValue(10, tiempo3aux);

                                    casoAdecidir.setValue(11, velocidad4aux);
                                    casoAdecidir.setValue(12, angulo4aux);
                                    casoAdecidir.setValue(13, tiempo4aux);

                                    float resaux = (float) conocimiento.classifyInstance(casoAdecidir);
                                    res = (int) resaux;

                                    encontrado = Math.abs(1 - resaux) < precision;

                                    System.out.println("Res = " + resaux);
                                    System.out.println("Encontrado: " + encontrado);

                                    if (encontrado) {

                                        velocidad1 = velocidad1aux;// / Datos.tiempoEnManiobraEjecucion;
                                        angulo1 = angulo1aux;
                                        tiempo1 = tiempo1uax; //* Datos.tiempoEnManiobraEjecucion;

                                        velocidad2 = velocidad2aux;// / Datos.tiempoEnManiobraEjecucion;
                                        angulo2 = angulo2aux;
                                        tiempo2 = tiempo2aux;// * Datos.tiempoEnManiobraEjecucion;

                                        velocidad3 = velocidad3aux;// / Datos.tiempoEnManiobraEjecucion;
                                        angulo3 = angulo3aux;
                                        tiempo3 = tiempo3aux;// * Datos.tiempoEnManiobraEjecucion;

                                        velocidad4 = velocidad4aux;// / Datos.tiempoEnManiobraEjecucion;
                                        angulo4 = angulo4aux;
                                        tiempo4 = velocidad4aux;// * Datos.tiempoEnManiobraEjecucion; 

                                        System.out.println("DistanciaDelante = " + distanciaCocheDelante);
                                        System.out.println("DistanciaDetras = " + distanciaCocheAtras);

                                        System.out.println("Velocidad1 = " + velocidad1);
                                        System.out.println("Angulo1 = " + angulo1);
                                        System.out.println("Tiempo1 = " + tiempo1);

                                        System.out.println("Velocidad2 = " + velocidad2);
                                        System.out.println("Angulo2 = " + angulo2);
                                        System.out.println("Tiempo2 = " + tiempo2);

                                        System.out.println("Velocidad3 = " + velocidad3);
                                        System.out.println("Angulo3 = " + angulo3);
                                        System.out.println("Tiempo3 = " + tiempo3);

                                        System.out.println("Velocidad4 = " + velocidad4);
                                        System.out.println("Angulo4 = " + angulo4);
                                        System.out.println("Tiempo4 = " + tiempo4);
                                    }

                                } catch (Exception ex) {
                                    Logger.getLogger(Ejecucion.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        
                        }
                    }
                }
            }   
        }
    }

    
    void ObtenerDatosFase(int num){
        if(num == 0){
            
            velocidad = velocidad1;
            angulo = angulo1;
            tiempo = tiempo1;
            
        }else if(num == 1){
            
            velocidad = velocidad2;
            angulo = angulo2;
            tiempo = tiempo2;
            
        }else if(num == 2){
            
            velocidad = velocidad3;
            angulo = angulo3;
            tiempo = tiempo3;
            
        }else if(num == 3){
            
            velocidad = velocidad4;
            angulo = angulo4;
            tiempo = tiempo4;
            
        }
    }

    
    void PonerDatosCoche(){
        agente.Velocidad(velocidad);
        agente.Rotacion(angulo);
        agente.Tiempo(tiempo);
    }

    @Override
    public Object[] Datos() {
        return null;
    }
    
}
