/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ficheros1;

/**
 *
 * @author juanl
 */
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Práctica Tema 2 Acceso a Datos
 * Author: Luis Robbe Toichoa Sautoho AKA: Doctor
 */
public class Lectura extends Component {
    /**
     * Antes de comenzar el ejercicio hemos importado en el pom.xml la librería de apache commons
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Creamos la variable título para almacenar el título del archivo txt.
         * Creamos la variable archivo para almacenar el contenido del txt
         * Preguntamos al usuario por el archivo a abrir en pantalla
         */
        String texto;
        String archivo = "";
        texto = JOptionPane.showInputDialog("¿Qué archivo quiere abrir?");
        /**
         * Creación del BufferedReader y del FileReader para leer el txt
         * Se va llenando de lineas el arvhivo vacio en cada iteración
         * Se rodean del try/catch
         */
        try (BufferedReader br = new BufferedReader(new FileReader(texto))){
            String linea;
            while ((linea = br.readLine()) != null){
                archivo += linea + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Impresión en pantalla del txt completo
         * Metemos el archivo de texto en un arreglo formando un arreglo de palabras
         */
        System.out.println(archivo);
        archivo = archivo.replaceAll("[\\.\\,\\(\\)]", "");
        String[] palabrasTexto = archivo.split(" ");
        /**
         * Creación del diccionario o hashmap
         * Se contabilizan las palabras mayores de 2 caracteres
         */
        HashMap<String, Integer> histograma = new HashMap<>();
        for (String p: palabrasTexto){
            if (histograma.containsKey(p) && (p.length() > 2)){
                histograma.put(p, histograma.get(p) + 1);
            }else if (p.length() > 2){
                histograma.put(p, 1);
            }
        }
        /**
         * Iteración del hashmap para que imprima los resultados por pantalla
         * Debajo, un try/catch rodeando la creción del CSV
         */
        for (Map.Entry<String, Integer> entry : histograma.entrySet()){
            System.out.printf("En el texto, '%s' aparece %d\n", entry.getKey(), entry.getValue());
        }
        try {
            crearCSV(histograma, texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Este método crea el CSV, definiendo la cabecera y las líneas y pasándolas a la tabla
     * @param map
     * @param texto
     * @throws IOException
     */
    public static void crearCSV(HashMap<String, Integer> map, String texto) throws IOException {
        String[] HEADERS = {"palabra", "numero"};
        FileWriter out = new FileWriter(texto + "_histograma.csv");
        try(CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))){
            map.forEach((palabra, numero) -> {
                /**
                 * La impresión de la cabecera se rodea de un try/catch
                 */
                try{
                    printer.printRecord(palabra, numero);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}