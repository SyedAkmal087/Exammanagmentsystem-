/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;


import com.csworkshop.exammanagement.exceptions.AdminAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminIdException;
import com.csworkshop.exammanagement.exceptions.AdminNotFoundException;
import com.csworkshop.exammanagement.entity.AdminEntity;
import com.csworkshop.exammanagement.exceptions.AdminNotUpdatedException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminNameLengthException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminPhoneNoException;
import com.csworkshop.exammanagement.exceptions.NullAdminEmailException;
import com.csworkshop.exammanagement.exceptions.NullAdminNameException;
import com.csworkshop.exammanagement.exceptions.NullAdminPasswordException;
import com.csworkshop.exammanagement.exceptions.NullAdminPhoneNoException;
import com.csworkshop.exammanagement.exceptions.WeakAdminPasswordException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface AdminSessionRemote {
     public AdminEntity findAdminById(int adminId) throws AdminNotFoundException,InvalidAdminIdException;
    public List<AdminEntity> findAdminByName(String adminName) throws AdminNotFoundException,NullAdminNameException,InvalidAdminNameLengthException;
 public List<AdminEntity> GetAllAdminsData() ;
     public AdminEntity AddAdmin(String name, String email, String phoneNum, String password)
            throws NullAdminNameException, InvalidAdminNameLengthException, NullAdminPhoneNoException, InvalidAdminPhoneNoException,
            NullAdminEmailException, InvalidAdminEmailException, WeakAdminPasswordException,
            AdminAlreadyExistsException;
     public  AdminEntity findAdminByEmailandPhoneNumber(String adminEmail, String adminPhoneNumber)throws AdminNotFoundException;
      
    public void deleteAdminById(int adminId)throws InvalidAdminIdException, AdminNotFoundException ;
   
    public void updateAdmin(int adminId, String newAdminName, String newEmail, String newPhoneNo, String newPassword)
    throws InvalidAdminIdException, NullAdminNameException, InvalidAdminNameLengthException, NullAdminPhoneNoException, InvalidAdminPhoneNoException,
            NullAdminEmailException, InvalidAdminEmailException, WeakAdminPasswordException, AdminNotFoundException,
            AdminNotUpdatedException ;

    public AdminEntity findAdminByEmailAndPassword(String email, String password) throws AdminNotFoundException, NullAdminEmailException, InvalidAdminEmailException, NullAdminPasswordException, WeakAdminPasswordException;
}
