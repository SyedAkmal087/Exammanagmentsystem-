/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
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
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface DateSheetsSessionRemote {

    public DateSheetsEntity addDateSheet(Date ExamDate, String CourseCode, String CourseDesc, int StartingHour, int StartingMinutes, int EndingHour, int EndingMinutes) throws ExamDateNullException, CourseDescNullException, StartingtimeNullException, EndingTimeNullException, StartingTimeInvalidException, EndingTimeInvalidExcption, InvalidCourseCodeException, CourseNotFoundException, AlreadyExistException;

    public List<DateSheetsEntity> GetExamByCourseCode(CoursesEntity Coursecode)
            throws CourseCodeNotFoundExceptions, InvalidCourseCodeException;

    public DateSheetsEntity getExamById(int id) throws ExamIdNullException, ExamIdNotFoundException;

    public List<DateSheetsEntity> getExamByCourse(String Code) throws CourseCodeNotFoundExceptions, InvalidCourseCodeException;

    public List<DateSheetsEntity> getExamByStartingTime(int StartHour, int startMinutes) throws StartingtimeNullException, StartingTimeInvalidException, ExamNotFoundException;

    public List<DateSheetsEntity> getExamByEndingTime(int EndHour, int EndMinutes) throws EndingTimeNullException, EndingTimeInvalidExcption, ExamNotFoundException;

    public void RemoveExamFromDateSheetById(int id) throws ExamIdNullException, ExamIdNotFoundException;

    public List<DateSheetsEntity> GetAllExamsData() throws EmptyListException;

    public DateSheetsEntity updateDateSheet(int dateSheetId, Date ExamDate, String CourseCode, String CourseDesc, int StartingHour, int StartingMinutes, int EndingHour, int EndingMinutes) throws ExamDateNullException, CourseDescNullException, StartingtimeNullException, EndingTimeNullException, StartingTimeInvalidException, EndingTimeInvalidExcption, InvalidCourseCodeException, CourseNotFoundException, EmptyListException;

}
