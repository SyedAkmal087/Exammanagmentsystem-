/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author syeda
 */
@Entity
@Table(name = "teachers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeachersEntity.findAll", query = "SELECT t FROM TeachersEntity t"),
    @NamedQuery(name = "TeachersEntity.findByTeacherId", query = "SELECT t FROM TeachersEntity t WHERE t.teacherId = :teacherId"),
    @NamedQuery(name = "TeachersEntity.findByTeacherName", query = "SELECT t FROM TeachersEntity t WHERE t.teacherName = :teacherName"),
    @NamedQuery(name = "TeachersEntity.findByTeacherPhoneNumber", query = "SELECT t FROM TeachersEntity t WHERE t.teacherPhoneNumber = :teacherPhoneNumber"),
    @NamedQuery(name = "TeachersEntity.findByTeacherEmail", query = "SELECT t FROM TeachersEntity t WHERE t.teacherEmail = :teacherEmail"),
    @NamedQuery(name = "TeachersEntity.findByTeacherPassword", query = "SELECT t FROM TeachersEntity t WHERE t.teacherPassword = :teacherPassword"),
    @NamedQuery(name = "TeachersEntity.findByAvailability", query = "SELECT t FROM TeachersEntity t WHERE t.availability = :availability")})
public class TeachersEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "teacher_name")
    private String teacherName;
    @Size(max = 15)
    @Column(name = "teacher_phone_number")
    private String teacherPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "teacher_email")
    private String teacherEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "teacher_password")
    private String teacherPassword;
    @Column(name = "availability")
    private Boolean availability;
    @OneToMany(mappedBy = "teachersId")
    private List<SeatingPlanEntity> seatingPlanEntityList;

    public TeachersEntity() {
    }

    public TeachersEntity(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public TeachersEntity(Integer teacherId, String teacherName, String teacherEmail, String teacherPassword) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherPassword = teacherPassword;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhoneNumber() {
        return teacherPhoneNumber;
    }

    public void setTeacherPhoneNumber(String teacherPhoneNumber) {
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @XmlTransient
    public List<SeatingPlanEntity> getSeatingPlanEntityList() {
        return seatingPlanEntityList;
    }

    public void setSeatingPlanEntityList(List<SeatingPlanEntity> seatingPlanEntityList) {
        this.seatingPlanEntityList = seatingPlanEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teacherId != null ? teacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeachersEntity)) {
            return false;
        }
        TeachersEntity other = (TeachersEntity) object;
        if ((this.teacherId == null && other.teacherId != null) || (this.teacherId != null && !this.teacherId.equals(other.teacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.TeachersEntity[ teacherId=" + teacherId + " ]";
    }
    
}
