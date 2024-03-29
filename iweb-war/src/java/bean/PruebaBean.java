/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;
import service.ServiciosIweb_Service;

/**
 *
 * @author Sergi
 */
@Named(value = "pruebaBean")
@RequestScoped
public class PruebaBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;
    
    private Part archivo;
    /**
     * Creates a new instance of PruebaBean
     */
    public PruebaBean() {
    }

    @PostConstruct
    public void init(){
        
    }
    
    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }
    
   
    
    public int countModulo() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service.ServiciosIweb port = service.getServiciosIwebPort();
        return port.countModulo();
    }
    
    public void procesar(){
        try {
            leerModulo(archivo.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(PruebaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void leerModulo(InputStream f) {
        List<String> lista=new LinkedList<>();
        try{
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            lista.add(linea);
        }
        sc.close();
        }catch(Exception e){
            System.out.println("Error en la apertura del fichero");
        }
        String nombre = lista.get(0);
        String alfa = lista.get(14);
        String beta = lista.get(16);
        String gamma = lista.get(18);
        String kappa = lista.get(20);
        crearModulo(nombre, Double.parseDouble(alfa), Double.parseDouble(beta), Double.parseDouble(gamma), Double.parseDouble(kappa));
    }

    private void crearModulo(java.lang.String nombre, double alfa, double beta, double gamma, double kappa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearModulo(nombre, alfa, beta, gamma, kappa);
    }

}
