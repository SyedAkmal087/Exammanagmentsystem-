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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "schedules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SchedulesEntity.findAll", query = "SELECT s FROM SchedulesEntity s"),
    @NamedQuery(name = "SchedulesEntity.findByScheduleId", query = "SELECT s FROM SchedulesEntity s WHERE s.scheduleId = :scheduleId"),
    @NamedQuery(name = "SchedulesEntity.findBySeatNumber", query = "SELECT s FROM SchedulesEntity s WHERE s.seatNumber = :seatNumber")})
public class SchedulesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seat_number")
    private int seatNumber;
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id")
    @ManyToOne(optional = false)
    private ExamsEntity examId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne(optional = false)
    private RoomsEntity roomId;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private StudentsEntity studentId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @ManyToOne(optional = false)
    private TeachersEntity teacherId;

    public SchedulesEntity() {
    }

    public SchedulesEntity(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public SchedulesEntity(Integer scheduleId, int seatNumber) {
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public ExamsEntity getExamId() {
        return examId;
    }

    public void setExamId(ExamsEntity examId) {
        this.examId = examId;
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

    public TeachersEntity getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(TeachersEntity teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleId != null ? scheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchedulesEntity)) {
            return false;
        }
        SchedulesEntity other = (SchedulesEntity) object;
        if ((this.scheduleId == null && other.scheduleId != null) || (this.scheduleId != null && !this.scheduleId.equals(other.scheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.SchedulesEntity[ scheduleId=" + scheduleId + " ]";
    }
    
}
