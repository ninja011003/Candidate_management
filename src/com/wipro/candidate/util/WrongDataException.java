package com.wipro.candidate.util;

public class WrongDataException extends Exception{
    public WrongDataException(){
        super();
    }
    @Override
    public String toString(){
        return "Data Incorrect";
    }
}
