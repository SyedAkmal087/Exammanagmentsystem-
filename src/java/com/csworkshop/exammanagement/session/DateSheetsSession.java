/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.CoursesEntity;
import com.csworkshop.exammanagement.entity.DateSheetsEntity;
import com.csworkshop.exammanagement.exceptions.CourseNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.AlreadyExistException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.CourseCodeNotFoundExceptions;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.CourseDescNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.EndingHourInvalidExcption;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.EndingHourNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.ExamDateNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.StartingHourInvalidException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.StartingHourNullException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseCodeException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author syeda
 */
@Stateless
public class DateSheetsSession implements DateSheetsSessionRemote {

    @EJB
    CoursesSessionRemote CoursesRemoteObj;
    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

    @Override
    public DateSheetsEntity GetExamByCourseCode(String CourseCode)
            throws CourseCodeNotFoundExceptions, InvalidCourseCodeException {
        DateSheetsEntity obj;
        if (CourseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course Code is Not Valid Please Try With Valid Course Code...!");
        } else {

            Query qry = em.createQuery("select s from DateSheetsEntity s where s.courseCode = :CourseCode");
            qry.setParameter("CourseCode", CourseCode);

            List<DateSheetsEntity> datesheet;
            datesheet = qry.getResultList();
            obj = (DateSheetsEntity) datesheet;
            if (datesheet.isEmpty()) {
                throw new CourseCodeNotFoundExceptions("No Course is  found for this Course Code :" + CourseCode);
            }
        }
        return obj;
    }

    public DateSheetsEntity addDateSheet(Date ExamDate, String CourseCode, String CouseDesc, short StartingHour, short StartingMinutes, short EndingHour, short EndingMinutes)
            throws ExamDateNullException, CourseDescNullException,
            StartingHourNullException, EndingHourNullException, StartingHourInvalidException, EndingHourInvalidExcption,
            InvalidCourseCodeException, CourseNotFoundException, AlreadyExistException 
    {
        DateSheetsEntity DseObj = new DateSheetsEntity();
        CoursesEntity CourseCodeObj;
        
        try {
            DseObj = GetExamByCourseCode(CourseCode);
            throw new AlreadyExistException("This Course Code is already Exits...!");
        } catch (CourseCodeNotFoundExceptions ex) {
            if (ExamDate == null) {
            throw new ExamDateNullException("Please Write Exam Date Also...!");
            } 
            else {
            DseObj.setExamDate(ExamDate);
            CourseCodeObj = CoursesRemoteObj.findCourseByCode(CourseCode);
            DseObj.setCourseCode(CourseCodeObj);
            DseObj.setCourseDesc(CouseDesc);
            DseObj.setStartingHour(StartingHour);
            DseObj.setStartingMinutes(StartingMinutes);
            DseObj.setEndingHour(EndingHour);
            DseObj.setEndingMinutes(EndingMinutes);
            }
            
        }
        em.persist(DseObj);
        return DseObj;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
