/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.CoursesEntity;
import com.csworkshop.exammanagement.exceptions.CourseAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.CourseNotFoundException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseCodeException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseIdException;
import com.csworkshop.exammanagement.exceptions.InvalidCourseNameException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author syeda
 */
@Stateless
public class CoursesSession implements CoursesSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

//    @PersistenceContext(unitName = "exammanagmentPU")
//    private EntityManager em;

    @Override
    public CoursesEntity addCourse(String courseName, String courseCode)
            throws InvalidCourseNameException, InvalidCourseCodeException,CourseAlreadyExistsException {

        // Checks if the courseName is valid
        if (courseName == null || courseName.isEmpty() || courseName.length() < 2) {
            throw new InvalidCourseNameException("Course name cannot be null or empty or less than 2 characters.");
        }

        // Checks if the courseCode is valid
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        // Check if a course with the same name or code already exists
        CoursesEntity existingCourse = findCourseByNameandCode(courseName, courseCode);
        if (existingCourse != null) {
            throw new CourseAlreadyExistsException(" " + courseName + " Course Code:" + courseCode + " already exists.");
        }

        // Add new course
        CoursesEntity course = new CoursesEntity();
        course.setCourseName(courseName);
        course.setCourseCode(courseCode);
        persist(course);

        // Display course details
        System.out.println("Course Added Successfully:");
        System.out.println("Course Details:");
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Course Code: " + course.getCourseCode());

        return course;
    }

    @Override
    public CoursesEntity findCourseById(int courseId) throws CourseNotFoundException, InvalidCourseIdException {
        if (courseId <= 0) {
            throw new InvalidCourseIdException("Course ID must be greater than zero.");
        }
        // to find the course by ID
        CoursesEntity course = em.find(CoursesEntity.class, courseId);
        // Checks if the course is null and throw an exception if not found
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
        // Returned the found course
        return course;
    }

    @Override
    public CoursesEntity findCourseByCode(String courseCode) throws InvalidCourseCodeException, CourseNotFoundException {
        // Checks if courseCode is null or empty
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        Query query = em.createNamedQuery("CoursesEntity.findByCourseCode", CoursesEntity.class);
        query.setParameter("courseCode", courseCode);
        CoursesEntity course = null;
        try {
            course = (CoursesEntity) query.getSingleResult();
        } catch (NoResultException e) {
            throw new CourseNotFoundException("No course found with code: " + courseCode);
        }
        return course;
    }

    @Override
    public List<CoursesEntity> findCourseByName(String courseName) throws InvalidCourseNameException, CourseNotFoundException {
        // Check if courseName is null, empty, or too short
        if (courseName == null || courseName.isEmpty() || courseName.length() < 2) {
            throw new InvalidCourseNameException("Course name cannot be null, empty, or less than 2 characters.");
        }
        // Create a TypedQuery using the named query
        TypedQuery<CoursesEntity> query = em.createNamedQuery("CoursesEntity.findByCourseName", CoursesEntity.class);
        query.setParameter("courseName", courseName);

        // Get results as a list
        List<CoursesEntity> courses = query.getResultList();

        // Handle the result if no courses are found
        if (courses.isEmpty()) {
            throw new CourseNotFoundException("No courses found with name: " + courseName);
        }
        // Return the list of courses
        return courses;
    }

    @Override
    public CoursesEntity findCourseByNameandCode(String courseName, String courseCode)
            throws InvalidCourseNameException, InvalidCourseCodeException {

        // Validate courseName
        if (courseName == null || courseName.isEmpty() || courseName.length() < 2) {
            throw new InvalidCourseNameException("Course name cannot be null, empty, or less than 2 characters.");
        }

        // Validate courseCode
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        // Create a query to find the course
        TypedQuery<CoursesEntity> query = em.createQuery(
                "SELECT c FROM CoursesEntity c WHERE c.courseName = :courseName and c.courseCode = :courseCode",
                CoursesEntity.class
        );
        query.setParameter("courseName", courseName);
        query.setParameter("courseCode", courseCode);

        // Execute the query and get results
        List<CoursesEntity> results = query.getResultList();

        if (results.isEmpty()) {
            return null; // No course found
        }

        return results.get(0); // Return the first result if any
    }

    @Override
    public void removeCourseById(int courseId) throws InvalidCourseIdException, CourseNotFoundException {
        // Validate courseId
        if (courseId <= 0) {
            throw new InvalidCourseIdException("Course ID cannot be zero.");
        }
        // Attempt to find the course by ID
        CoursesEntity course = findCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
        // Remove the course from the database
        em.remove(course);
    }

    @Override
    public void removeCourseByCode(String courseCode) throws InvalidCourseCodeException, CourseNotFoundException {
        // Validate courseCode
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        // Attempt to find the course by code
        CoursesEntity course = findCourseByCode(courseCode);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with code: " + courseCode);
        }

        // Remove the course from the database
        em.remove(course);
    }

    @Override
    public void removeCourseByNameAndCode(String courseName, String courseCode)
            throws InvalidCourseNameException, InvalidCourseCodeException, CourseNotFoundException {

        // Validate courseName and courseCode
        if (courseName == null || courseName.isEmpty() || courseName.length() < 2) {
            throw new InvalidCourseNameException("Course name cannot be null, empty, or less than 2 characters.");
        }
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        try {
            // Attempt to find the course by name and code
            TypedQuery<CoursesEntity> query = em.createNamedQuery("CoursesEntity.findByCourseNameAndCode", CoursesEntity.class);
            query.setParameter("courseName", courseName);
            query.setParameter("courseCode", courseCode);
            List<CoursesEntity> results = query.getResultList();

            if (results.isEmpty()) {
                throw new CourseNotFoundException("No course found with name: " + courseName + " and code: " + courseCode);
            }

            // Remove the specific course
            CoursesEntity course = results.get(0);
            em.remove(course);

            // Optionally, log the removal
            System.out.println("Successfully removed course with name: " + courseName + " and code: " + courseCode);

        } catch (Exception e) {
            // Handle exceptions related to EJB or persistence
            System.err.println("An error occurred while removing the course: " + e.getMessage());
            throw e; // Re-throw to ensure the caller is informed of the exception
        }
    }

    @Override
    public CoursesEntity updateCourse(int courseId, String courseName, String courseCode)
            throws InvalidCourseIdException, InvalidCourseNameException, InvalidCourseCodeException, CourseNotFoundException {
        // Validate courseId
        if (courseId <= 0) {
            throw new InvalidCourseIdException("New Course ID must be greater than zero.");
        }

        // Validate courseName
        if (courseName == null || courseName.isEmpty() || courseName.length() < 2) {
            throw new InvalidCourseNameException("Course name cannot be null, empty, or less than 2 characters.");
        }

        // Validate courseCode
        if (courseCode == null || courseCode.isEmpty()) {
            throw new InvalidCourseCodeException("Course code cannot be null or empty.");
        }

        // Attempt to find the course by the current ID
        CoursesEntity course = em.find(CoursesEntity.class, courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        // Update course details
        //course.setCourseId(courseId);  // Set the new ID
        course.setCourseName(courseName);
        course.setCourseCode(courseCode);

        // Merge updated course entity
        em.merge(course);
        course.toString();
        // Returned updated course entity

        return course;
    }

    @Override
    public List<CoursesEntity> getAllCourses() throws CourseNotFoundException {
        //  a TypedQuery using the named query
        TypedQuery<CoursesEntity> query = em.createNamedQuery("CoursesEntity.findAll", CoursesEntity.class);

        // Execute the query and get the results
        List<CoursesEntity> courses = query.getResultList();

        //  if the list is empty and throw an exception if no courses are found
        if (courses == null || courses.isEmpty()) {
            throw new CourseNotFoundException("No Course Exists.");
        }
//    
        return courses;
    }

    public void persist(Object object) {
        em.persist(object);
    }   
}