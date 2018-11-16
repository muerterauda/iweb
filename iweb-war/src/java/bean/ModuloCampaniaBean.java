/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.xml.ws.WebServiceRef;
import services.Campaña;
import services.Modulo;
import services.ServiciosIweb_Service;

/**
 *
 * @author Sergi
 */
@Named(value = "moduloCampaniaBean")
@SessionScoped
public class ModuloCampaniaBean implements java.io.Serializable {

    @Inject
    private EditarModuloBean sessionBean;
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;

    private List<Modulo> modulos;
    private List<Campaña> campaniasModulo;
    private List<Modulo> modulosSeleccionados;
    private Modulo modulo;

    /**
     * Creates a new instance of ModuloCampaniaBean
     */
    public ModuloCampaniaBean() {
    }

    @PostConstruct
    public void init() {
        modulo = new Modulo();
        modulos = getModulosService();
        campaniasModulo = new ArrayList<>();
        modulosSeleccionados = new ArrayList<>();
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
        for (Modulo m : modulos) {
            if (m.getId().equals(id)) {
                modulo = m;
                break;
            }
        }
        sessionBean.init();
        return "editarModulo";
    }

    public void computeCampanias(long id) {
        for (Modulo m : modulos) {
            if (m.getId().equals(id)) {
                if (modulosSeleccionados.contains(m)) {
                    modulosSeleccionados.remove(m);
                } else {
                    modulosSeleccionados.add(m);
                }
            }
        }
        campaniasModulo.clear();
        for (Modulo m : modulosSeleccionados) {
            for (Campaña c : buscarCampañasModulo(m.getId())) {
                campaniasModulo.add(c);
            }
        }
    }
    
    public String volver(){
        init();
        return "index";
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
