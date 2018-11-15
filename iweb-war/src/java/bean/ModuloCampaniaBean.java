/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;
import services.Campaña;
import services.Modulo;
import services.ServiciosIweb_Service;

/**
 *
 * @author Sergi
 */
@Named(value = "moduloCampaniaBean")
@RequestScoped 
public class ModuloCampaniaBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;
    
    private List<Modulo> modulos;
    private List<Campaña> campaniasModulo;
    private Modulo modulo;
    
    private boolean campVisible;
    private boolean selected;

    /**
     * Creates a new instance of ModuloCampaniaBean
     */
    public ModuloCampaniaBean() {
    }
    
    
    @PostConstruct
    public void init (){
        modulos = getModulosService();
        campVisible = false;
        selected = false;
    }
    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<Campaña> getCampaniasModulo() {
        return campaniasModulo;
    }

    public void setCampaniasModulo(List<Campaña> campaniasModulo) {
        this.campaniasModulo = campaniasModulo;
    }

    public boolean isCampVisible() {
        return campVisible;
    }

    public void setCampVisible(boolean campVisible) {
        this.campVisible = campVisible;
    }

    public String editarModulo(long id){
        return "editarModulo";
    }
    
    public void computeCampanias(long id){
        campaniasModulo = buscarCampañasModulo(id);
        if(campaniasModulo!=null){
            campVisible = true;
        }
    }
    
    /* -------------------------  Servicios ---------------------------------------------------------------------------------*/

    private java.util.List<services.Modulo> getModulosService() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.getModulos();
    }

    private java.util.List<services.Campaña> buscarCampañasModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModulo(id);
    }
    
    
    
}
