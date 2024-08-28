/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;


import com.csworkshop.exammanagement.entity.DateSheetsEntity;
import com.csworkshop.exammanagement.exceptions.ExceptionsForDateSheets.CourseCodeNotFoundExceptions;
import com.csworkshop.exammanagement.exceptions.InvalidCourseCodeException;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface DateSheetsSessionRemote {

    public DateSheetsEntity GetExamByCourseCode(String CourseCode) throws CourseCodeNotFoundExceptions, InvalidCourseCodeException;
    
}
