/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gishoauth.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ZO-PC
 */
@Entity
@Table(name = "UserRole")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findByIdUserRole", query = "SELECT u FROM UserRole u WHERE u.idUserRole = :idUserRole")})
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idUserRole")
    private Integer idUserRole;
    @Basic(optional = false)
    @Column(name = "libelleRole")
    private String libelleRole;
    @JoinColumn(name = "login", referencedColumnName = "login", nullable=false,insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private GishoUser login;
    

    public UserRole() {
    }

    public UserRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public Integer getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(Integer idUserRole) {
        this.idUserRole = idUserRole;
    }

    public GishoUser getLogin() {
        return login;
    }

    public void setLogin(GishoUser login) {
        this.login = login;
    }

    public String getLibelleRole() {
        return libelleRole;
    }

    public void setLibelleRole(String libelleRole) {
        this.libelleRole = libelleRole;
    }
    
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserRole != null ? idUserRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.idUserRole == null && other.idUserRole != null) || (this.idUserRole != null && !this.idUserRole.equals(other.idUserRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gishoauth.model.entity.UserRole[ idUserRole=" + idUserRole + " ]";
    }
    
}
