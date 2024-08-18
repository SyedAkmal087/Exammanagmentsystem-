/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "students")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentsEntity.findAll", query = "SELECT s FROM StudentsEntity s"),
    @NamedQuery(name = "StudentsEntity.findByStudentId", query = "SELECT s FROM StudentsEntity s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "StudentsEntity.findByStudentName", query = "SELECT s FROM StudentsEntity s WHERE s.studentName = :studentName"),
    @NamedQuery(name = "StudentsEntity.findByBatchNo", query = "SELECT s FROM StudentsEntity s WHERE s.batchNo = :batchNo"),
    @NamedQuery(name = "StudentsEntity.findByRollNo", query = "SELECT s FROM StudentsEntity s WHERE s.rollNo = :rollNo"),
    @NamedQuery(name = "StudentsEntity.findByEmail", query = "SELECT s FROM StudentsEntity s WHERE s.email = :email"),
    @NamedQuery(name = "StudentsEntity.findByPassword", query = "SELECT s FROM StudentsEntity s WHERE s.password = :password")})
public class StudentsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_id")
    private Integer studentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "student_name")
    private String studentName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "batch_no")
    private String batchNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "roll_no")
    private String rollNo;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private List<SchedulesEntity> schedulesEntityList;
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @ManyToOne
    private SectionsEntity sectionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentId")
    private List<EnrollmentsEntity> enrollmentsEntityList;

    public StudentsEntity() {
    }

    public StudentsEntity(Integer studentId) {
        this.studentId = studentId;
    }

    public StudentsEntity(Integer studentId, String studentName, String batchNo, String rollNo, String email, String password) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.batchNo = batchNo;
        this.rollNo = rollNo;
        this.email = email;
        this.password = password;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<SchedulesEntity> getSchedulesEntityList() {
        return schedulesEntityList;
    }

    public void setSchedulesEntityList(List<SchedulesEntity> schedulesEntityList) {
        this.schedulesEntityList = schedulesEntityList;
    }

    public SectionsEntity getSectionId() {
        return sectionId;
    }

    public void setSectionId(SectionsEntity sectionId) {
        this.sectionId = sectionId;
    }

    @XmlTransient
    public List<EnrollmentsEntity> getEnrollmentsEntityList() {
        return enrollmentsEntityList;
    }

    public void setEnrollmentsEntityList(List<EnrollmentsEntity> enrollmentsEntityList) {
        this.enrollmentsEntityList = enrollmentsEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentsEntity)) {
            return false;
        }
        StudentsEntity other = (StudentsEntity) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.StudentsEntity[ studentId=" + studentId + " ]";
    }
    
}
