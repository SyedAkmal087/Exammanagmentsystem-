/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
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
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface RoomsSessionRemote {

    public RoomsEntity addClassRoom(String roomNumber, int roomCapacity, boolean roomAvailbility, String roomLocation) throws InvalidRoomNumberException,
            InvalidRoomCapacityException,InvalidRoomLocationException,RoomAlreadyExistException;

    public RoomsEntity findRoomById(int roomId) throws RoomNotFoundException,InvalidRoomIdException ;

 
   public RoomsEntity findRoomByRoomNumber(String roomNumber) throws RoomNotFoundException;

    public void deleteRoomById(int RoomId)throws RoomNotFoundException,InvalidRoomIdException ;

    public RoomsEntity updateRoom(int RoomId, String RoomNumber, int capacity, boolean availbility, String RoomLocation) throws RoomNotFoundException,
            InvalidRoomCapacityException,InvalidRoomLocationException,RoomAlreadyExistException,InvalidRoomNumberException,InvalidRoomIdException;

    public List<RoomsEntity> getAllRooms() throws RoomNotFoundException;

    
    
}
