/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.EnrollmentsEntity;
import com.csworkshop.exammanagement.exceptions.CourseNotFoundException;
import com.csworkshop.exammanagement.exceptions.EnrollmentAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.EnrollmentNotFoundException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseIdException;
import com.csworkshop.exammanagement.exceptions.InvalidEnrollmentIdException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.exceptions.StudentNotFoundException;
import java.util.List;
import javax.ejb.Remote;



/**
 *
 * @author syeda
 */
@Remote
public interface EnrollmentSessionRemote {

    public EnrollmentsEntity addEnrollment(int studentId, int courseId) throws InvalidStudentIdException, InvalidCourseIdException, EnrollmentNotFoundException, EnrollmentAlreadyExistsException, CourseNotFoundException, StudentNotFoundException;

    public EnrollmentsEntity findEnrollmentById(int enrollmentId) throws EnrollmentNotFoundException, InvalidEnrollmentIdException;

    public EnrollmentsEntity findEnrollmentByStudentAndCourse(int studentId, int courseId) throws EnrollmentNotFoundException, CourseNotFoundException, StudentNotFoundException, InvalidStudentIdException, InvalidCourseIdException;

    public void deleteEnrollment(int enrollmentId) throws EnrollmentNotFoundException, InvalidEnrollmentIdException;

    public EnrollmentsEntity updateEnrollment(int enrollmentId, int studentId, int courseId) throws EnrollmentNotFoundException, InvalidStudentIdException, EnrollmentAlreadyExistsException, InvalidCourseIdException, InvalidEnrollmentIdException, CourseNotFoundException, StudentNotFoundException;

    public List<EnrollmentsEntity> findAllEnrollments() throws EnrollmentNotFoundException;

    public List<EnrollmentsEntity> findEnrollmentsByCourseId(int courseId) throws InvalidCourseIdException, CourseNotFoundException, EnrollmentNotFoundException;

    public List<EnrollmentsEntity> findEnrollmentsByStudentId(int studentId) throws InvalidStudentIdException, StudentNotFoundException, EnrollmentNotFoundException;

    public List<EnrollmentsEntity> findEnrollmentsByStudentName(String studentName) throws StudentNotFoundException, EnrollmentNotFoundException;
    
}
