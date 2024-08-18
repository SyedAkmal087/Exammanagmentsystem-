/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdminEntity.findAll", query = "SELECT a FROM AdminEntity a"),
    @NamedQuery(name = "AdminEntity.findByAdminId", query = "SELECT a FROM AdminEntity a WHERE a.adminId = :adminId"),
    @NamedQuery(name = "AdminEntity.findByAdminName", query = "SELECT a FROM AdminEntity a WHERE a.adminName = :adminName"),
    @NamedQuery(name = "AdminEntity.findByAdminEmail", query = "SELECT a FROM AdminEntity a WHERE a.adminEmail = :adminEmail"),
    @NamedQuery(name = "AdminEntity.findByAdminPhoneNumber", query = "SELECT a FROM AdminEntity a WHERE a.adminPhoneNumber = :adminPhoneNumber"),
    @NamedQuery(name = "AdminEntity.findByAdminPassword", query = "SELECT a FROM AdminEntity a WHERE a.adminPassword = :adminPassword")})
public class AdminEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "admin_id")
    private Integer adminId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "admin_name")
    private String adminName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "admin_email")
    private String adminEmail;
    @Size(max = 15)
    @Column(name = "admin_phone_number")
    private String adminPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "admin_password")
    private String adminPassword;

    public AdminEntity() {
    }

    public AdminEntity(Integer adminId) {
        this.adminId = adminId;
    }

    public AdminEntity(Integer adminId, String adminName, String adminEmail, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminId != null ? adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminEntity)) {
            return false;
        }
        AdminEntity other = (AdminEntity) object;
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.AdminEntity[ adminId=" + adminId + " ]";
    }
    
}
