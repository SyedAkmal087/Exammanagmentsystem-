/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.exceptions.AdminNotUpdatedException;
import com.csworkshop.exammanagement.exceptions.AdminAlreadyExistsException;
import com.csworkshop.exammanagement.exceptions.WeakAdminPasswordException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminEmailException;
import com.csworkshop.exammanagement.exceptions.NullAdminEmailException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminPhoneNoException;
import com.csworkshop.exammanagement.exceptions.NullAdminPhoneNoException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminNameLengthException;
import com.csworkshop.exammanagement.exceptions.NullAdminNameException;
import com.csworkshop.exammanagement.exceptions.InvalidAdminIdException;
import com.csworkshop.exammanagement.exceptions.AdminNotFoundException;
import com.csworkshop.exammanagement.entity.AdminEntity;
import com.csworkshop.exammanagement.exceptions.NullAdminPasswordException;
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
public class AdminSession implements AdminSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

    @Override
    public AdminEntity findAdminById(int adminId) throws AdminNotFoundException, InvalidAdminIdException {
        if (adminId <= 0) {
            throw new InvalidAdminIdException("Invalid Admin ID: " + adminId);
        }
        AdminEntity admin = em.find(AdminEntity.class, adminId);
        if (admin == null) {
            throw new AdminNotFoundException("No Admin exist with id " + adminId);
        }
        return admin;
    }

    @Override
    public List<AdminEntity> findAdminByName(String adminName) throws AdminNotFoundException, NullAdminNameException, InvalidAdminNameLengthException {
        if (adminName == null || adminName.isEmpty()) {
            throw new NullAdminNameException("Admin name can not be Null or empty");
        }
        if (adminName.length() < 3) {
            throw new InvalidAdminNameLengthException("Admin name can not shorter than three characters");
        }
        Query qry = em.createQuery("select a from AdminEntity a where a.adminName=:adminName");
        qry.setParameter("adminName", adminName);

        List<AdminEntity> admins;
        admins = qry.getResultList();

        if (admins.isEmpty()) {
            throw new AdminNotFoundException("No Admin found for admin name :" + adminName);
        }

        return admins;
    }

    @Override
    public List<AdminEntity> GetAllAdminsData() {
        List<AdminEntity> allAdmins;
        Query qry = em.createQuery("SELECT a FROM AdminEntity a");
        allAdmins = qry.getResultList();

        return allAdmins;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8; // Example rule  
    }

    private void InputValidation(String adminName, String adminEmail, String phoneNo, String adminPassword)
            throws NullAdminNameException, InvalidAdminNameLengthException, NullAdminPhoneNoException, InvalidAdminPhoneNoException, NullAdminEmailException, InvalidAdminEmailException,
            WeakAdminPasswordException {
        if (adminName == null || adminName.isEmpty()) {
            throw new NullAdminNameException(" Name can not be Null or empty");
        }
        if (adminName.length() < 3) {
            throw new InvalidAdminNameLengthException(" Name can not shorter than three characters");
        }
        if (adminEmail == null || adminEmail.isEmpty()) {
            throw new NullAdminEmailException("E-mail cannot be null or empty.");
        }
        if (phoneNo == null || phoneNo.isEmpty()) {
            throw new NullAdminPhoneNoException("Phone number cannot be null or empty)");
        }
        if (!phoneNo.matches("\\d{11}")) {
            throw new InvalidAdminPhoneNoException("Invalid Phone Number(must be 11-digit number)");
        }
        if (!adminEmail.matches("^[A-Za-z0-9+_.-]+@uog\\.edu\\.pk$")) {
            throw new InvalidAdminEmailException("Invalid Email Format(Email must contain uog.edu.pk domain)");
        }
        if (!isValidPassword(adminPassword) || adminPassword.isEmpty()) {
            throw new WeakAdminPasswordException("New password does not meet the strength requirements,must be 8 characters long");
        }
    }

    @Override
    public AdminEntity findAdminByEmailandPhoneNumber(String adminEmail, String adminPhoneNumber) {
        Query qry = em.createQuery("SELECT a FROM AdminEntity a WHERE a.adminEmail=:adminEmail AND a.adminPhoneNumber=:adminPhoneNumber");
        qry.setParameter("adminEmail", adminEmail);
        qry.setParameter("adminPhoneNumber", adminPhoneNumber);

        try {
            return (AdminEntity) qry.getSingleResult();
        } catch (NoResultException e) {
//          throw new AdminNotFoundException("no admin found ");
            return null;
        }

    }

    @Override
    public AdminEntity AddAdmin(String name, String email, String phoneNum, String password)
            throws NullAdminNameException, InvalidAdminNameLengthException, NullAdminPhoneNoException, InvalidAdminPhoneNoException,
            NullAdminEmailException, InvalidAdminEmailException, WeakAdminPasswordException,
            AdminAlreadyExistsException {
        AdminEntity admin = new AdminEntity();
        InputValidation(name, email, phoneNum, password);

        AdminEntity existingAdmin = findAdminByEmailandPhoneNumber(email, phoneNum);
        if (existingAdmin != null) {
            throw new AdminAlreadyExistsException("Admin already Exists with the provided details ");
        } else {
            admin.setAdminName(name);
            admin.setAdminPhoneNumber(phoneNum);
            admin.setAdminEmail(email);
            admin.setAdminPassword(password);
            persist(admin);
        }
        return admin;
    }

    @Override
    public void deleteAdminById(int adminId)
            throws InvalidAdminIdException, AdminNotFoundException {

        if (adminId <= 0) {
            throw new InvalidAdminIdException("ID cannot be zero.");
        }

        AdminEntity admin = findAdminById(adminId);
        if (admin == null) {
            throw new AdminNotFoundException("Admin not found with ID: " + adminId);
        }

        em.remove(admin);
    }

    @Override
    public void updateAdmin(int adminId, String newAdminName, String newEmail, String newPhoneNo, String newPassword)
            throws InvalidAdminIdException, NullAdminNameException, InvalidAdminNameLengthException, NullAdminPhoneNoException, InvalidAdminPhoneNoException,
            NullAdminEmailException, InvalidAdminEmailException, WeakAdminPasswordException, AdminNotFoundException,
            AdminNotUpdatedException {

        InputValidation(newAdminName, newEmail, newPhoneNo, newPassword);
        AdminEntity admin = findAdminById(adminId);

        boolean isUpdated = false;
        if (newAdminName != null && !newAdminName.isEmpty()) {
            admin.setAdminName(newAdminName);
            isUpdated = true;
        }
        if (newPhoneNo != null && !newPhoneNo.isEmpty()) {
            admin.setAdminPhoneNumber(newPhoneNo);
            isUpdated = true;
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            admin.setAdminEmail(newEmail);
            isUpdated = true;
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            admin.setAdminPassword(newPassword);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new AdminNotUpdatedException("Update Failed!");
        }
        em.merge(admin);

    }

    @Override
    public AdminEntity findAdminByEmailAndPassword(String email, String password)
            throws AdminNotFoundException, NullAdminEmailException, InvalidAdminEmailException, NullAdminPasswordException, WeakAdminPasswordException {

        if (email == null || email.isEmpty()) {
            throw new NullAdminEmailException("Please Enter Email");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@uog\\.edu\\.pk$")) {
            throw new InvalidAdminEmailException("Invalid Email Format");
        }

        if (password == null || password.isEmpty()) {
            throw new NullAdminPasswordException("Please Enter Password");
        }

        if (!isValidPassword(password)) {
            throw new WeakAdminPasswordException("Password does not meet the strength requirements (must be 8 characters long)");
        }

        TypedQuery<AdminEntity> qry = em.createQuery(
                "SELECT a FROM AdminEntity a WHERE a.adminEmail = :email AND a.adminPassword = :password",
                AdminEntity.class
        );
        qry.setParameter("email", email);
        qry.setParameter("password", password);

        AdminEntity admin;
        try {
            admin = qry.getSingleResult();
        } catch (NoResultException e) {
            throw new AdminNotFoundException("Invalid login!Please try again");
        }

        return admin;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
