/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.exceptions.StudentRecordAlreadyExistException;
import com.csworkshop.exammanagement.exceptions.NullStudentRollNoException;
import com.csworkshop.exammanagement.exceptions.NullStudentBatchNoException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentIdException;
import com.csworkshop.exammanagement.entity.SectionsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentNameLengthException;
import com.csworkshop.exammanagement.exceptions.NullStudentNameException;
import com.csworkshop.exammanagement.exceptions.InvalidStudentOldPasswordException;
import com.csworkshop.exammanagement.exceptions.WeakStudentPasswordException;
import com.csworkshop.exammanagement.exceptions.StudentNotFoundException;
import com.csworkshop.exammanagement.entity.StudentsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.SectionNotFoundException;



import java.util.List;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;


import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


import javax.persistence.Query;



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
    public StudentsEntity findStudentById(int studentId) throws StudentNotFoundException,InvalidStudentIdException{
        if (studentId <= 0) {
            throw new InvalidStudentIdException("Invalid Student ID: " + studentId);
        }
        StudentsEntity student= em.find(StudentsEntity.class, studentId);
        if(student==null ){
            throw new StudentNotFoundException("No Student exist with id "+studentId);
        }
        return student;
    }
@Override
    public List<StudentsEntity> findStudentByName(String studentName) throws StudentNotFoundException,NullStudentNameException,InvalidStudentNameLengthException{
        if(studentName==null ||studentName.isEmpty()){
            throw new NullStudentNameException("Student name can not be Null or empty");
        }
        if( studentName.length()<3){
            throw new InvalidStudentNameLengthException("student name can not shorter than three characters");
        }
        Query qry=em.createQuery("select s from StudentsEntity s where s.studentName=:studentName");
        qry.setParameter("studentName", studentName);

         List<StudentsEntity> students;  
            students = qry.getResultList(); 
            
            if (students.isEmpty() ){
                throw new StudentNotFoundException("No Student found for student name :"+studentName);
            }
          
        return students;
    }
    @Override
    public List<StudentsEntity> GetAllStudentsData() {
        List<StudentsEntity> allStudents;
        Query qry=em.createQuery("SELECT s FROM StudentsEntity s");
        allStudents=qry.getResultList();
       
        return allStudents;
    }
 
    private boolean isValidPassword(String password) {    
    return password != null && password.length() >= 8; // Example rule  
}  
 
    @Override
    public StudentsEntity findStudentRecordByRollNumber(String rollNo)
        throws StudentNotFoundException{
         Query qry=em.createQuery("select s from StudentsEntity s where s.rollNo=:rollNo");
        qry.setParameter("rollNo", rollNo);
        StudentsEntity student; 
       try{
           student = (StudentsEntity) qry.getSingleResult();
        } catch (NoResultException e) {

            throw new StudentNotFoundException("No Student found with Roll Number: " + rollNo);
        }
    
        return student;
    } 
   @Override
    public StudentsEntity addStudent(String studentName,String batchNo,String rollNo,String email,String password,int sectionId)
        throws  NullStudentNameException,NullStudentBatchNoException,NullStudentRollNoException,StudentRecordAlreadyExistException,InvalidStudentNameLengthException,SectionNotFoundException,InvalidEmailException,WeakStudentPasswordException,InvalidSectionIdException{
          StudentsEntity student=new StudentsEntity();
if(studentName==null ||studentName.isEmpty()){
            throw new NullStudentNameException("StudentName is Null");
        }
else if( studentName.length()<3){
            throw new InvalidStudentNameLengthException("student name is shorter than three characters");
        }
   else if(batchNo==null ||batchNo.isEmpty()){
            throw new NullStudentBatchNoException("Student Batch Number is Null");
        }
   else if(rollNo==null ||rollNo.isEmpty()){
            throw new NullStudentRollNoException("Student Roll Number is Null");
        }
     else  if (!isValidPassword(password)|| password.isEmpty()) {  
            throw new WeakStudentPasswordException("password does not meet the strength requirements.");  
        }
           String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // basic regex for email validation  
       if (email == null || !email.matches(emailRegex)) {  
        throw new InvalidEmailException("Invalid email format: " + email);  
    }    
        
        StudentsEntity existingStudent=null;
        try {
            existingStudent = findStudentRecordByRollNumber(  rollNo);
            throw new StudentRecordAlreadyExistException("Student Already Exist With The Roll Number "+rollNo);
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
        public StudentsEntity UpdateStudentRecord(int studentId, String studentName,String batchNo,String rollNo,String email,String password,int sectionId) 
                throws NullStudentNameException,NullStudentBatchNoException,StudentRecordAlreadyExistException,NullStudentRollNoException,WeakStudentPasswordException,StudentNotFoundException,InvalidStudentIdException,InvalidStudentNameLengthException,SectionNotFoundException,InvalidEmailException,InvalidSectionIdException{
            StudentsEntity student=findStudentById(studentId);
        if(student!=null){
             if(studentName==null || studentName.isEmpty()){
            throw new NullStudentNameException("StudentName is Null");
        }
      else if( studentName.length()<3){
            throw new InvalidStudentNameLengthException("student name is shorter than three characters");
        } 
      else if(batchNo==null ||batchNo.isEmpty()){
            throw new NullStudentBatchNoException("StudentName is Null");
        }
      else if(rollNo==null ||rollNo.isEmpty()){
            throw new NullStudentRollNoException("StudentName is Null");
        }
       String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // basic regex for email validation  
       if (email == null || !email.matches(emailRegex)||email.isEmpty()) {  
        throw new InvalidEmailException("Invalid email format: " + email);  
    }     
    
       else if (!isValidPassword(password)||password.isEmpty()) {  
           throw new WeakStudentPasswordException("New password does not meet the strength requirements.");  
        
       }
        StudentsEntity existingStudent=null;
        try {
            existingStudent = findStudentRecordByRollNumber(  rollNo);
            throw new StudentRecordAlreadyExistException("Student Already Exist With The Roll Number "+rollNo);
        } catch (StudentNotFoundException ex) {
          
             student.setStudentName(studentName);
        student.setBatchNo(batchNo);
        student.setEmail(email);
        student.setRollNo(rollNo);
        student.setPassword(password);
        SectionsEntity section =sectionManager.findSectionById(sectionId);
        student.setSectionId(section);
 
            em.merge(student);     
        }}
        else{
            throw new StudentNotFoundException("No Student Found With ID: "+studentId);
            
        }
    return student;    }
  
    @Override
    public StudentsEntity DeleteStudentById(int studentId) throws StudentNotFoundException,InvalidStudentIdException{
        StudentsEntity student=findStudentById(studentId);
        em.remove(student);
        return student;
            
        }
 
        @Override
        public StudentsEntity UpdateStudentPassword(int studentId, String oldPassword,String newPassword) 
                throws StudentNotFoundException,InvalidStudentOldPasswordException,WeakStudentPasswordException,InvalidStudentIdException{
            StudentsEntity student=findStudentById(studentId);
        if(student!=null){
            
         if (!student.getPassword().equals(oldPassword)) {  
            throw new InvalidStudentOldPasswordException("Old password is incorrect for Student ID: " + studentId);  
        } 
         if (!isValidPassword(newPassword)) {  
            throw new WeakStudentPasswordException("New password does not meet the strength requirements.");  
        }
            student.setPassword(newPassword);
            em.merge(student);
        }
        else{
            throw new StudentNotFoundException("No Student Found With ID: "+studentId);
            
        }
    return student;
    }
     
    public void persist(Object object) {
        em.persist(object);
    }

   

   
}
