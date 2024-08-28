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
@Table(name = "seating_plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeatingPlanEntity.findAll", query = "SELECT s FROM SeatingPlanEntity s"),
    @NamedQuery(name = "SeatingPlanEntity.findBySeatingId", query = "SELECT s FROM SeatingPlanEntity s WHERE s.seatingId = :seatingId")})
public class SeatingPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "seating_id")
    private Integer seatingId;
    @JoinColumn(name = "date_sheet_id", referencedColumnName = "id")
    @ManyToOne
    private DateSheetsEntity dateSheetId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne
    private RoomsEntity roomId;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne
    private StudentsEntity studentId;
    @JoinColumn(name = "teachers_id", referencedColumnName = "teacher_id")
    @ManyToOne
    private TeachersEntity teachersId;

    public SeatingPlanEntity() {
    }

    public SeatingPlanEntity(Integer seatingId) {
        this.seatingId = seatingId;
    }

    public Integer getSeatingId() {
        return seatingId;
    }

    public void setSeatingId(Integer seatingId) {
        this.seatingId = seatingId;
    }

    public DateSheetsEntity getDateSheetId() {
        return dateSheetId;
    }

    public void setDateSheetId(DateSheetsEntity dateSheetId) {
        this.dateSheetId = dateSheetId;
    }

    public RoomsEntity getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomsEntity roomId) {
        this.roomId = roomId;
    }

    public StudentsEntity getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentsEntity studentId) {
        this.studentId = studentId;
    }

    public TeachersEntity getTeachersId() {
        return teachersId;
    }

    public void setTeachersId(TeachersEntity teachersId) {
        this.teachersId = teachersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seatingId != null ? seatingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeatingPlanEntity)) {
            return false;
        }
        SeatingPlanEntity other = (SeatingPlanEntity) object;
        if ((this.seatingId == null && other.seatingId != null) || (this.seatingId != null && !this.seatingId.equals(other.seatingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.SeatingPlanEntity[ seatingId=" + seatingId + " ]";
    }
    
}
