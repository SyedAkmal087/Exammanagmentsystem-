
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.TeachersEntity;
import com.csworkshop.exammanagement.exceptions.InvalidTeacherEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidTeacherIdException;
import com.csworkshop.exammanagement.exceptions.InvalidTeacherNameException;
import com.csworkshop.exammanagement.exceptions.InvalidTeacherPhoneNoException;
import com.csworkshop.exammanagement.exceptions.NullTeacherEmailException;
import com.csworkshop.exammanagement.exceptions.NullTeacherNameException;
import com.csworkshop.exammanagement.exceptions.NullTeacherPasswordException;
import com.csworkshop.exammanagement.exceptions.NullTeacherPhoneNoException;
import com.csworkshop.exammanagement.exceptions.TeacherAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.TeacherNotFoundException;
import com.csworkshop.exammanagement.exceptions.TeacherNotUpdatedException;

import com.csworkshop.exammanagement.exceptions.WeakTeacherPasswordException;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author syeda
 */
@Stateless
public class TeachersSession implements TeachersSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

   

    @Override
    public TeachersEntity addTeacher(String name, String phone_num, String email, String password, boolean availability)
            throws NullTeacherNameException, InvalidTeacherNameException, NullTeacherPhoneNoException, InvalidTeacherPhoneNoException,
            NullTeacherEmailException, InvalidTeacherEmailException, NullTeacherPasswordException, WeakTeacherPasswordException,
            TeacherAlreadyExistsException {

        validateInputs(name, phone_num, email, password, availability);
TeachersEntity teacher = new TeachersEntity();
        try {
            if (doesTeacherWithEmailandPhoneNumberExist(email, phone_num)) {
                throw new TeacherAlreadyExistsException("Teacher already exists.");
            }
        } catch (TeacherNotFoundException ex) {
        teacher.setTeacherName(name);
        teacher.setTeacherPhoneNumber(phone_num);
        teacher.setTeacherEmail(email);
        teacher.setTeacherPassword(password);
        teacher.setAvailability(availability);
        persist(teacher);
        }
        return teacher;
    }

    private boolean doesTeacherWithEmailandPhoneNumberExist(String email, String phone_num) throws TeacherNotFoundException{
        TeachersEntity existingTeacher;
        try {
            existingTeacher = findTeacherByEmailandPhoneNumber(email, phone_num);
        } catch (TeacherNotFoundException ex) {
            throw new TeacherNotFoundException("no teacher found ");
        }
        return existingTeacher != null;
    }

    @Override
    public TeachersEntity findTeacherByEmailandPhoneNumber(String teacherEmail, String teacherPhoneNumber) throws TeacherNotFoundException {
        //  named query to find a teacher by email and phone number
        Query qry = em.createQuery("select t from TeachersEntity t where t.teacherEmail = :teacherEmail and t.teacherPhoneNumber = :teacherPhoneNumber");
        qry.setParameter("teacherEmail", teacherEmail);
        qry.setParameter("teacherPhoneNumber", teacherPhoneNumber);
        TeachersEntity teacher;
        try {
            teacher = (TeachersEntity) qry.getSingleResult();
        } catch (NoResultException e) {
            throw new TeacherNotFoundException("no teacher found ");
        }
        return teacher;
    }

    private void validateInputs(String name, String phone_num, String email, String password, boolean availability)
            throws NullTeacherNameException, InvalidTeacherNameException, NullTeacherEmailException, InvalidTeacherEmailException,
            NullTeacherPhoneNoException, InvalidTeacherPhoneNoException, NullTeacherPasswordException, WeakTeacherPasswordException {
        if (name == null || name.isEmpty()) {
            throw new NullTeacherNameException("Name cannot be null or empty.");
        }
        if (name.length() < 3) {
            throw new InvalidTeacherNameException("Name must be 3 characters long.");
        }
        if (phone_num == null || phone_num.isEmpty()) {
            throw new NullTeacherPhoneNoException("Phone number cannot be null)");
        }
        if (!phone_num.matches("\\d{11}")) {
            throw new InvalidTeacherPhoneNoException("Invalid Phone Number(must be 11-digit number)");
        }
        //  enforce the @uog.edu.pk domain
        if (email == null || email.isEmpty()) {
            throw new NullTeacherEmailException("E-mail cannot be null or empty.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@uog\\.edu\\.pk$")) {
            throw new InvalidTeacherEmailException("Invalid Email(Email must contain uog.edu.pk domain)");
        }
        if (password == null || password.isEmpty()) {
            throw new NullTeacherPasswordException("Password must be 8 characters long.");
        }
        if (password.length() < 8) {
            throw new WeakTeacherPasswordException("Password must be 8 characters long.");
        }

    }

    @Override
    public TeachersEntity findTeacherbyId(int id)
            throws InvalidTeacherIdException, TeacherNotFoundException {
        if (id <= 0) {
            throw new InvalidTeacherIdException("ID cannot be zero.");
        }
        TeachersEntity teacher = em.find(TeachersEntity.class, id);
        if (teacher == null) {
            throw new TeacherNotFoundException("No teacher found with id: " + id);
        }
        return teacher;
    }

    @Override
    public List<TeachersEntity> findTeacherByName(String name)
            throws InvalidTeacherNameException, NullTeacherNameException, TeacherNotFoundException {
        // Check if name is null, empty
        if (name == null || name.isEmpty()) {
            throw new NullTeacherNameException("Name cannot be null, empty.");
        }
        if (name.length() < 3) {
            throw new InvalidTeacherNameException("Name must be 3 characters long.");
        }

        // Create a Query using the named query
        Query query = em.createNamedQuery("TeachersEntity.findByTeacherName");
        query.setParameter("teacherName", name);

        // Get results as a list
        List<TeachersEntity> teachers = query.getResultList();

        // Handle the result if no teachers are found
        if (teachers.isEmpty()) {
            throw new TeacherNotFoundException("No teacher found with name: " + name);
        }

        // Return the list of teachers
        return teachers;
    }

    @Override
    public void removeTeacherById(int teacherId)
            throws InvalidTeacherIdException, TeacherNotFoundException {
        // Validate teacherId
        if (teacherId <= 0) {
            throw new InvalidTeacherIdException("ID cannot be zero.");
        }

        TeachersEntity teacher = findTeacherbyId(teacherId);
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }

        // Remove the teacher from the database
        em.remove(teacher);
    }

    @Override
    public void updateTeacher(int teacherId, String newTeacherName, String newPhone_no, String newEmail, String newPassword, boolean avail)
            throws InvalidTeacherIdException, NullTeacherNameException, InvalidTeacherNameException, NullTeacherPhoneNoException, InvalidTeacherPhoneNoException,
            NullTeacherEmailException, InvalidTeacherEmailException, NullTeacherPasswordException, WeakTeacherPasswordException, TeacherNotFoundException,
            TeacherNotUpdatedException {
        // Validate the teacher ID
        if (teacherId <= 0) {
            throw new InvalidTeacherIdException("ID cannot be zero.");
        }
        validateInputs(newTeacherName, newPhone_no, newEmail, newPassword, avail);
        TeachersEntity teacher = findTeacherbyId(teacherId);
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }
        boolean isUpdated = false;
        if (newTeacherName != null && !newTeacherName.isEmpty()) {
            teacher.setTeacherName(newTeacherName);
            isUpdated = true;
        }
        if (newPhone_no != null && !newPhone_no.isEmpty()) {
            teacher.setTeacherPhoneNumber(newPhone_no);
            isUpdated = true;
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            teacher.setTeacherEmail(newEmail);
            isUpdated = true;
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            teacher.setTeacherPassword(newPassword);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new TeacherNotUpdatedException("Update Failed!");
        }
        em.merge(teacher);
    }

    @Override
    public List<TeachersEntity> getAllTeachers()
            throws TeacherNotFoundException {
        // Create a TypedQuery to retrieve all teachers
        TypedQuery<TeachersEntity> query = em.createQuery("SELECT t FROM TeachersEntity t", TeachersEntity.class);

        // Get the result list
        List<TeachersEntity> teachers = query.getResultList();

        if (teachers == null || teachers.isEmpty()) {
            throw new TeacherNotFoundException("No teachers found in the Record.");
        }

        // Return the list of teachers
        return teachers;
    }
     private boolean isValidPassword(String password) {    
    return password != null && password.length() >= 8; // Example rule  
}  
 @Override
        public TeachersEntity UpdateTeacherPassword(int teacherId, String oldPassword,String newPassword) 
                throws TeacherNotFoundException,InvalidTeacherOldPasswordException,WeakTeacherPasswordException,InvalidTeacherIdException{
            TeachersEntity teacher=findTeacherbyId(teacherId);
        if(teacher!=null){
            
         if (!teacher.getTeacherPassword().equals(oldPassword)) {  
            throw new InvalidTeacherOldPasswordException("Old password is incorrect for Student ID: " + teacherId);  
        } 
         if (!isValidPassword(newPassword)) {  
            throw new WeakTeacherPasswordException("New password does not meet the strength requirements.");  
        }
            teacher.setTeacherPassword(newPassword);
            em.merge(teacher);
        }
        else{
            throw new TeacherNotFoundException("No Student Found With ID: "+teacherId);
            
        }
    return teacher;
    }
  

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
}