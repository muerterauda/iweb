/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.CampañaFacade;
import ejb.ModuloFacade;
import entity.Campaña;
import entity.Modulo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author PC
 */
@WebService(serviceName = "ServiciosIweb")
@Stateless()
public class ServiciosIweb {

    @EJB
    private ModuloFacade moduloFacade;
    @EJB
    private CampañaFacade campañaFacade;
    
    @WebMethod(operationName = "countModulo")
    public int countModulo() {
        return moduloFacade.count();
    }
    
    @WebMethod(operationName = "countCampaña")
    public int countCampaña() {
        return campañaFacade.count();
    }

    @WebMethod(operationName = "crearModulo")
    @Oneway
    public void crearModulo(@WebParam(name = "nombre") String nombre, @WebParam(name = "alfa") double alfa, @WebParam(name = "beta") double beta, @WebParam(name = "gamma") double gamma, @WebParam(name = "kappa") double kappa) {
        Modulo m = new Modulo();
        m.setNombre(nombre);
        m.setAlfa(alfa);
        m.setBeta(beta);
        m.setGamma(gamma);
        m.setKappa(kappa);
        
        moduloFacade.create(m);
    }
    
    @WebMethod(operationName="crearCampaña")
    @Oneway
    public void crearCampaña(@WebParam(name = "modulo") long modulo, @WebParam(name = "nombre") String nombre, @WebParam(name = "fechaIni") Date fechaIni, @WebParam(name = "fechaFin") Date fechaFin){
        Campaña c = new Campaña();
        c.setModulo(moduloFacade.find(modulo));
        c.setNombre(nombre);
        c.setFechaInicio(fechaIni);
        c.setFechaFin(fechaFin);
        
        campañaFacade.create(c);
    }
    
    @WebMethod(operationName = "editarModulo")
    @Oneway
    public void editarModulo(@WebParam(name = "id") long id, @WebParam(name = "nombre") String nombre, @WebParam(name = "alfa") double alfa, @WebParam(name = "beta") double beta, @WebParam(name = "gamma") double gamma, @WebParam(name = "kappa") double kappa) {
        Modulo m = moduloFacade.find(id);
        m.setNombre(nombre);
        m.setAlfa(alfa);
        m.setBeta(beta);
        m.setGamma(gamma);
        m.setKappa(kappa);
        
        moduloFacade.edit(m);
    }
    
    @WebMethod(operationName ="editarCampaña")
    @Oneway
    public void editarCampaña(@WebParam(name = "id") long id, @WebParam(name = "modulo") long modulo, @WebParam(name = "nombre") String nombre, @WebParam(name = "fechaIni") Date fechaIni, @WebParam(name = "fechaFin") Date fechaFin){
        Campaña c = campañaFacade.find(id);
        c.setModulo(moduloFacade.find(modulo));
        c.setNombre(nombre);
        c.setFechaInicio(fechaIni);
        c.setFechaFin(fechaFin);
        
        campañaFacade.edit(c);
        
    }
    
    @WebMethod(operationName = "borrarModulo")
    @Oneway
    public void borrarModulo(@WebParam(name = "id") long id) {
        Modulo m = moduloFacade.find(id);
        moduloFacade.remove(m);
    }
    
    @WebMethod(operationName = "borrarCampaña")
    @Oneway
    public void borrarCampaña(@WebParam(name = "id") long id) {
        Campaña m = campañaFacade.find(id);
        campañaFacade.remove(m);
    }
    
    //Búsqueda Módulos
    @WebMethod(operationName ="buscarModulos")
    public List<Modulo> buscarModulos(){        
        return moduloFacade.findAll();
    }
    
    //Campañas de módulos
    @WebMethod(operationName = "buscarCampañasModulo")
    public List<Campaña> buscarCampañasModulo(@WebParam(name = "id") long id){
        Modulo m = moduloFacade.find(id);
        
        return m.getCampañaList();
    }
    
    //Modulo por nombre
    @WebMethod(operationName = "buscarModulosNombre")
    public List<Modulo> buscarModuloNombre(@WebParam(name = "nombre") String nombre){
        List<Modulo> lista = new ArrayList<>();
        
        moduloFacade.findAll().stream().filter((m) -> (m.getNombre().equals(nombre))).forEachOrdered((m) -> {
            lista.add(m);
        });
        
        
        return lista;
    }
    
    //Campañas de módulo por fecha
    //Campañas de módulos
    @WebMethod(operationName = "buscarCampañasModuloFecha")
    public List<Campaña> buscarCampañasModuloFecha(@WebParam(name = "id") long id, @WebParam(name = "fecha") Date fecha){
        Modulo m = moduloFacade.find(id);
        List<Campaña> lista = new ArrayList<>();
        
        for(Campaña c : m.getCampañaList()){
            if(c.getFechaInicio().before(fecha) && c.getFechaFin().after(fecha))
                lista.add(c);
        }
        
        return lista;
    }
}
