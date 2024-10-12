/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.SeatingPlanNotFoundException;
import com.csworkshop.exammanagement.entity.DateSheetsEntity;
import com.csworkshop.exammanagement.entity.RoomsEntity;
import com.csworkshop.exammanagement.entity.SeatingPlanEntity;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.entity.TeachersEntity;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.DateSheetNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoExamFoundInThisRoom;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoExamFoundOnDateException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoSeatingPlanForTeacherException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.RoomNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.StudentNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.TeacherNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author syeda
 */
@Stateless
public class SeatingPlanSession implements SeatingPlanSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public SeatingPlanEntity addSeatingPlan(int dateSheetId, int roomId, int studentId, int teacherId)
            throws DateSheetNotFoundException, RoomNotFoundException, StudentNotFoundException, TeacherNotFoundException {

        SeatingPlanEntity seatingPlan = new SeatingPlanEntity();

        // Assuming you have corresponding services or methods to find these entities
        DateSheetsEntity dateSheet = em.find(DateSheetsEntity.class, dateSheetId);
        RoomsEntity room = em.find(RoomsEntity.class, roomId);
        StudentsEntity student = em.find(StudentsEntity.class, studentId);
        TeachersEntity teacher = em.find(TeachersEntity.class, teacherId);

        if (dateSheet == null) {
            throw new DateSheetNotFoundException("Date Sheet with ID " + dateSheetId + " not found.");
        }
        if (room == null) {
            throw new RoomNotFoundException("Room with ID " + roomId + " not found.");
        }
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
        }

        // Set the values for the seating plan
        seatingPlan.setDateSheetId(dateSheet);
        seatingPlan.setRoomId(room);
        seatingPlan.setStudentId(student);
        seatingPlan.setTeachersId(teacher);

        // Persist the new seating plan record in the database
        em.persist(seatingPlan);

        return seatingPlan;
    }

    @Override
    public List<SeatingPlanEntity> getSeatingPlanByRoomId(int roomId) throws RoomNotFoundException, NoExamFoundInThisRoom {

        // Check if the room exists in the database
        RoomsEntity room = em.find(RoomsEntity.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException("Room with ID " + roomId + " not found.");
        }

        // Query to get the seating plans by room ID
        List<SeatingPlanEntity> seatingPlans = em.createQuery("SELECT sp FROM SeatingPlanEntity sp WHERE sp.roomId = :roomId", SeatingPlanEntity.class)
                .setParameter("roomId", room)
                .getResultList();
        if (seatingPlans.isEmpty()) {
            throw new NoExamFoundInThisRoom("This Room is free there is no exam in this at this time...");
        }
        return seatingPlans;
    }

    @Override
    public List<SeatingPlanEntity> getSeatingPlanByTeacherId(int teacherId)
            throws TeacherNotFoundException, NoSeatingPlanForTeacherException {

        // Check if the teacher exists in the database
        TeachersEntity teacher = em.find(TeachersEntity.class, teacherId);
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
        }

        // Query to get the seating plans by teacher ID
        List<SeatingPlanEntity> seatingPlans = em.createQuery("SELECT sp FROM SeatingPlanEntity sp WHERE sp.teachersId = :teacherId", SeatingPlanEntity.class)
                .setParameter("teacherId", teacher)
                .getResultList();

        // Throw exception if no seating plans are found for this teacher
        if (seatingPlans.isEmpty()) {
            throw new NoSeatingPlanForTeacherException("No seating plans found for this teacher.");
        }

        return seatingPlans;
    }

    @Override
    public List<SeatingPlanEntity> getSeatingPlanByExamDate(Date examDate)
            throws NoExamFoundOnDateException {

        // Step 1: Find DateSheets by examDate
        List<DateSheetsEntity> dateSheets = em.createQuery(
                "SELECT ds FROM DateSheetsEntity ds WHERE ds.examDate = :examDate",
                DateSheetsEntity.class)
                .setParameter("examDate", examDate)
                .getResultList();

        // Check if any DateSheets were found
        if (dateSheets.isEmpty()) {
            throw new NoExamFoundOnDateException("No exams found on the specified date.");
        }

        // Step 2: Get Seating Plans based on the DateSheet IDs
        List<SeatingPlanEntity> seatingPlans = em.createQuery(
                "SELECT sp FROM SeatingPlanEntity sp WHERE sp.dateSheetId IN :dateSheetIds",
                SeatingPlanEntity.class)
                .setParameter("dateSheetIds", dateSheets)
                .getResultList();

        // Check if any seating plans were found
        if (seatingPlans.isEmpty()) {
            throw new NoExamFoundOnDateException("No seating plans found for the exams on this date.");
        }

        return seatingPlans;
    }
    
    
    @Override
    public List<SeatingPlanEntity> getAllSeatingPlans() throws SeatingPlanNotFoundException{
        Query qry = em.createQuery("Select s from SeatingPlanEntity s");
        List<SeatingPlanEntity> stPlans = qry.getResultList();
        if (stPlans == null) {
            throw new SeatingPlanNotFoundException("No seating plan found in DB");
        }
        return stPlans;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
