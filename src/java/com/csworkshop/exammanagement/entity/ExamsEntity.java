/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author syeda
 */
@Entity
@Table(name = "exams")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamsEntity.findAll", query = "SELECT e FROM ExamsEntity e"),
    @NamedQuery(name = "ExamsEntity.findByExamId", query = "SELECT e FROM ExamsEntity e WHERE e.examId = :examId"),
    @NamedQuery(name = "ExamsEntity.findByExamDate", query = "SELECT e FROM ExamsEntity e WHERE e.examDate = :examDate"),
    @NamedQuery(name = "ExamsEntity.findByStartTime", query = "SELECT e FROM ExamsEntity e WHERE e.startTime = :startTime"),
    @NamedQuery(name = "ExamsEntity.findByEndTime", query = "SELECT e FROM ExamsEntity e WHERE e.endTime = :endTime")})
public class ExamsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exam_id")
    private Integer examId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "exam_date")
    @Temporal(TemporalType.DATE)
    private Date examDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private CoursesEntity courseId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    @ManyToOne(optional = false)
    private RoomsEntity roomId;
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @ManyToOne(optional = false)
    private SectionsEntity sectionId;
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @ManyToOne(optional = false)
    private TeachersEntity teacherId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examId")
    private List<SchedulesEntity> schedulesEntityList;

    public ExamsEntity() {
    }

    public ExamsEntity(Integer examId) {
        this.examId = examId;
    }

    public ExamsEntity(Integer examId, Date examDate, Date startTime, Date endTime) {
        this.examId = examId;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public CoursesEntity getCourseId() {
        return courseId;
    }

    public void setCourseId(CoursesEntity courseId) {
        this.courseId = courseId;
    }

    public RoomsEntity getRoomId() {
        return roomId;
    }

    public void setRoomId(RoomsEntity roomId) {
        this.roomId = roomId;
    }

    public SectionsEntity getSectionId() {
        return sectionId;
    }

    public void setSectionId(SectionsEntity sectionId) {
        this.sectionId = sectionId;
    }

    public TeachersEntity getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(TeachersEntity teacherId) {
        this.teacherId = teacherId;
    }

    @XmlTransient
    public List<SchedulesEntity> getSchedulesEntityList() {
        return schedulesEntityList;
    }

    public void setSchedulesEntityList(List<SchedulesEntity> schedulesEntityList) {
        this.schedulesEntityList = schedulesEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examId != null ? examId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamsEntity)) {
            return false;
        }
        ExamsEntity other = (ExamsEntity) object;
        if ((this.examId == null && other.examId != null) || (this.examId != null && !this.examId.equals(other.examId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.ExamsEntity[ examId=" + examId + " ]";
    }
    
}
