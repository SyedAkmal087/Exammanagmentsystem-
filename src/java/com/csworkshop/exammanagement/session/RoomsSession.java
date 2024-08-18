/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.RoomsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidRoomCapacityException;
import com.csworkshop.exammanagement.exceptions.InvalidRoomIdException;
import com.csworkshop.exammanagement.exceptions.InvalidRoomLocationException;
import com.csworkshop.exammanagement.exceptions.InvalidRoomNumberException;
import com.csworkshop.exammanagement.exceptions.RoomAlreadyExistException;
import com.csworkshop.exammanagement.exceptions.RoomNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

/**
 *
 * @author syeda
 */
@Stateless
public class RoomsSession implements RoomsSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

  

    @Override
    public RoomsEntity addClassRoom(String roomNumber, int roomCapacity, boolean roomAvailbility, String roomLocation) throws InvalidRoomNumberException, InvalidRoomCapacityException, InvalidRoomLocationException, RoomAlreadyExistException {
        RoomsEntity room = new RoomsEntity();
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new InvalidRoomNumberException("Room Number is Null or Empty");
        } else if (roomCapacity <= 0) {
            throw new InvalidRoomCapacityException("Room Capacity must be greater than zero");
        } else if (roomLocation == null || roomLocation.isEmpty()) {
            throw new InvalidRoomLocationException("Room Location is Null or Empty");
        } else {
            RoomsEntity existingRoom = null;
            try {
                existingRoom = findRoomByRoomNumber(roomNumber);
                throw new RoomAlreadyExistException("Room already Exist with room number: " + roomNumber);

            } catch (RoomNotFoundException e) {
                room.setRoomNumber(roomNumber);
                room.setRoomCapacity(roomCapacity);
                room.setIsAvailable(roomAvailbility);
                room.setRoomLocation(roomLocation);
                persist(room);

            }
        }
        return room;

    }

    @Override
    public RoomsEntity findRoomById(int roomId) throws RoomNotFoundException, InvalidRoomIdException {
        if (roomId <= 0) {
            throw new InvalidRoomIdException("Invalid Room ID: " + roomId);
        }
        RoomsEntity room = em.find(RoomsEntity.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException("Room Not Found with id: " + roomId);
        }
        return room;

    }

    @Override
    public RoomsEntity findRoomByRoomNumber(String roomNumber) throws RoomNotFoundException {
        RoomsEntity room;
        Query qry = em.createQuery("select r from RoomsEntity r where r.roomNumber=:roomNumber");
        qry.setParameter("roomNumber", roomNumber);
        try {
            room = (RoomsEntity) qry.getSingleResult();
        } catch (NoResultException e) {

            throw new RoomNotFoundException("No Room found with Room Number: " + roomNumber);
        }

        return room;
    }

    @Override
    public void deleteRoomById(int roomId) throws RoomNotFoundException, InvalidRoomIdException {
        RoomsEntity room = findRoomById(roomId);
        em.remove(room);
    }

    @Override
    public RoomsEntity updateRoom(int RoomId, String roomNumber, int capacity, boolean availbility, String roomLocation) throws RoomNotFoundException, InvalidRoomCapacityException, InvalidRoomLocationException, RoomAlreadyExistException, InvalidRoomNumberException, InvalidRoomIdException {
        RoomsEntity room = findRoomById(RoomId);
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new InvalidRoomNumberException("Room Number is Null or Empty");
        } else if (capacity <= 0) {
            throw new InvalidRoomCapacityException("Room Capacity must be greater than zero");
        } else if (roomLocation == null || roomLocation.isEmpty()) {
            throw new InvalidRoomLocationException("Room Location is Null or Empty");
        } else {
            RoomsEntity existingRoom = null;
            try {
                existingRoom = findRoomByRoomNumber(roomNumber);
                throw new RoomAlreadyExistException("Room already Exist with room number: " + roomNumber);

            } catch (RoomNotFoundException e) {
                room.setRoomNumber(roomNumber);
                room.setRoomCapacity(capacity);
                room.setIsAvailable(availbility);
                room.setRoomLocation(roomLocation);
                em.merge(room);

            }
        }
        return room;
    }

//    @Override
//    public RoomsEntity updateRoom(int RoomId, String roomNumber, int capacity, boolean avaibility, String roomLocation) throws RoomNotFoundException {
//        RoomsEntity room = findRoomById(RoomId);
//        
//        if (room != null) {
//            room.setRoomNumber(roomNumber);
//            room.setRoomCapacity(capacity);
//            room.setIsAvailable(avaibility);
//            room.setRoomLocation(roomLocation);
//            em.merge(room);
//        }
//        return room;
//    }
    @Override
    public List<RoomsEntity> getAllRooms() throws RoomNotFoundException {
        Query qry = em.createQuery("Select r from RoomsEntity r");
        List<RoomsEntity> rooms = qry.getResultList();
        if (rooms == null) {
            throw new RoomNotFoundException("No Room found in DB");
        }
        return rooms;
    }

    public void persist(Object object) {
        em.persist(object);
    }


}
