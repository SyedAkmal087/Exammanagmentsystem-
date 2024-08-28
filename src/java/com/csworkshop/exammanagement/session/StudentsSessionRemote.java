/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;


import com.csworkshop.exammanagement.exceptions.StudentRecordAlreadyExistException;
import com.csworkshop.exammanagement.exceptions.NullStudentRollNoException;
import com.csworkshop.exammanagement.exceptions.NullStudentBatchNoException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentOldPasswordException;
import com.csworkshop.exammanagement.exceptions.WeakStudentPasswordException;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentNameLengthException;
import com.csworkshop.exammanagement.exceptions.NullStudentNameException;
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
        public StudentsEntity findStudentById(int studentId) throws StudentNotFoundException,InvalidStudentIdException;
        
        public List<StudentsEntity> findStudentByName(String studentName) throws StudentNotFoundException,NullStudentNameException,InvalidStudentNameLengthException;
          public List<StudentsEntity> GetAllStudentsData();
              public StudentsEntity DeleteStudentById(int studentId) throws StudentNotFoundException,InvalidStudentIdException;
              
        public StudentsEntity UpdateStudentPassword(int studentId,String oldPassword,String newPassword)
                throws StudentNotFoundException,InvalidStudentOldPasswordException,WeakStudentPasswordException,InvalidStudentIdException;
       
        public StudentsEntity addStudent(String studentName,String batchNo,String RollNo,String email,String Password,int sectionId)
        throws  NullStudentNameException,NullStudentBatchNoException,NullStudentRollNoException,StudentRecordAlreadyExistException,InvalidStudentNameLengthException,SectionNotFoundException,InvalidEmailException,WeakStudentPasswordException,InvalidSectionIdException;
     
        public StudentsEntity UpdateStudentRecord(int studentId, String studentName,String batchNo,String RollNo,String email,String password,int sectionId) 
                throws NullStudentNameException,StudentRecordAlreadyExistException,NullStudentBatchNoException,NullStudentRollNoException,WeakStudentPasswordException,StudentNotFoundException,InvalidStudentIdException,InvalidStudentNameLengthException,SectionNotFoundException,InvalidEmailException,InvalidSectionIdException;
         
        public StudentsEntity findStudentRecordByRollNumber(String rollNo)
        throws StudentNotFoundException;
}
