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
@Table(name = "sections")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SectionsEntity.findAll", query = "SELECT s FROM SectionsEntity s"),
    @NamedQuery(name = "SectionsEntity.findBySectionId", query = "SELECT s FROM SectionsEntity s WHERE s.sectionId = :sectionId"),
    @NamedQuery(name = "SectionsEntity.findBySectionName", query = "SELECT s FROM SectionsEntity s WHERE s.sectionName = :sectionName")})
public class SectionsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "section_id")
    private Integer sectionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "section_name")
    private String sectionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectionId")
    private List<ExamsEntity> examsEntityList;
    @OneToMany(mappedBy = "sectionId")
    private List<StudentsEntity> studentsEntityList;

    public SectionsEntity() {
    }

    public SectionsEntity(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public SectionsEntity(Integer sectionId, String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @XmlTransient
    public List<ExamsEntity> getExamsEntityList() {
        return examsEntityList;
    }

    public void setExamsEntityList(List<ExamsEntity> examsEntityList) {
        this.examsEntityList = examsEntityList;
    }

    @XmlTransient
    public List<StudentsEntity> getStudentsEntityList() {
        return studentsEntityList;
    }

    public void setStudentsEntityList(List<StudentsEntity> studentsEntityList) {
        this.studentsEntityList = studentsEntityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sectionId != null ? sectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SectionsEntity)) {
            return false;
        }
        SectionsEntity other = (SectionsEntity) object;
        if ((this.sectionId == null && other.sectionId != null) || (this.sectionId != null && !this.sectionId.equals(other.sectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.SectionsEntity[ sectionId=" + sectionId + " ]";
    }
    
}
