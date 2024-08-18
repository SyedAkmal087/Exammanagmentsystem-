/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.csworkshop.exammanagement.session;

import com.csworkshop.exammanagement.entity.SectionsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.InvalidSectionNameException;
import com.csworkshop.exammanagement.exceptions.SectionNotFoundException;
import java.util.List;
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
public class SectionsSession implements SectionsSessionRemote {

    @PersistenceContext(unitName = "exammanagmentPU")
    private EntityManager em;

   

    @Override
    public SectionsEntity addSection(String sectionName) throws InvalidSectionNameException {
        SectionsEntity section = new SectionsEntity();
        if (sectionName == null|| sectionName.isEmpty()) {
            throw new InvalidSectionNameException("Section name is null or empty");
        }
        section.setSectionName(sectionName);
        persist(section);
        return section;
    }

    @Override
    public SectionsEntity findSectionById(int sectionId) throws SectionNotFoundException,InvalidSectionIdException {
        if (sectionId <= 0) {
            throw new InvalidSectionIdException("Invalid Section ID: " + sectionId);
        }
        SectionsEntity section = em.find(SectionsEntity.class, sectionId);
        if (section == null) {
            throw new SectionNotFoundException("No section exist with id: " + sectionId);
        }
        return section;

    }

    @Override
    public SectionsEntity findSectionBySectionName(String sectionName) throws SectionNotFoundException {
        SectionsEntity section;
        Query qry = em.createQuery("select s from SectionsEntity s where s.sectionName=:sectionName");
        qry.setParameter("sectionName", sectionName);
        try {
            section = (SectionsEntity) qry.getSingleResult();
        } catch (NoResultException e) {

            throw new SectionNotFoundException("No Room found with Room Number: " + sectionName);
        }

        return section;
    }

    @Override
    public void deleteSectionById(int sectionId) throws SectionNotFoundException,InvalidSectionIdException{
        SectionsEntity section = findSectionById(sectionId);
        em.remove(section);

    }

    @Override
    public SectionsEntity updateSection(int SectionId, String sectionName) throws SectionNotFoundException,InvalidSectionIdException {
        SectionsEntity section = findSectionById(SectionId);
        if (section != null) {
            section.setSectionName(sectionName);
            em.merge(section);
        }
        return section;
    }

    @Override
    public List<SectionsEntity> getAllSection() throws SectionNotFoundException {
        Query qry = em.createQuery("Select s from SectionsEntity s");
        List<SectionsEntity> sections = qry.getResultList();
        if (sections == null) {
            throw new SectionNotFoundException("No section found in DB");
        }
        return sections;
    }

    public void persist(Object object) {
        em.persist(object);
    }

   
}
