/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import services.Campaña;
import services.Modulo;
import services.ServiciosIweb_Service;

/**
 *
 * @author GRJuanjo
 */
@Named(value = "testBean")
@SessionScoped
public class TestBean implements Serializable{

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8992/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;

    int cuentamodulo;
    int cuentacampaña;
    long idmodulo;
    long modulo;
    long idcampaña;
    long idmoduloparabuscarcampañas;
    String nombreModulo, nombremoduloparacrearcampaña;
    String nombre;
    double alfa, beta, gamma, kappa;
    String nombreCampaña;
    Date fechaIni, fechaFin, fechaQuery;

    public String getNombremoduloparacrearcampaña() {
        return nombremoduloparacrearcampaña;
    }

    public void setNombremoduloparacrearcampaña(String nombremoduloparacrearcampaña) {
        this.nombremoduloparacrearcampaña = nombremoduloparacrearcampaña;
    }

    
    public Date getFechaQuery() {
        return fechaQuery;
    }

    public void setFechaQuery(Date fechaQuery) {
        this.fechaQuery = fechaQuery;
    }
    List<Modulo> modulos;
    List<Campaña> campañaspormodulo;

    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
    }

    @PostConstruct
    public void doInit(){
        
    }
    
    public void doCrearModulo() {
        this.crearModulo(nombre, alfa, beta, gamma, kappa);
        this.setModulos();
    }

    public void doCrearCampaña() {
        try {
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(fechaIni);
            XMLGregorianCalendar calendarIni = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            
            GregorianCalendar c2 = new GregorianCalendar();
            c2.setTime(fechaFin);
            XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);

            this.crearCampaña(modulo, nombreCampaña, calendarIni, calendarFin);
            this.setCampañaspormodulo();
        } catch(Exception e){
            
        }
    }
    
    public void doEditarCampaña() {
        try {
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(fechaIni);
            XMLGregorianCalendar calendarIni = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            
            GregorianCalendar c2 = new GregorianCalendar();
            c2.setTime(fechaFin);
            XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);

            this.editarCampaña(idcampaña, modulo, nombreCampaña, calendarIni, calendarFin);
            this.setCampañaspormodulo();
        } catch(Exception e){
            
        }
    }

    public void doEditarModulo() {
        this.editarModulo(idmodulo, nombre, alfa, beta, gamma, kappa);
        this.setModulos();
    }

    public void doBuscarModulosNombre() {
        this.modulos = this.buscarModulosNombre(nombreModulo);
    }

    public void setCountModulo() {
        cuentamodulo = this.countModulo();
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public int getCuentaModulo() {
        return cuentamodulo;
    }

    public int getCuentaCamapaña() {
        return cuentacampaña;
    }

    public void setCuentaCampaña() {
        cuentacampaña = this.countCampaña();
    }

    public long getModulo() {
        return modulo;
    }

    public void setModulo(long modulo) {
        this.modulo = modulo;
    }

    public void setIdmodulo(long modulo) {
        this.idmodulo = modulo;
    }

    public long getIdmodulo() {
        return idmodulo;
    }

    public String getNombreCampaña() {
        return nombreCampaña;
    }

    public void setNombreCampaña(String nombreCampaña) {
        this.nombreCampaña = nombreCampaña;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void doBorrarModulo() {
        this.borrarModulo(idmodulo);
        this.setModulos();
    }

    public void setIdcampaña(long campaña) {
        this.idcampaña = campaña;
    }

    public long getIdcampaña() {
        return idcampaña;
    }

    public void doBorrarCampaña() {
        this.borrarCampaña(idcampaña);
        this.setCampañaspormodulo();
    }

    public void setModulos() {
        this.modulos = this.buscarModulos();
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public List<Campaña> getCampañaspormodulo() {
        return campañaspormodulo;
    }

    public void setCampañaspormodulo() {
        campañaspormodulo = this.buscarCampañasModulo(idmoduloparabuscarcampañas);
    }

    public long getIdmoduloparabuscarcampañas() {
        return idmoduloparabuscarcampañas;
    }

    public void setIdmoduloparabuscarcampañas(long id) {
        this.idmoduloparabuscarcampañas = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getKappa() {
        return kappa;
    }

    public void setKappa(double kappa) {
        this.kappa = kappa;
    }
    
    public void doCrearCampañaNombre(){
        try {
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(fechaQuery);
            XMLGregorianCalendar calendarIni = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            GregorianCalendar c2 = new GregorianCalendar();
            c2.setTime(fechaFin);
            XMLGregorianCalendar calendarFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            this.crearCampañaNombre(nombremoduloparacrearcampaña, nombreCampaña,calendarIni,calendarFin);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doBuscarPorModuloYFechaInicio() {
        try {
            GregorianCalendar c1 = new GregorianCalendar();
            c1.setTime(fechaIni);
            XMLGregorianCalendar calendarIni = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
            this.campañaspormodulo=this.buscarCampañasModuloFechaInicio(idmoduloparabuscarcampañas, calendarIni);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(TestBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void borrarCampaña(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.borrarCampaña(id);
    }

    private void borrarModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.borrarModulo(id);
    }

    private java.util.List<services.Campaña> buscarCampañasModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModulo(id);
    }

    private java.util.List<services.Campaña> buscarCampañasModuloFecha(long id, javax.xml.datatype.XMLGregorianCalendar fecha) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModuloFecha(id, fecha);
    }

    private java.util.List<services.Campaña> buscarCampañasModuloFechaInicio(long id, javax.xml.datatype.XMLGregorianCalendar fecha) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModuloFechaInicio(id, fecha);
    }

    private java.util.List<services.Modulo> buscarModulos() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarModulos();
    }

    private java.util.List<services.Modulo> buscarModulosNombre(java.lang.String nombre) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarModulosNombre(nombre);
    }

    private int countCampaña() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.countCampaña();
    }

    private int countModulo() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.countModulo();
    }

    private void crearCampaña(long modulo, java.lang.String nombre, javax.xml.datatype.XMLGregorianCalendar fechaIni, javax.xml.datatype.XMLGregorianCalendar fechaFin) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearCampaña(modulo, nombre, fechaIni, fechaFin);
    }

    private void crearCampañaNombre(java.lang.String nombreModulo, java.lang.String nombre, javax.xml.datatype.XMLGregorianCalendar fechaIni, javax.xml.datatype.XMLGregorianCalendar fechaFin) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearCampañaNombre(nombreModulo, nombre, fechaIni, fechaFin);
    }

    private void crearModulo(java.lang.String nombre, double alfa, double beta, double gamma, double kappa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.crearModulo(nombre, alfa, beta, gamma, kappa);
    }

    private void editarCampaña(long id, long modulo, java.lang.String nombre, javax.xml.datatype.XMLGregorianCalendar fechaIni, javax.xml.datatype.XMLGregorianCalendar fechaFin) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.editarCampaña(id, modulo, nombre, fechaIni, fechaFin);
    }

    private void editarModulo(long id, java.lang.String nombre, double alfa, double beta, double gamma, double kappa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.editarModulo(id, nombre, alfa, beta, gamma, kappa);
    }

    

}
