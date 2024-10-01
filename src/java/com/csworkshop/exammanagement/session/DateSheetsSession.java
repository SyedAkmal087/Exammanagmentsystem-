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
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.EmptyListException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.EndingTimeInvalidExcption;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.EndingTimeNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.ExamDateNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.ExamIdNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.ExamIdNullException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.ExamNotFoundException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.StartingTimeInvalidException;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.StartingtimeNullException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseCodeException;
import java.util.Date;
import java.util.List;


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
    public List<DateSheetsEntity> GetAllExamsData()
            throws EmptyListException {
        List<DateSheetsEntity> DseList;
        DseList = em.createNamedQuery("DateSheetsEntity.findAll", DateSheetsEntity.class).getResultList();
        if (DseList.isEmpty()) {
            throw new EmptyListException("No record Found...!");
        }
        return DseList;
    }

    @Override
    public List<DateSheetsEntity> GetExamByCourseCode(CoursesEntity Coursecode)
            throws CourseCodeNotFoundExceptions, InvalidCourseCodeException {

        List<DateSheetsEntity> datesheet;
        if (Coursecode.getCourseCode().isEmpty()) {
            throw new InvalidCourseCodeException("Course Code is Not Valid Please Try With Valid Course Code...!");
        } else {

            Query qry = em.createQuery("select s from DateSheetsEntity s where s.courseCode.courseCode = :Coursecode");
            qry.setParameter("Coursecode", Coursecode.getCourseCode());
            datesheet = qry.getResultList();
            if (datesheet.isEmpty()) {
                throw new CourseCodeNotFoundExceptions("No Course is  found for this Course Code :" + Coursecode);
            }
        }
        return datesheet;
    }

    @Override
    public DateSheetsEntity addDateSheet(Date ExamDate, String CourseCode, String CourseDesc, int StartingHour, int StartingMinutes, int EndingHour, int EndingMinutes)
            throws ExamDateNullException, CourseDescNullException,
            StartingtimeNullException, EndingTimeNullException, StartingTimeInvalidException, EndingTimeInvalidExcption,
            InvalidCourseCodeException, AlreadyExistException, CourseNotFoundException {

        DateSheetsEntity DseObj = new DateSheetsEntity();
        List<DateSheetsEntity> existingexams;
        CoursesEntity CourseCodeObj;
        CourseCodeObj = CoursesRemoteObj.findCourseByCode(CourseCode);

        try {
            existingexams = GetExamByCourseCode(CourseCodeObj);
            if (!existingexams.isEmpty()) {
                throw new AlreadyExistException("This Course Code is already Exits...!");
            }

        } catch (CourseCodeNotFoundExceptions ex1) {
            if (ExamDate == null) {
                throw new ExamDateNullException("Please Write Exam Date Also...!");
            } else {
                DseObj.setExamDate(ExamDate);
                DseObj.setCourseCode(CourseCodeObj);
                if (CourseDesc.isEmpty()) {
                    throw new CourseDescNullException("Please Your are required to write the Description of Course...!");
                }
                {
                    DseObj.setCourseDesc(CourseDesc);
                }
                if (StartingHour < 1 || StartingMinutes < 0) {
                    throw new StartingtimeNullException("Please Enter Starting Time of the Exam!");
                } else if (StartingHour > 23 || StartingMinutes > 59) {
                    throw new StartingTimeInvalidException("Starting hours/Minutes are Invalid Try Again with Valid Hours/Minutes must be (1-23) and Minutes (0-59)...!");
                } else {
                    DseObj.setStartingHour(StartingHour);
                    DseObj.setStartingMinutes(StartingMinutes);
                }
                if (EndingHour < 1 || EndingMinutes < 0) {
                    throw new EndingTimeNullException("Please Enter Ending Time of the Exam!");
                } else if (StartingHour > 23 || StartingMinutes > 59) {
                    throw new EndingTimeInvalidExcption("Ending hours are Invalid Try Again with Valid Hours must be (1-23) and Minutes (0-59)...!");
                } else {
                    DseObj.setEndingHour(EndingHour);
                    DseObj.setEndingMinutes(EndingMinutes);
                }
            }
        }
        em.persist(DseObj);
        return DseObj;
    }

    @Override
    public DateSheetsEntity getExamById(int id)
            throws ExamIdNullException, ExamIdNotFoundException {
        if (id < 1) {
            throw new ExamIdNullException("Please Enter Exam Id...!");
        }
        DateSheetsEntity obj = em.find(DateSheetsEntity.class, id);
        if (obj == null) {
            throw new ExamIdNotFoundException("There is no Record Found Againt this Id : " + id);
        }
        return obj;
    }

    @Override
    public List<DateSheetsEntity> getExamByCourse(String Code)
            throws CourseCodeNotFoundExceptions, InvalidCourseCodeException {
        CoursesEntity CourseCodeObj;
        if (Code.isEmpty()) {
            throw new InvalidCourseCodeException("Please Write Course Code ....!");
        }

        try {
            CourseCodeObj = CoursesRemoteObj.findCourseByCode(Code);
        } catch (CourseNotFoundException ex) {
            throw new CourseCodeNotFoundExceptions("There is no Record Found Againt this Code : " + Code);
        }

        List<DateSheetsEntity> obj = em.createNamedQuery("DateSheetsEntity.findByCourseCode", DateSheetsEntity.class)
                .setParameter("courseCode", CourseCodeObj.getCourseCode())
                .getResultList();
        if (obj.isEmpty()) {
            throw new CourseCodeNotFoundExceptions("There is no Record Found Againt this Code : " + Code);
        }
        return obj;
    }

    @Override
    public List<DateSheetsEntity> getExamByStartingTime(int StartHour, int startMinutes)
            throws StartingtimeNullException, StartingTimeInvalidException, ExamNotFoundException {
        List<DateSheetsEntity> obj;
        if (StartHour < 1 || startMinutes < 0) {
            throw new StartingtimeNullException("Please Write valid Hours and Minutes...!");
        } else if (StartHour > 23 || startMinutes > 59) {
            throw new StartingTimeInvalidException("Starting hours/Minutes are Invalid Try Again with Valid Hours/Minutes must be (1-23) and Minutes (0-59)...!");
        } else {
            obj = em.createNamedQuery("DateSheetsEntity.findByStartingHoursAndMinutes", DateSheetsEntity.class)
                    .setParameter("startingHour", StartHour)
                    .setParameter("startingMinutes", startMinutes)
                    .getResultList();
        }
        if (obj.isEmpty()) {
            throw new ExamNotFoundException("There is no Exam on this Starting Time : " + StartHour + ":" + startMinutes);
        }
        return obj;
    }

    @Override
    public List<DateSheetsEntity> getExamByEndingTime(int EndHour, int EndMinutes)
            throws EndingTimeNullException, EndingTimeInvalidExcption, ExamNotFoundException {
        List<DateSheetsEntity> obj;
        if (EndHour < 1 || EndMinutes < 0) {
            throw new EndingTimeNullException("Please Write valid Hours and Minutes...!");
        } else if (EndHour > 23 || EndMinutes > 59) {
            throw new EndingTimeInvalidExcption("Ending hours/Minutes are Invalid Try Again with Valid Hours/Minutes must be (1-23) and Minutes (0-59)...!");
        } else {
            obj = em.createNamedQuery("DateSheetsEntity.findByEndingHoursAndMinutes", DateSheetsEntity.class)
                    .setParameter("endingHour", EndHour)
                    .setParameter("endingMinutes", EndMinutes)
                    .getResultList();
        }
        if (obj.isEmpty()) {
            throw new ExamNotFoundException("There is no Exam on this Starting Time : " + EndHour + ":" + EndMinutes);
        }
        return obj;
    }

    @Override
    public void RemoveExamFromDateSheetById(int id) throws ExamIdNullException, ExamIdNotFoundException {
        DateSheetsEntity removeObject = getExamById(id);
        em.remove(removeObject);
    }

    @Override
    public DateSheetsEntity updateDateSheet(int dateSheetId, Date ExamDate, String CourseCode, String CourseDesc, int StartingHour, int StartingMinutes, int EndingHour, int EndingMinutes)
            throws ExamDateNullException, CourseDescNullException,
            StartingtimeNullException, EndingTimeNullException, StartingTimeInvalidException, EndingTimeInvalidExcption,
            InvalidCourseCodeException, CourseNotFoundException, EmptyListException {

        DateSheetsEntity DseObj;
        CoursesEntity CourseCodeObj;

        // Find the DateSheet entity by its ID
        DseObj = em.find(DateSheetsEntity.class, dateSheetId);
        if (DseObj == null) {
            throw new EmptyListException("DateSheet not found with the provided ID.");
        }
        // Find the course by course code
        CourseCodeObj = CoursesRemoteObj.findCourseByCode(CourseCode);
        if (CourseCodeObj == null) {
            throw new CourseNotFoundException("DateSheet not found with the provided ID.");
        }
        // Update the fields if validation passes
        if (ExamDate == null) {
            throw new ExamDateNullException("Exam Date cannot be null.");
        }
        DseObj.setExamDate(ExamDate);
        DseObj.setCourseCode(CourseCodeObj);
        if (CourseDesc == null || CourseDesc.isEmpty()) {
            throw new CourseDescNullException("Course description cannot be empty.");
        }
        DseObj.setCourseDesc(CourseDesc);
        if (StartingHour < 1 || StartingMinutes < 0 || StartingHour > 23 || StartingMinutes > 59) {
            throw new StartingTimeInvalidException("Starting hours/minutes are invalid. Must be hours (1-23) and minutes (0-59).");
        }
        DseObj.setStartingHour(StartingHour);
        DseObj.setStartingMinutes(StartingMinutes);
        if (EndingHour < 1 || EndingMinutes < 0 || EndingHour > 23 || EndingMinutes > 59) {
            throw new EndingTimeInvalidExcption("Ending hours/minutes are invalid. Must be hours (1-23) and minutes (0-59).");
        }
        DseObj.setEndingHour(EndingHour);
        DseObj.setEndingMinutes(EndingMinutes);
        // Save changes
        em.merge(DseObj);  // Use merge to update an existing entity

        return DseObj;
    }
    @Override
    public long TotalExams()
    {
        
        return (long) em.createQuery("SELECT COUNT(s) FROM DateSheetsEntity s").getSingleResult();
        
    }
    public void persist(Object object) {
        em.persist(object);
    }

}
