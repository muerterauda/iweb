/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.ModuloFacade;
import entity.Modulo;
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

    @WebMethod(operationName = "create")
    @Oneway
    private void create(@WebParam(name = "entity") Modulo entity) {
        moduloFacade.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    private void edit(@WebParam(name = "entity") Modulo entity) {
        moduloFacade.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    private void remove(@WebParam(name = "entity") Modulo entity) {
        moduloFacade.remove(entity);
    }

    @WebMethod(operationName = "find")
    private Modulo find(@WebParam(name = "id") Object id) {
        return moduloFacade.find(id);
    }

    @WebMethod(operationName = "findAll")
    private List<Modulo> findAll() {
        return moduloFacade.findAll();
    }

    @WebMethod(operationName = "findRange")
    private List<Modulo> findRange(@WebParam(name = "range") int[] range) {
        return moduloFacade.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return moduloFacade.count();
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
    
    @WebMethod(operationName = "editarModulo")
    @Oneway
    public void editarModulo(@WebParam(name = "id") Long id, @WebParam(name = "nombre") String nombre, @WebParam(name = "alfa") double alfa, @WebParam(name = "beta") double beta, @WebParam(name = "gamma") double gamma, @WebParam(name = "kappa") double kappa) {
        Modulo m = moduloFacade.find(id);
        m.setNombre(nombre);
        m.setAlfa(alfa);
        m.setBeta(beta);
        m.setGamma(gamma);
        m.setKappa(kappa);
        
        moduloFacade.edit(m);
    }

    @WebMethod(operationName = "borrarModulo")
    @Oneway
    public void borrarModulo(@WebParam(name = "id") long id) {
        Modulo m = moduloFacade.find(id);
        moduloFacade.remove(m);
    }
}
