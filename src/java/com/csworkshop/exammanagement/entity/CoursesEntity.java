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
@Table(name = "courses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoursesEntity.findAll", query = "SELECT c FROM CoursesEntity c"),
    @NamedQuery(name = "CoursesEntity.findByCourseId", query = "SELECT c FROM CoursesEntity c WHERE c.courseId = :courseId"),
    @NamedQuery(name = "CoursesEntity.findByCourseName", query = "SELECT c FROM CoursesEntity c WHERE c.courseName = :courseName"),
    @NamedQuery(name = "CoursesEntity.findByCourseCode", query = "SELECT c FROM CoursesEntity c WHERE c.courseCode = :courseCode")})
public class CoursesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "course_id")
    private Integer courseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "course_name")
    private String courseName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "course_code")
    private String courseCode;
    @OneToMany(mappedBy = "courseCode")
    private List<DateSheetsEntity> dateSheetsEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private List<EnrollmentsEntity> enrollmentsEntityList;

    public CoursesEntity() {
    }

    public CoursesEntity(Integer courseId) {
        this.courseId = courseId;
    }

    public CoursesEntity(Integer courseId, String courseName, String courseCode) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @XmlTransient
    public List<DateSheetsEntity> getDateSheetsEntityList() {
        return dateSheetsEntityList;
    }

    public void setDateSheetsEntityList(List<DateSheetsEntity> dateSheetsEntityList) {
        this.dateSheetsEntityList = dateSheetsEntityList;
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
        hash += (courseId != null ? courseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoursesEntity)) {
            return false;
        }
        CoursesEntity other = (CoursesEntity) object;
        if ((this.courseId == null && other.courseId != null) || (this.courseId != null && !this.courseId.equals(other.courseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return courseCode;
    }
    
}
