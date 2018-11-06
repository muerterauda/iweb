/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "modulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m")
    , @NamedQuery(name = "Modulo.findById", query = "SELECT m FROM Modulo m WHERE m.id = :id")
    , @NamedQuery(name = "Modulo.findByNombre", query = "SELECT m FROM Modulo m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Modulo.findByAlfa", query = "SELECT m FROM Modulo m WHERE m.alfa = :alfa")
    , @NamedQuery(name = "Modulo.findByBeta", query = "SELECT m FROM Modulo m WHERE m.beta = :beta")
    , @NamedQuery(name = "Modulo.findByGamma", query = "SELECT m FROM Modulo m WHERE m.gamma = :gamma")
    , @NamedQuery(name = "Modulo.findByKappa", query = "SELECT m FROM Modulo m WHERE m.kappa = :kappa")})
public class Modulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alfa")
    private double alfa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "beta")
    private double beta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gamma")
    private double gamma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kappa")
    private double kappa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private List<Campaña> campañaList;

    public Modulo() {
    }

    public Modulo(Long id) {
        this.id = id;
    }

    public Modulo(Long id, String nombre, double alfa, double beta, double gamma, double kappa) {
        this.id = id;
        this.nombre = nombre;
        this.alfa = alfa;
        this.beta = beta;
        this.gamma = gamma;
        this.kappa = kappa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @XmlTransient
    public List<Campaña> getCampañaList() {
        return campañaList;
    }

    public void setCampañaList(List<Campaña> campañaList) {
        this.campañaList = campañaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Modulo[ id=" + id + " ]";
    }
    
}
