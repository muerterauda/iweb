/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "campa\u00f1a")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campa\u00f1a.findAll", query = "SELECT c FROM Campa\u00f1a c")
    , @NamedQuery(name = "Campa\u00f1a.findById", query = "SELECT c FROM Campa\u00f1a c WHERE c.id = :id")
    , @NamedQuery(name = "Campa\u00f1a.findByNombre", query = "SELECT c FROM Campa\u00f1a c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Campa\u00f1a.findByFechaInicio", query = "SELECT c FROM Campa\u00f1a c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Campa\u00f1a.findByFechaFin", query = "SELECT c FROM Campa\u00f1a c WHERE c.fechaFin = :fechaFin")})
public class Campaña implements Serializable {

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
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @JoinColumn(name = "modulo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modulo modulo;

    public Campaña() {
    }

    public Campaña(Long id) {
        this.id = id;
    }

    public Campaña(Long id, String nombre, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Campaña)) {
            return false;
        }
        Campaña other = (Campaña) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Campa\u00f1a[ id=" + id + " ]";
    }
    
}
