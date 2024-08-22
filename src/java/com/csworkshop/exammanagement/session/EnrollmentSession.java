/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.CoursesEntity;
import com.csworkshop.exammanagement.entity.EnrollmentsEntity;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.exceptions.CourseNotFoundException;
import com.csworkshop.exammanagement.exceptions.EnrollmentAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.EnrollmentNotFoundException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseIdException;
import com.csworkshop.exammanagement.exceptions.InvalidEnrollmentIdException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.exceptions.StudentNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author syeda
 */
@Stateless
public class EnrollmentSession implements EnrollmentSessionRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;
    @EJB
    private CoursesSessionRemote coursesManager;
    @EJB
    private StudentsSessionRemote studentManager;

    @Override
    public EnrollmentsEntity addEnrollment(int studentId, int courseId) throws InvalidStudentIdException, InvalidCourseIdException, EnrollmentNotFoundException, EnrollmentAlreadyExistsException, CourseNotFoundException, StudentNotFoundException {
        EnrollmentsEntity enrollment = new EnrollmentsEntity();
        if (courseId <= 0) {
            throw new InvalidCourseIdException("Invalid Course Id");
        } else if (studentId <= 0) {
            throw new InvalidStudentIdException("Invalid Student Id");
        } else {
            EnrollmentsEntity existingEnrollment;
            existingEnrollment = findEnrollmentByStudentAndCourse(studentId, courseId);
            if (existingEnrollment != null) {
                throw new EnrollmentAlreadyExistsException("Enrollment already exists for student ID: " + studentId + " and course ID: " + courseId);
            }
            StudentsEntity student = studentManager.findStudentById(studentId);
            enrollment.setStudentId(student);
            CoursesEntity course = coursesManager.findCourseById(courseId);
            enrollment.setCourseId(course);
            persist(enrollment);
        }

        return enrollment;

    }

    @Override
    public EnrollmentsEntity findEnrollmentById(int enrollmentId) throws EnrollmentNotFoundException, InvalidEnrollmentIdException {
        if (enrollmentId <= 0) {
            throw new InvalidEnrollmentIdException("Invalid enrollment ID: " + enrollmentId);
        }
        EnrollmentsEntity enrollment = em.find(EnrollmentsEntity.class, enrollmentId);
        if (enrollment == null) {
            throw new EnrollmentNotFoundException("Enrollment not found with ID: " + enrollmentId);
        }
        return enrollment;
    }

    @Override
    public EnrollmentsEntity findEnrollmentByStudentAndCourse(int studentId, int courseId) throws EnrollmentNotFoundException, CourseNotFoundException, StudentNotFoundException, InvalidStudentIdException, InvalidCourseIdException {
        EnrollmentsEntity enrollment;
        StudentsEntity student = studentManager.findStudentById(studentId);
        CoursesEntity course = coursesManager.findCourseById(courseId);
        Query qry = em.createQuery("Select e from EnrollmentsEntity e where e.studentId = :studentId and e.courseId = :courseId");
        qry.setParameter("studentId", student);
        qry.setParameter("courseId", course);
        try {
            enrollment = (EnrollmentsEntity) qry.getSingleResult();
        } catch (NoResultException e) {
            throw new EnrollmentNotFoundException("No enrollment found for student ID: " + studentId + " and course ID: " + courseId);
        }
        return enrollment;
    }

    @Override
    public void deleteEnrollment(int enrollmentId) throws EnrollmentNotFoundException, InvalidEnrollmentIdException {
        EnrollmentsEntity enrollment = findEnrollmentById(enrollmentId);
        em.remove(enrollment);
    }

    @Override
    public EnrollmentsEntity updateEnrollment(int enrollmentId, int studentId, int courseId) throws EnrollmentNotFoundException, InvalidStudentIdException, EnrollmentAlreadyExistsException, InvalidCourseIdException, InvalidEnrollmentIdException, CourseNotFoundException, StudentNotFoundException {
        {
            EnrollmentsEntity enrollment = findEnrollmentById(enrollmentId);

            if (studentId <= 0) {
                throw new InvalidStudentIdException("Invalid student ID");
            } else if (courseId <= 0) {
                throw new InvalidCourseIdException(" Invalid course ID");
            } else {
                EnrollmentsEntity existingEnrollmenet;
                try {
                    existingEnrollmenet = findEnrollmentByStudentAndCourse(studentId, courseId);
                    throw new EnrollmentAlreadyExistsException("Enrollment already exists for student ID: " + studentId + " and course ID: " + courseId);
                } catch (EnrollmentNotFoundException e) {
                    StudentsEntity student = studentManager.findStudentById(studentId);
                    enrollment.setStudentId(student);
                    CoursesEntity course = coursesManager.findCourseById(courseId);
                    enrollment.setCourseId(course);
                    em.merge(enrollment);
                }
            }
            return enrollment;
        }

    }

    @Override
    public List<EnrollmentsEntity> findAllEnrollments() throws EnrollmentNotFoundException {
        Query query = em.createQuery("select e from EnrollmentsEntity e");
        List<EnrollmentsEntity> enrollments = query.getResultList();
        if (enrollments == null || enrollments.isEmpty()) {
            throw new EnrollmentNotFoundException("No enrollments found in the database.");
        }
        return enrollments;
    }

    @Override
    public List<EnrollmentsEntity> findEnrollmentsByCourseId(int courseId) throws InvalidCourseIdException, CourseNotFoundException, EnrollmentNotFoundException {
        if (courseId <= 0) {
            throw new InvalidCourseIdException("Invalid Course Id");
        }

        CoursesEntity course = coursesManager.findCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        Query qry = em.createQuery("select e from EnrollmentsEntity e where e.courseId = :courseId");
        qry.setParameter("courseId", course);
        List<EnrollmentsEntity> enrollments = qry.getResultList();

        if (enrollments == null || enrollments.isEmpty()) {
            throw new EnrollmentNotFoundException("No enrollments found for course ID: " + courseId);
        }

        return enrollments;
    }

    @Override
    public List<EnrollmentsEntity> findEnrollmentsByStudentId(int studentId) throws InvalidStudentIdException, StudentNotFoundException, EnrollmentNotFoundException {
        if (studentId <= 0) {
            throw new InvalidStudentIdException("Invalid Student Id");
        }

        StudentsEntity student = studentManager.findStudentById(studentId);
        if (student == null) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        Query qry = em.createQuery("Select e from EnrollmentsEntity e where e.studentId = :studentId");
        qry.setParameter("studentId", student);
        List<EnrollmentsEntity> enrollments = qry.getResultList();

        if (enrollments == null || enrollments.isEmpty()) {
            throw new EnrollmentNotFoundException("No enrollments found for student ID: " + studentId);
        }

        return enrollments;
    }

    @Override
    public List<EnrollmentsEntity> findEnrollmentsByStudentName(String studentName) throws StudentNotFoundException, EnrollmentNotFoundException {
        if (studentName == null || studentName.isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EnrollmentsEntity> cq = cb.createQuery(EnrollmentsEntity.class);
        Root<EnrollmentsEntity> enrollmentRoot = cq.from(EnrollmentsEntity.class);

        // Join with StudentsEntity
        Join<EnrollmentsEntity, StudentsEntity> studentJoin = enrollmentRoot.join("studentId");

        // Create Predicate for the studentName
        Predicate studentNamePredicate = cb.equal(studentJoin.get("studentName"), studentName);

        // Add the predicate to the CriteriaQuery
        cq.select(enrollmentRoot).where(studentNamePredicate);

        TypedQuery<EnrollmentsEntity> query = em.createQuery(cq);
        List<EnrollmentsEntity> results = query.getResultList();

        if (results == null || results.isEmpty()) {
            throw new EnrollmentNotFoundException("No enrollments found for student name: " + studentName);
        }

        return results;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
