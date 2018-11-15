package bean;

import services.Modulo;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import services.ServiciosIweb_Service;

@Named(value = "editar")
@RequestScoped
public class EditarModuloBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ServiciosIweb/ServiciosIweb.wsdl")
    private ServiciosIweb_Service service;

    @Inject
    private ModuloCampaniaBean sessionBean;

    private Modulo current_modulo;

    private String nombre_modulo;
    private String alpha;
    private String beta;
    private String gamma;
    private String kappa;

    //Errores
    private String nombre_modulo_vacio;
    private String alpha_error;
    private String beta_error;
    private String gamma_error;
    private String kappa_error;

    public EditarModuloBean() {
    }

    public String editarModulo() {
        String action = "modulos";
        editarModulo_1(current_modulo.getId(), nombre_modulo, Double.parseDouble(alpha), Double.parseDouble(beta), Double.parseDouble(gamma), Double.parseDouble(kappa));
        return action;
    }

    @PostConstruct
    public void init() {
        //TODO valores iniciales
        current_modulo=sessionBean.getModulo();
        nombre_modulo=current_modulo.getNombre();
        alpha=Double.toString(current_modulo.getAlfa());
        beta=Double.toString(current_modulo.getBeta());
        gamma=Double.toString(current_modulo.getGamma());
        kappa=Double.toString(current_modulo.getKappa());
        nombre_modulo_vacio = alpha_error = beta_error
                = gamma_error = kappa_error = "";
    }

    public void nombreVacio(){
        if(nombre_modulo.isEmpty()){
            nombre_modulo_vacio = "El nombre del módulo no puede estar vacío.";
        } else {
            nombre_modulo_vacio = "";
        }
    }

    public void alphaVacio(){
        if(alpha.isEmpty()){
            alpha_error = "El valor alpha no puede estar vacio";
        } else {
            try {
                Double.parseDouble(alpha);
            } catch(Exception e){
                alpha_error = "El formato de alpha debe ser numerico.";
            }
            alpha_error = "";
        }
    }

    public void betaVacio(){
        if(beta.isEmpty()){
            beta_error = "El valor beta no puede estar vacio";
        } else {
            try {
                Double.parseDouble(beta);
            } catch(Exception e){
                beta_error = "El formato de beta debe ser numerico.";
            }
            beta_error = "";
        }
    }

    public void gammaVacio(){
        if(gamma.isEmpty()){
            gamma_error = "El valor gamma no puede estar vacio";
        } else {
            try {
                Double.parseDouble(gamma);
            } catch(Exception e){
                gamma_error = "El formato de gamma debe ser numerico.";
            }
            gamma_error = "";
        }
    }

    public void  kappaVacio(){
        if(kappa.isEmpty()){
            kappa_error = "El valor kappa no puede estar vacio";
        } else {
            try {
                Double.parseDouble(kappa);
            } catch(Exception e){
                kappa_error = "El formato de kappa debe ser numerico.";
            }
            kappa_error = "";
        }
    }

    private void editarModulo_1(long id, java.lang.String nombre, double alfa, double beta, double gamma, double kappa) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        services.ServiciosIweb port = service.getServiciosIwebPort();
        port.editarModulo(id, nombre, alfa, beta, gamma, kappa);
    }

    public String getNombre_modulo() {
        return nombre_modulo;
    }

    public void setNombre_modulo(String nombre_modulo) {
        this.nombre_modulo = nombre_modulo;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }

    public String getKappa() {
        return kappa;
    }

    public void setKappa(String kappa) {
        this.kappa = kappa;
    }

    public String getNombre_modulo_vacio() {
        return nombre_modulo_vacio;
    }

    public void setNombre_modulo_vacio(String nombre_modulo_vacio) {
        this.nombre_modulo_vacio = nombre_modulo_vacio;
    }

    public String getAlpha_error() {
        return alpha_error;
    }

    public void setAlpha_error(String alpha_error) {
        this.alpha_error = alpha_error;
    }

    public String getBeta_error() {
        return beta_error;
    }

    public void setBeta_error(String beta_error) {
        this.beta_error = beta_error;
    }

    public String getGamma_error() {
        return gamma_error;
    }

    public void setGamma_error(String gamma_error) {
        this.gamma_error = gamma_error;
    }

    public String getKappa_error() {
        return kappa_error;
    }

    public void setKappa_error(String kappa_error) {
        this.kappa_error = kappa_error;
    }
    
}
