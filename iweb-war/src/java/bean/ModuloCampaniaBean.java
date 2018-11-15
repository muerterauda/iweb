/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.xml.ws.WebServiceRef;
import service.Campaña;
import service.Modulo;
import service.ServiciosIweb_Service;

/**
 *
 * @author Sergi
 */
@Named(value = "moduloCampaniaBean")
@SessionScoped
public class ModuloCampaniaBean implements java.io.Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;

    private List<Modulo> modulos;
    private List<Campaña> campaniasModulo;
    private Modulo modulo;

    /**
     * Creates a new instance of ModuloCampaniaBean
     */
    public ModuloCampaniaBean() {
    }
    
    
    @PostConstruct
    public void init() {
        modulos = getModulosService();
        campaniasModulo = new ArrayList<>();
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

    public List<Campaña> getCampaniasModulo() {
        return campaniasModulo;
    }

    public void setCampaniasModulo(List<Campaña> campaniasModulo) {
        this.campaniasModulo = campaniasModulo;
    }

    public String editarModulo(long id) {
        return "editarModulo";
    }

    public void computeCampanias(long id) {
        List<Campaña> aux = buscarCampañasModulo(id);
        aux.forEach((x) -> {
            if (!campaniasModulo.contains(x)) {
                campaniasModulo.add(x);
            }else{
                campaniasModulo.remove(x);
            }
        });
    }

    /* -------------------------  Servicios ---------------------------------------------------------------------------------*/
    private java.util.List<service.Modulo> getModulosService() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service.ServiciosIweb port = service.getServiciosIwebPort();
        return port.getModulos();
    }

    private java.util.List<service.Campaña> buscarCampañasModulo(long id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service.ServiciosIweb port = service.getServiciosIwebPort();
        return port.buscarCampañasModulo(id);
    }

}