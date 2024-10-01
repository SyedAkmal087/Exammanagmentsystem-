/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;


import com.csworkshop.exammanagement.exceptions.InvalidOldPasswordException;
import com.csworkshop.exammanagement.exceptions.StudentRecordAlreadyExistException;
import com.csworkshop.exammanagement.exceptions.NullStudentRollNoException;
import com.csworkshop.exammanagement.exceptions.NullStudentBatchNoException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.exceptions.WeakStudentPasswordException;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentNameLengthException;
import com.csworkshop.exammanagement.exceptions.NullStudentEmailException;
import com.csworkshop.exammanagement.exceptions.NullStudentNameException;
import com.csworkshop.exammanagement.exceptions.NullStudentPasswordException;
import com.csworkshop.exammanagement.exceptions.SectionNotFoundException;
import com.csworkshop.exammanagement.exceptions.StudentNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface StudentsSessionRemote {

    public List<StudentsEntity> getAllStudentsData() throws StudentNotFoundException;

    public StudentsEntity deleteStudentById(int studentId) throws StudentNotFoundException, InvalidStudentIdException;

    public StudentsEntity updateStudentPassword(int studentId, String oldPassword, String newPassword) throws StudentNotFoundException, InvalidOldPasswordException, WeakStudentPasswordException, InvalidStudentIdException;

    public StudentsEntity updateStudentRecord(int studentId, String studentName, String batchNo, String rollNo, String email, String password, int sectionId) throws StudentRecordAlreadyExistException,NullStudentNameException, NullStudentBatchNoException, NullStudentRollNoException, WeakStudentPasswordException, StudentNotFoundException, InvalidStudentIdException, InvalidStudentNameLengthException, SectionNotFoundException, NullStudentRollNoException, InvalidSectionIdException, InvalidStudentEmailException;

    public List<StudentsEntity> findStudentByName(String studentName) throws StudentNotFoundException, NullStudentNameException, InvalidStudentNameLengthException;

    public List<StudentsEntity> findStudentsByBatchNumber(String batchNumber) throws StudentNotFoundException;

    public List<StudentsEntity> findStudentsBySectionAndBatchNo(Integer sectionId, String batchNumber) throws StudentNotFoundException;

    public StudentsEntity findStudentRecordByRollNumber(String rollNo) throws StudentNotFoundException;

    public List<StudentsEntity> getStudentsBySectionId(int sectionId) throws SectionNotFoundException, InvalidSectionIdException, StudentNotFoundException;

    public StudentsEntity findStudentById(int studentId) throws StudentNotFoundException, InvalidStudentIdException;


    public StudentsEntity addStudent(String studentName, String batchNo, String rollNo, String email, String password, int sectionId) throws NullStudentNameException, 
            InvalidStudentNameLengthException, NullStudentRollNoException, NullStudentEmailException, InvalidStudentEmailException,
            NullStudentBatchNoException, NullStudentPasswordException, WeakStudentPasswordException, InvalidSectionIdException, SectionNotFoundException,
            StudentRecordAlreadyExistException,StudentNotFoundException;
    public StudentsEntity findStudentRecordByEmail(String email) throws StudentNotFoundException ;

    public StudentsEntity findStudentByEmailAndPassword(String email, String password) throws StudentNotFoundException, InvalidStudentEmailException, NullStudentEmailException, NullStudentPasswordException;

    public long TotalStudents();

       
}
