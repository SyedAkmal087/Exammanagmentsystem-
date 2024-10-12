/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.SeatingPlanEntity;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.DateSheetNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoExamFoundInThisRoom;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoExamFoundOnDateException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.NoSeatingPlanForTeacherException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.RoomNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.SeatingPlanNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.StudentNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan.TeacherNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface SeatingPlanSessionRemote {

    public SeatingPlanEntity addSeatingPlan(int dateSheetId, int roomId, int studentId, int teacherId) throws DateSheetNotFoundException, RoomNotFoundException, StudentNotFoundException, TeacherNotFoundException;

    public List<SeatingPlanEntity> getSeatingPlanByRoomId(int roomId) throws RoomNotFoundException, NoExamFoundInThisRoom;

    public List<SeatingPlanEntity> getSeatingPlanByTeacherId(int teacherId) throws TeacherNotFoundException, NoSeatingPlanForTeacherException;

    public List<SeatingPlanEntity> getSeatingPlanByExamDate(Date examDate) throws NoExamFoundOnDateException;

    public List<SeatingPlanEntity> getAllSeatingPlans() throws SeatingPlanNotFoundException;
    
}
