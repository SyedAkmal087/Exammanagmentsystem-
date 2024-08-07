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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "enrollments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnrollmentsEntity.findAll", query = "SELECT e FROM EnrollmentsEntity e"),
    @NamedQuery(name = "EnrollmentsEntity.findByEnrollmentId", query = "SELECT e FROM EnrollmentsEntity e WHERE e.enrollmentId = :enrollmentId")})
public class EnrollmentsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "enrollment_id")
    private Integer enrollmentId;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private CoursesEntity courseId;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private StudentsEntity studentId;

    public EnrollmentsEntity() {
    }

    public EnrollmentsEntity(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public CoursesEntity getCourseId() {
        return courseId;
    }

    public void setCourseId(CoursesEntity courseId) {
        this.courseId = courseId;
    }

    public StudentsEntity getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentsEntity studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enrollmentId != null ? enrollmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnrollmentsEntity)) {
            return false;
        }
        EnrollmentsEntity other = (EnrollmentsEntity) object;
        if ((this.enrollmentId == null && other.enrollmentId != null) || (this.enrollmentId != null && !this.enrollmentId.equals(other.enrollmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.EnrollmentsEntity[ enrollmentId=" + enrollmentId + " ]";
    }
    
}
