/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gishoauth.model.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ZO-PC
 */
@Entity
@Table(name = "GishoApplicationUser")
@NamedQueries({
    @NamedQuery(name = "GishoUser.findAll", query = "SELECT g FROM GishoUser g"),
    @NamedQuery(name = "GishoUser.findByLogin", query = "SELECT g FROM GishoUser g WHERE g.login = :login"),
    @NamedQuery(name = "GishoUser.findByPassword", query = "SELECT g FROM GishoUser g WHERE g.password = :password")})
public class GishoUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "pasword")
    private String password;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "login")
    private Set<UserRole> userRoleList;

    public GishoUser() {
    }

    public GishoUser(String login) {
        this.login = login;
    }

    public GishoUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pasword) {
        this.password = pasword;
    }

    public Set<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(Set<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GishoUser)) {
            return false;
        }
        GishoUser other = (GishoUser) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gishoauth.model.entity.GishoUser[ login=" + login + " ]";
    }
    
}
