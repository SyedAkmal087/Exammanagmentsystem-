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
@Table(name = "rooms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomsEntity.findAll", query = "SELECT r FROM RoomsEntity r"),
    @NamedQuery(name = "RoomsEntity.findByRoomId", query = "SELECT r FROM RoomsEntity r WHERE r.roomId = :roomId"),
    @NamedQuery(name = "RoomsEntity.findByRoomNumber", query = "SELECT r FROM RoomsEntity r WHERE r.roomNumber = :roomNumber"),
    @NamedQuery(name = "RoomsEntity.findByRoomCapacity", query = "SELECT r FROM RoomsEntity r WHERE r.roomCapacity = :roomCapacity"),
    @NamedQuery(name = "RoomsEntity.findByIsAvailable", query = "SELECT r FROM RoomsEntity r WHERE r.isAvailable = :isAvailable"),
    @NamedQuery(name = "RoomsEntity.findByRoomLocation", query = "SELECT r FROM RoomsEntity r WHERE r.roomLocation = :roomLocation")})
public class RoomsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "room_id")
    private Integer roomId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "room_number")
    private String roomNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "room_capacity")
    private int roomCapacity;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @Size(max = 100)
    @Column(name = "room_location")
    private String roomLocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private List<ExamsEntity> examsEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private List<SchedulesEntity> schedulesEntityList;

    public RoomsEntity() {
    }

    public RoomsEntity(Integer roomId) {
        this.roomId = roomId;
    }

    public RoomsEntity(Integer roomId, String roomNumber, int roomCapacity) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    @XmlTransient
    public List<ExamsEntity> getExamsEntityList() {
        return examsEntityList;
    }

    public void setExamsEntityList(List<ExamsEntity> examsEntityList) {
        this.examsEntityList = examsEntityList;
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
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomsEntity)) {
            return false;
        }
        RoomsEntity other = (RoomsEntity) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.csworkshop.exammanagement.entity.RoomsEntity[ roomId=" + roomId + " ]";
    }
    
}
