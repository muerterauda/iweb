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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
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
        if (comprobarExistenciaModulo(this.buscarModulos(), nombre)) {
            Modulo m = new Modulo();
            m.setNombre(nombre);
            m.setAlfa(alfa);
            m.setBeta(beta);
            m.setGamma(gamma);
            m.setKappa(kappa);

            moduloFacade.create(m);
        }
    }

    private boolean comprobarExistenciaModulo(List<Modulo> lista, String nombre) {
        boolean res = true;

        for (Modulo m : lista) {
            if (m.getNombre().equals(nombre)) {
                res = false;
                break;
            }
        }

        return res;
    }

    private boolean comprobarExistenciaCampaña(List<Campaña> lista, String nombre) {
        boolean res = true;

        for (Campaña c : lista) {
            System.out.print(c.getNombre() + " " + nombre);
            if (c.getNombre().equals(nombre)) {
                System.out.print("aiu");
                res = false;
                break;
            }
        }

        return res;
    }

    @WebMethod(operationName = "crearCampaña")
    @Oneway
    public void crearCampaña(@WebParam(name = "modulo") long modulo, @WebParam(name = "nombre") String nombre, @WebParam(name = "fechaIni") Date fechaIni, @WebParam(name = "fechaFin") Date fechaFin) {
        Campaña c = new Campaña();
        Modulo m = moduloFacade.find(modulo);

        List<Campaña> aux = m.getCampañaList();
        if (comprobarExistenciaCampaña(aux, nombre)) {
            c.setModulo(m);
            c.setNombre(nombre);
            c.setFechaInicio(fechaIni);
            c.setFechaFin(fechaFin);

            aux.add(c);
            m.setCampañaList(aux);

            campañaFacade.create(c);
            moduloFacade.edit(m);
        }
    }

    @WebMethod(operationName = "editarModulo")
    @Oneway
    public void editarModulo(@WebParam(name = "id") long id, @WebParam(name = "nombre") String nombre, @WebParam(name = "alfa") double alfa, @WebParam(name = "beta") double beta, @WebParam(name = "gamma") double gamma, @WebParam(name = "kappa") double kappa) {
        if (comprobarExistenciaModulo(this.buscarModulos(), nombre)) {
            Modulo m = moduloFacade.find(id);
            m.setNombre(nombre);
            m.setAlfa(alfa);
            m.setBeta(beta);
            m.setGamma(gamma);
            m.setKappa(kappa);

            moduloFacade.edit(m);
        }
    }

    @WebMethod(operationName = "editarCampaña")
    @Oneway
    public void editarCampaña(@WebParam(name = "id") long id, @WebParam(name = "modulo") long modulo, @WebParam(name = "nombre") String nombre, @WebParam(name = "fechaIni") Date fechaIni, @WebParam(name = "fechaFin") Date fechaFin) {
        Campaña c = campañaFacade.find(id);
        Modulo m = moduloFacade.find(modulo);
        Modulo n = c.getModulo();

        if (comprobarExistenciaCampaña(m.getCampañaList(), nombre)) {

            if (!Objects.equals(n.getId(), m.getId())) {
                List<Campaña> aux = n.getCampañaList();
                aux.remove(c);
                n.setCampañaList(aux);

                List<Campaña> aux1 = m.getCampañaList();
                aux1.add(c);
                m.setCampañaList(aux1);

                c.setModulo(m);
                moduloFacade.edit(n);
                moduloFacade.edit(m);
            }

            c.setNombre(nombre);
            c.setFechaInicio(fechaIni);
            c.setFechaFin(fechaFin);

            campañaFacade.edit(c);
        }
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
        Campaña c = campañaFacade.find(id);
        Modulo m = moduloFacade.find(c.getModulo().getId());
        
        List<Campaña> aux = m.getCampañaList();
        aux.remove(c);
        m.setCampañaList(aux);
            
        campañaFacade.remove(c);
    }

    //Búsqueda Módulos
    @WebMethod(operationName = "buscarModulos")
    public List<Modulo> buscarModulos() {
        return moduloFacade.findAll();
    }

    //Campañas de módulos
    @WebMethod(operationName = "buscarCampañasModulo")
    public List<Campaña> buscarCampañasModulo(@WebParam(name = "id") long id) {
        Modulo m = moduloFacade.find(id);

        return m.getCampañaList();
    }

    //Modulo por nombre
    @WebMethod(operationName = "buscarModulosNombre")
    public List<Modulo> buscarModuloNombre(@WebParam(name = "nombre") String nombre) {
        List<Modulo> lista = new ArrayList<>();

        moduloFacade.findAll().stream().filter((m) -> (m.getNombre().equals(nombre))).forEachOrdered((m) -> {
            lista.add(m);
        });

        return lista;
    }

    //Campañas de módulo por fecha
    //Campañas de módulos
    @WebMethod(operationName = "buscarCampañasModuloFechaInicio")
    public List<Campaña> buscarCampañasModuloFechaInicio(@WebParam(name = "id") long id, @WebParam(name = "fecha") Date fecha) {
        Modulo m = moduloFacade.find(id);
        List<Campaña> lista = new ArrayList<>();
        Calendar ca = Calendar.getInstance();
        Calendar ca2 = Calendar.getInstance();
        ca.setTime(fecha);
        int day = ca.get(Calendar.DAY_OF_MONTH);
        int month = ca.get(Calendar.MONTH);
        int year = ca.get(Calendar.YEAR);
        int day2 = 0;
        int month2 = 0;
        int year2 = 0;
        for (Campaña c : m.getCampañaList()) {
            ca2.setTime(c.getFechaInicio());
            day2 = ca2.get(Calendar.DAY_OF_MONTH);
            month2 = ca2.get(Calendar.MONTH);
            year2 = ca2.get(Calendar.YEAR);
            if (day2 == day && month2 == month && year2 == year) {
                lista.add(c);
            }
        }

        return lista;
    }

    @WebMethod(operationName = "crearCampañaNombre")
    @Oneway
    public void crearCampañaNombre(@WebParam(name = "nombreModulo") String nombreModulo, @WebParam(name = "nombre") String nombre, @WebParam(name = "fechaIni") Date fechaIni, @WebParam(name = "fechaFin") Date fechaFin) {
        List<Modulo> lista = buscarModuloNombre(nombreModulo);
        Modulo m;

        if (lista.isEmpty()) {
            crearModulo(nombreModulo, 0, 0, 0, 0);
        }
        this.crearCampaña(buscarModuloNombre(nombreModulo).get(0).getId(), nombre, fechaIni, fechaFin);
    }
}
