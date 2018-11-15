/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;
import services.Campaña;
import services.Modulo;
import services.ServiciosIweb_Service;


/**
 *
 * @author GRJuanjo
 */
@Named(value = "testBean")
@RequestScoped
public class TestBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_37789/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;
    
    int cuentamodulo;
    int cuentacampaña;
    long idmodulo;
    long idcampaña;
    long idmoduloparabuscarcampañas;
    List<Modulo> modulos;
    List<Campaña> campañaspormodulo;
    /**
     * Creates a new instance of TestBean
     */
    public TestBean() {
    }
    
    public void setCountModulo(){
        cuentamodulo= this.countModulo();
    }
    
    
    public int getCuentaModulo(){
        return cuentamodulo;
    }

    private int countModulo() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.countModulo();
    }

    public int getCuentaCamapaña(){
        return cuentacampaña;
    }
    
    public void setCuentaCampaña(){
        cuentacampaña=this.countCampaña();
    }

    private int countCampaña() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.countCampaña();
    }
    
    public void setIdmodulo(long modulo){
        this.idmodulo=modulo;
    }
    
    public long getIdmodulo(){
        return idmodulo;
    }
    
    public void doBorrarModulo(){
        this.borrarModulo(idmodulo);
    }

    private void borrarModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.borrarModulo(id);
    }
    
    public void setIdcampaña(long campaña){
        this.idcampaña=campaña;
    }
    
    public long getIdcampaña(){
        return idcampaña;
    }
    
    public void doBorrarCampaña(){
        this.borrarCampaña(idcampaña);
    }

    private void borrarCampaña(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.borrarCampaña(id);
    }
        public void setModulos(){
            this.modulos=this.buscarModulos();
        }
        
        public List<Modulo> getModulos(){
            return modulos;
        }
    private java.util.List<services.Modulo> buscarModulos() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarModulos();
    }
    public List<Campaña> getCampañaspormodulo(){
        return campañaspormodulo;
    }
    
    public void setCampañaspormodulo(){
        campañaspormodulo=this.buscarCampañasModulo(idmoduloparabuscarcampañas);
    }
    private java.util.List<services.Campaña> buscarCampañasModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModulo(id);
    }
    
    public long getIdmoduloparabuscarcampañas(){
        return idmoduloparabuscarcampañas;
    }
    
    public void setIdmoduloparabuscarcampañas(long id){
        this.idmoduloparabuscarcampañas=id;
    }
}
