/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import services.ServiciosIweb_Service;

/**
 *
 * @author Sergi
 */
@Named(value = "inicioBean")
@RequestScoped
public class InicioBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;
    
    private Part archivo;
    private String error;
    private String mensaje;
    /**
     * Creates a new instance of PruebaBean
     */
    public InicioBean() {
    }

    @PostConstruct
    public void init(){
        
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }
    
    public String entrar(){
        return "modulos";
    }
    
    public void procesar(){
        leerArchivo(archivo);
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
        if(buscarModulosNombre(nombre).isEmpty()){
            crearModulo(nombre, Double.parseDouble(alfa), Double.parseDouble(beta), Double.parseDouble(gamma), Double.parseDouble(kappa));
            mensaje="Archivo importado correctamente";
            error=null;
        }else{
            error="Modulo ya existente";
            mensaje=null;
        }
       }
        
    private void crearModulo(java.lang.String nombre, double alfa, double beta, double gamma, double kappa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearModulo(nombre, alfa, beta, gamma, kappa);
    }

    private java.util.List<services.Modulo> buscarModulosNombre(java.lang.String nombre) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarModulosNombre(nombre);
    }

    private void leerArchivo(Part p) {
        InputStream inputStream=null;
        InputStream inputStreamC=null;
         try {
            inputStream=p.getInputStream();
            inputStreamC=p.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(InicioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner sc = new Scanner(inputStream);
        boolean e=false;
        if(sc.hasNext()){
            String empezar=sc.next();
             if(empezar.startsWith("I-")){
                leerModulo(inputStreamC);
             }else if(empezar.startsWith("Módulo:")){
                leerMedida(inputStreamC);
            }else{
                 e=true;
            }
            }else{
                e=true;
            }
        if(e)
            error="Formato del archivo no sportado";
        
    }

    private void leerMedida(InputStream inputStream) {
        List<String> lista=new LinkedList<>();
        int i=0;
        try{
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()&&i<3) {
            String linea = sc.nextLine();
            linea = linea.replaceAll("[(\t)]+", " ");
            linea = linea.replaceAll("[(' ')]+", " ");
            linea = linea.trim();
            lista.add(linea);
            i++;
        }
        sc.close();
        }catch(Exception e){
            System.out.println("Error en la apertura del fichero");
        }
        String moduloNombre=lista.get(0).split(": ")[1];
        String nombreCampaña=lista.get(1).split(": ")[1];
        String fechaMedida=lista.get(2).split(": ")[1];
        String[] fecha=fechaMedida.split("/");
        String fechaInicio="01-"+fecha[1]+"-"+fecha[2];
        String dia="";
        switch(fecha[1]){
            case "01":case "03":case "05": case "07":case "08":case "10":case "12":
                        dia="31";
                        break;
            case "04":case "06":case "09":case "11":
                        dia="30";
                        break;
            case "02":
                        dia="28";
                        break;
                       
        }
        String fechaFin=dia+"-"+fecha[1]+"-"+fecha[2];
        GregorianCalendar c1=new GregorianCalendar();
        GregorianCalendar c2=new GregorianCalendar();
        SimpleDateFormat s=new SimpleDateFormat("dd-MM-yyyy");
        try{
              Date dateInicio= s.parse(fechaInicio);
              Date dateFin= s.parse(fechaFin);
              DatatypeFactory factory=DatatypeFactory.newInstance();
              c1.setTime(dateInicio);
              c2.setTime(dateFin);
              XMLGregorianCalendar inicio=factory.newXMLGregorianCalendar(c1);
              XMLGregorianCalendar fin=factory.newXMLGregorianCalendar(c2);
              crearCampañaNombre(moduloNombre, nombreCampaña, inicio, fin);
              mensaje="Archivo importado correctamente";
              error=null;
        }catch(Exception e){
            error="Error en la importacion del fichero";
            mensaje=null;
        }
    }

    private void crearCampañaNombre(java.lang.String nombreModulo, java.lang.String nombre, javax.xml.datatype.XMLGregorianCalendar fechaIni, javax.xml.datatype.XMLGregorianCalendar fechaFin) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearCampañaNombre(nombreModulo, nombre, fechaIni, fechaFin);
    }

}
