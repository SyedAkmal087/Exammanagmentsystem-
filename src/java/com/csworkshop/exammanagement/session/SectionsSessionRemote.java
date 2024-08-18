/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.csworkshop.exammanagement.session;


import com.csworkshop.exammanagement.entity.SectionsEntity;
import com.csworkshop.exammanagement.exceptions.InvalidSectionIdException;
import com.csworkshop.exammanagement.exceptions.InvalidSectionNameException;
import com.csworkshop.exammanagement.exceptions.SectionNotFoundException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author syeda
 */
@Remote
public interface SectionsSessionRemote {

    public SectionsEntity addSection(String sectionName) throws InvalidSectionNameException;

    public SectionsEntity findSectionById(int SectionId) throws SectionNotFoundException,InvalidSectionIdException;

    public void deleteSectionById(int SectionId) throws SectionNotFoundException,InvalidSectionIdException;

    public SectionsEntity updateSection(int SectionId, String SectionName) throws SectionNotFoundException,InvalidSectionIdException;

    public List<SectionsEntity> getAllSection() throws SectionNotFoundException;

    public SectionsEntity findSectionBySectionName(String sectionName) throws SectionNotFoundException;
    
}
