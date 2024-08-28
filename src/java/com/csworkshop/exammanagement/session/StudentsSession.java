/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.exceptions.InvalidOldPasswordException;
import com.csworkshop.exammanagement.exceptions.StudentRecordAlreadyExistException;
import com.csworkshop.exammanagement.exceptions.NullStudentRollNoException;
import com.csworkshop.exammanagement.exceptions.NullStudentBatchNoException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.entity.SectionsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidStudentEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentNameLengthException;
import com.csworkshop.exammanagement.exceptions.NullStudentNameException;
import com.csworkshop.exammanagement.exceptions.WeakStudentPasswordException;
import com.csworkshop.exammanagement.exceptions.StudentNotFoundException;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.NullStudentEmailException;
import com.csworkshop.exammanagement.exceptions.NullStudentPasswordException;
import com.csworkshop.exammanagement.exceptions.SectionNotFoundException;

import java.util.List;

import javax.ejb.EJB;
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

public class StudentsSession implements StudentsSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

    @EJB
    SectionsSessionRemote sectionManager;

    @Override
    public StudentsEntity addStudent(String studentName, String batchNo, String rollNo, String email, String password, int sectionId)
            throws NullStudentNameException, InvalidStudentNameLengthException, NullStudentRollNoException, NullStudentEmailException,
            InvalidStudentEmailException, NullStudentBatchNoException, NullStudentPasswordException,
            WeakStudentPasswordException,  InvalidSectionIdException,SectionNotFoundException, StudentRecordAlreadyExistException {
        StudentsEntity student = new StudentsEntity();
        if (studentName == null || studentName.isEmpty()) {
            throw new NullStudentNameException("Please Enter Name");
        } else if (studentName.length() < 3) {
            throw new InvalidStudentNameLengthException("Invalid Student Name Length");
        } else if (rollNo == null || rollNo.isEmpty()) {
            throw new NullStudentRollNoException("Please Enter Roll Number");
        } else if (email == null || email.isEmpty()) {
            throw new NullStudentEmailException("Please Enter Email");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@uog\\.edu\\.pk$")) {
            throw new InvalidStudentEmailException("Invalid Email format");
        } else if (batchNo == null || batchNo.isEmpty()) {
            throw new NullStudentBatchNoException("Please Enter Batch Number");
        } else if (password == null || password.isEmpty()) {
            throw new NullStudentPasswordException("Please Enter Password");
        } else if (!isValidPassword(password)) {
            throw new WeakStudentPasswordException("Password does not meet the strength requirements");
        }
        StudentsEntity existingStudent = null;
        try {
            existingStudent = findStudentRecordByRollNumber(rollNo);
            throw new StudentRecordAlreadyExistException("Student Already Exist With The Roll Number " + rollNo);
        } catch (StudentNotFoundException ex) {
            student.setStudentName(studentName);
            student.setBatchNo(batchNo);
            student.setRollNo(rollNo);
            student.setEmail(email);
            student.setPassword(password);
            SectionsEntity section = sectionManager.findSectionById(sectionId);
            student.setSectionId(section);
            persist(student);
        }
        return student;
    }

    @Override
    public StudentsEntity findStudentById(int studentId) throws StudentNotFoundException, InvalidStudentIdException {
        if (studentId <= 0) {
            throw new InvalidStudentIdException("Invalid Student ID: ");
        }
        StudentsEntity student = em.find(StudentsEntity.class, studentId);
        if (student == null) {
            throw new StudentNotFoundException("No Student exist with id " + studentId);
        }
        return student;
    }

    @Override
    public List<StudentsEntity> getStudentsBySectionId(int sectionId) throws SectionNotFoundException, InvalidSectionIdException, StudentNotFoundException {
        sectionManager.findSectionById(sectionId);
        TypedQuery<StudentsEntity> query = em.createQuery("SELECT s FROM StudentsEntity s WHERE s.sectionId.sectionId = :sectionId", StudentsEntity.class);
        query.setParameter("sectionId", sectionId);
        List<StudentsEntity> students = query.getResultList();
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("No students found for section ID: " + sectionId);
        }
        return students;
    }

    @Override
    public StudentsEntity findStudentRecordByRollNumber(String rollNo)
            throws StudentNotFoundException {
        Query qry = em.createQuery("select s from StudentsEntity s where s.rollNo=:rollNo");
        qry.setParameter("rollNo", rollNo);
        StudentsEntity student;
        try {
            student = (StudentsEntity) qry.getSingleResult();
        } catch (NoResultException e) {

            throw new StudentNotFoundException("No Student found with Roll Number: " + rollNo);
        }

        return student;
    }

    @Override
    public List<StudentsEntity> findStudentsBySectionAndBatchNo(Integer sectionId, String batchNumber) throws StudentNotFoundException {
        if (sectionId == null) {
            throw new IllegalArgumentException("Please Enter Section Id.");
        }
        if (batchNumber == null || batchNumber.isEmpty()) {
            throw new IllegalArgumentException("Please Enter Batch Number.");
        }
        TypedQuery<StudentsEntity> query = em.createQuery(
                "SELECT s FROM StudentsEntity s WHERE s.sectionId.sectionId = :sectionId AND s.batchNo = :batchNo",
                StudentsEntity.class
        );
        query.setParameter("sectionId", sectionId);
        query.setParameter("batchNo", batchNumber);
        List<StudentsEntity> students = query.getResultList();
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("No students found for section ID " + sectionId + " and batch " + batchNumber);
        }
        return students;
    }

    @Override
    public List<StudentsEntity> findStudentsByBatchNumber(String batchNumber) throws StudentNotFoundException {
        if (batchNumber == null || batchNumber.isEmpty()) {
            throw new IllegalArgumentException("Please Enter Batch Number.");
        }
        TypedQuery<StudentsEntity> query = em.createNamedQuery("StudentsEntity.findByBatchNo", StudentsEntity.class);
        query.setParameter("batchNo", batchNumber);
        List<StudentsEntity> students = query.getResultList();

        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("No students found for batch " + batchNumber);
        }
        return students;
    }

    @Override
    public List<StudentsEntity> findStudentByName(String studentName) throws StudentNotFoundException, NullStudentNameException, InvalidStudentNameLengthException {
        if (studentName == null || studentName.isEmpty()) {
            throw new NullStudentNameException("Please Enter Student Name");
        }
        if (studentName.length() < 3) {
            throw new InvalidStudentNameLengthException("Invalid Name Length");
        }
        Query qry = em.createQuery("select s from StudentsEntity s where s.studentName=:studentName");
        qry.setParameter("studentName", studentName);

        List<StudentsEntity> students;
        students = qry.getResultList();

        if (students.isEmpty()) {
            throw new StudentNotFoundException("No Student found with name :" + studentName);
        }

        return students;
    }

    @Override
    public StudentsEntity updateStudentRecord(int studentId, String studentName, String batchNo, String rollNo, String email, String password, int sectionId)
            throws NullStudentNameException, NullStudentBatchNoException, NullStudentRollNoException, WeakStudentPasswordException, StudentNotFoundException, InvalidStudentIdException, InvalidStudentNameLengthException, SectionNotFoundException, NullStudentRollNoException, InvalidSectionIdException, InvalidStudentEmailException {

        StudentsEntity student = findStudentById(studentId);
        if (student != null) {
            if (studentName == null || studentName.isEmpty()) {
                throw new NullStudentNameException("Please Enter Student Name");
            } else if (studentName.length() < 3) {
                throw new InvalidStudentNameLengthException("Invalid Name Length");
            } else if (batchNo == null || batchNo.isEmpty()) {
                throw new NullStudentBatchNoException("Please Enter Batch Number");
            } else if (rollNo == null || rollNo.isEmpty()) {
                throw new NullStudentRollNoException("Please Enter Roll number");
            }
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@uog\\.edu\\.pk$") || email.isEmpty()) {
                throw new InvalidStudentEmailException("Invalid email format");
            }
            if (!isValidPassword(password) || password.isEmpty()) {
                throw new WeakStudentPasswordException("Password does not meet the strength requirements");
            }

            student.setStudentName(studentName);
            student.setBatchNo(batchNo);
            student.setEmail(email);
            student.setRollNo(rollNo);
            student.setPassword(password);

            SectionsEntity section = sectionManager.findSectionById(sectionId);
            student.setSectionId(section);

            em.merge(student);
        } else {
            throw new StudentNotFoundException("No Student Found with ID: " + studentId);
        }
        return student;
    }

    @Override
    public StudentsEntity updateStudentPassword(int studentId, String oldPassword, String newPassword)
            throws StudentNotFoundException, InvalidOldPasswordException, WeakStudentPasswordException, InvalidStudentIdException {
        StudentsEntity student = findStudentById(studentId);
        if (student != null) {

            if (!student.getPassword().equals(oldPassword)) {
                throw new InvalidOldPasswordException("Incorrect Password ");
            }
            if (!isValidPassword(newPassword)) {
                throw new WeakStudentPasswordException("New password does not meet the strength requirements.");
            }
            student.setPassword(newPassword);
            em.merge(student);
        } else {
            throw new StudentNotFoundException("No Student Found with ID: " + studentId);

        }
        return student;
    }

    @Override
    public StudentsEntity deleteStudentById(int studentId) throws StudentNotFoundException, InvalidStudentIdException {
        StudentsEntity student = findStudentById(studentId);
        em.remove(student);
        return student;

    }

    @Override
    public List<StudentsEntity> getAllStudentsData() throws StudentNotFoundException {
        List<StudentsEntity> allStudents = null;
        Query qry = em.createQuery("SELECT s FROM StudentsEntity s");
        allStudents = qry.getResultList();
        if (allStudents == null || allStudents.isEmpty()) {
            throw new StudentNotFoundException("No students found in the Record.");
        }

        return allStudents;
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}