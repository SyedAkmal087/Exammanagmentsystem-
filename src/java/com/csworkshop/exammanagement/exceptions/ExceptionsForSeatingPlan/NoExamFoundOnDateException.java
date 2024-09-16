/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.csworkshop.exammanagement.exceptions.ExceptionsForSeatingPlan;

/**
 *
 * @author syeda
 */
public class NoExamFoundOnDateException extends Exception{
    
    public NoExamFoundOnDateException(String msg)
    {
        super(msg);
    }
    
}
