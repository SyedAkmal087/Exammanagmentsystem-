/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.CoursesEntity;
import com.csworkshop.exammanagement.exceptions.CourseAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.CourseNotFoundException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseCodeException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseIdException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseNameException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface CoursesSessionRemote {

    public CoursesEntity addCourse(String courseName, String courseCode) throws InvalidCourseNameException, InvalidCourseCodeException, CourseAlreadyExistsException;

    public CoursesEntity findCourseById(int courseId) throws CourseNotFoundException, InvalidCourseIdException;

    public CoursesEntity findCourseByCode(String courseCode) throws InvalidCourseCodeException, CourseNotFoundException;

    List<CoursesEntity> findCourseByName(String courseName) throws InvalidCourseNameException, CourseNotFoundException;

    public CoursesEntity findCourseByNameandCode(String courseName, String courseCode) throws InvalidCourseNameException, InvalidCourseCodeException;

    public CoursesEntity updateCourse(int courseId, String courseName, String courseCode) throws
            InvalidCourseIdException, InvalidCourseNameException, InvalidCourseCodeException, CourseNotFoundException;

    public void removeCourseById(int courseId) throws InvalidCourseIdException, CourseNotFoundException;

    public void removeCourseByCode(String courseCode) throws InvalidCourseCodeException, CourseNotFoundException;

    public void removeCourseByNameAndCode(String courseName, String courseCode) throws InvalidCourseNameException, InvalidCourseCodeException, CourseNotFoundException;

    public List<CoursesEntity> getAllCourses() throws CourseNotFoundException;

}
