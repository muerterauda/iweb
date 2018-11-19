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
import javax.inject.Inject;
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
    
    @Inject
    private ModuloCampaniaBean sessionBean;
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
        sessionBean.init();
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
        GregorianCalendar c1=new GregorianCalendar();
        GregorianCalendar c2=new GregorianCalendar();
        SimpleDateFormat s=new SimpleDateFormat("dd/MM/yyyy");
       
        XMLGregorianCalendar inicio=null;
        XMLGregorianCalendar fin=null;
         try{
            Date dateInicio= s.parse(fechaMedida);
            Date dateFin= s.parse(fechaMedida);
            DatatypeFactory factory=DatatypeFactory.newInstance();
            c1.setTime(dateInicio);
            c2.setTime(dateFin);
            inicio=factory.newXMLGregorianCalendar(c1);
            fin=factory.newXMLGregorianCalendar(c2);
            List<services.Campaña> listaC=buscarCampañaNombre(nombreCampaña,moduloNombre);
            if(listaC.isEmpty()){
                crearCampañaNombre(moduloNombre, nombreCampaña, inicio, fin);
            }else{
                services.Campaña camp=listaC.get(0);
                if(camp.getFechaInicio().compare(inicio)>0){
                    editarCampaña(camp.getId(), camp.getModulo().getId(), camp.getNombre(), inicio, camp.getFechaFin());
                }else if(camp.getFechaFin().compare(fin)<0){
                    editarCampaña(camp.getId(), camp.getModulo().getId(), camp.getNombre(), camp.getFechaInicio(),fin);
                }
            }
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

   

    private void editarCampaña(long id, long modulo, java.lang.String nombre, javax.xml.datatype.XMLGregorianCalendar fechaIni, javax.xml.datatype.XMLGregorianCalendar fechaFin) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.editarCampaña(id, modulo, nombre, fechaIni, fechaFin);
    }

    private java.util.List<services.Campaña> buscarCampañaNombre(java.lang.String nombre, java.lang.String nombreModulo) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañaNombre(nombre, nombreModulo);
    }

}
