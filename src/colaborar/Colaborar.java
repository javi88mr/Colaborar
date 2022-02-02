/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colaborar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

/**
 *
 * @author Javier
 */
public class Colaborar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Process nuevoProceso; //Definimos una variable de tipo Process
        String nombreFichero = args[1];
        File archivo = null;
        RandomAccessFile raf = null;
        int lineas = Integer.parseInt(args[0]);
        
        try {
            //Redirigimos salida estándar y de error a un fichero
            //Se crea un PrintStrem para asociar todas las salidas(sout)
            //al fichero que se crea con el PrintStrem. Importante, inicializar
            //el PrintStrem (PrinstStrem ps;)fuera del try y dentro del try 
            //ps = new PrintStrem...
            PrintStream ps = new PrintStream(
                new BufferedOutputStream(new FileOutputStream(new File("javalog.txt"), true)), true);
            System.setOut(ps);
            System.setErr(ps);            
        } catch (Exception e) {
            System.err.println("Error al redirigir las salidas");
            System.err.println(e.toString());
        }

        archivo = new File(nombreFichero);
        //Preparamos el acceso al fichero
        if (!archivo.exists()) {
            //Si no existe el fichero
            try {
                archivo.createNewFile(); //Lo creamos
                
                System.out.println("Creado el fichero.");
            } catch (Exception e) {
                System.err.println("Error al crear el fichero");
                System.err.println(e.toString());
            } finally {
                try { // Nos asegurarnos que se cierra el fichero.
                    if (null != raf) {
                        raf.close();
                    }
                } catch (Exception e2) {
                    System.err.println("Error al cerrar el fichero");
                    System.err.println(e2.toString());
                    System.exit(1); //Si hay error, finalizamos
                }
            }
        }
        //Creamos un grupo de procesos que accederán al mismo fichero
        try {
            for (int i = 0; i < 10; i++,lineas+=10) {
                
                nuevoProceso = Runtime.getRuntime().exec("java -jar "
                        + "Lenguaje.jar " + lineas + " " + args[1]);
                nuevoProceso.waitFor();
                
                //Creamos el nuevo proceso y le indicamos el número de orden y
                //el fichero que debe utilizar.
                System.out.println("Creado el proceso " + i);
                //Mostramos en consola que hemos creado otro proceso               
            }
        } catch (SecurityException ex) {
            System.err.println("Ha ocurrido un error de Seguridad."
                    + "No se ha podido crear el proceso por falta de permisos.");
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error, descripción: "
                    + ex.toString());
        }
    }
    
}
