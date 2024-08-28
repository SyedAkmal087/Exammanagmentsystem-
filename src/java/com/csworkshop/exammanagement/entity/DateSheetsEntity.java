/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "date_sheets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DateSheetsEntity.findAll", query = "SELECT d FROM DateSheetsEntity d"),
    @NamedQuery(name = "DateSheetsEntity.findById", query = "SELECT d FROM DateSheetsEntity d WHERE d.id = :id"),
    @NamedQuery(name = "DateSheetsEntity.findByExamDate", query = "SELECT d FROM DateSheetsEntity d WHERE d.examDate = :examDate"),
    @NamedQuery(name = "DateSheetsEntity.findByCourseDesc", query = "SELECT d FROM DateSheetsEntity d WHERE d.courseDesc = :courseDesc"),
    @NamedQuery(name = "DateSheetsEntity.findByStartingHour", query = "SELECT d FROM DateSheetsEntity d WHERE d.startingHour = :startingHour"),
    @NamedQuery(name = "DateSheetsEntity.findByStartingMinutes", query = "SELECT d FROM DateSheetsEntity d WHERE d.startingMinutes = :startingMinutes"),
    @NamedQuery(name = "DateSheetsEntity.findByEndingHour", query = "SELECT d FROM DateSheetsEntity d WHERE d.endingHour = :endingHour"),
    @NamedQuery(name = "DateSheetsEntity.findByEndingMinutes", query = "SELECT d FROM DateSheetsEntity d WHERE d.endingMinutes = :endingMinutes")})
public class DateSheetsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "exam_date")
    @Temporal(TemporalType.DATE)
    private Date examDate;
    @Size(max = 255)
    @Column(name = "course_desc")
    private String courseDesc;
    @Column(name = "starting_hour")
    private Short startingHour;
    @Column(name = "starting_minutes")
    private Short startingMinutes;
    @Column(name = "ending_hour")
    private Short endingHour;
    @Column(name = "ending_minutes")
    private Short endingMinutes;
    @OneToMany(mappedBy = "dateSheetId")
    private List<SeatingPlanEntity> seatingPlanEntityList;
    @JoinColumn(name = "course_code", referencedColumnName = "course_code")
    @ManyToOne
    private CoursesEntity courseCode;

    public DateSheetsEntity() {
    }

    public DateSheetsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Short getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(Short startingHour) {
        this.startingHour = startingHour;
    }

    public Short getStartingMinutes() {
        return startingMinutes;
    }

    public void setStartingMinutes(Short startingMinutes) {
        this.startingMinutes = startingMinutes;
    }

    public Short getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(Short endingHour) {
        this.endingHour = endingHour;
    }

    public Short getEndingMinutes() {
        return endingMinutes;
    }

    public void setEndingMinutes(Short endingMinutes) {
        this.endingMinutes = endingMinutes;
    }

    @XmlTransient
    public List<SeatingPlanEntity> getSeatingPlanEntityList() {
        return seatingPlanEntityList;
    }

    public void setSeatingPlanEntityList(List<SeatingPlanEntity> seatingPlanEntityList) {
        this.seatingPlanEntityList = seatingPlanEntityList;
    }

    public CoursesEntity getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(CoursesEntity courseCode) {
        this.courseCode = courseCode;
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
        if (!(object instanceof DateSheetsEntity)) {
            return false;
        }
        DateSheetsEntity other = (DateSheetsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.DateSheetsEntity[ id=" + id + " ]";
    }
    
}
