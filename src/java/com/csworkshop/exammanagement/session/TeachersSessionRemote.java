/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
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
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface TeachersSessionRemote {

    public TeachersEntity addTeacher(String name, String phone_num, String email, String password, boolean availability) throws NullTeacherNameException, InvalidTeacherNameException, NullTeacherPhoneNoException, InvalidTeacherPhoneNoException, NullTeacherEmailException, InvalidTeacherEmailException, NullTeacherPasswordException, WeakTeacherPasswordException, TeacherAlreadyExistsException;

    public TeachersEntity findTeacherbyId(int id) throws InvalidTeacherIdException, TeacherNotFoundException;

    public List<TeachersEntity> findTeacherByName(String name) throws InvalidTeacherNameException, NullTeacherNameException, TeacherNotFoundException;

    public void removeTeacherById(int teacherId) throws InvalidTeacherIdException, TeacherNotFoundException;

    public TeachersEntity updateTeacher(int teacherId, String newTeacherName, String newPhone_no, String newEmail, String newPassword, boolean avail) throws TeacherAlreadyExistsException,
            InvalidTeacherIdException, NullTeacherNameException, InvalidTeacherNameException, NullTeacherPhoneNoException, InvalidTeacherPhoneNoException, NullTeacherEmailException, InvalidTeacherEmailException, NullTeacherPasswordException, WeakTeacherPasswordException, TeacherNotFoundException, TeacherNotUpdatedException;

    public List<TeachersEntity> getAllTeachers() throws TeacherNotFoundException;
    public TeachersEntity findTeacherByEmailandPhoneNumber(String teacherEmail, String teacherPhoneNumber)throws TeacherNotFoundException;
    public TeachersEntity UpdateTeacherPassword(int teacherId, String oldPassword,String newPassword) 
                throws TeacherNotFoundException,InvalidTeacherOldPasswordException,WeakTeacherPasswordException,InvalidTeacherIdException;

    public TeachersEntity findTeacherRecordByPhoneNumber(String teacherPhoneNumber);

    public TeachersEntity findTeacherRecordByEmail(String teacherEmail);
       

}
