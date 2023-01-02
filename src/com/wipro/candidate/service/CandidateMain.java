package com.wipro.candidate.service;

import java.util.ArrayList;

import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.dao.CandidateDAO;
import com.wipro.candidate.util.WrongDataException;

public class CandidateMain {
    public String addCandidate(CandidateBean candBean){
        CandidateDAO cDao = new CandidateDAO();
        try{
            if(candBean==null||candBean.getName()==null||candBean.getName().length()<2){
                throw new WrongDataException();
            }
            if(candBean.getM1()<0||candBean.getM1()>100||candBean.getM2()<0||candBean.getM2()>100||candBean.getM3()<0||candBean.getM3()>100){
                throw new WrongDataException();
            }
        }
        catch(WrongDataException e){
            return e.toString();
        }
        candBean.setId(cDao.generateCandidateId(candBean.getName()));
        //computation of grades and result
        int total= candBean.getM1()+candBean.getM2()+candBean.getM3();
        if(total>=240){
            candBean.setResult("PASS");
            candBean.setGrade("Distinction");
        }
        else if(total>=180&&total<240){
            candBean.setResult("PASS");
            candBean.setGrade("First Class");
        }
        else if(total>=150&&total<180){
            candBean.setResult("PASS");
            candBean.setGrade("Second Class");
        }
        else if(total>=105&&total<150){
            candBean.setResult("PASS");
            candBean.setGrade("Third Class");
        }
        else{
            candBean.setResult("FAIL");
            candBean.setGrade("No Grade");
        }
        //insertion of data in the Database
        if(cDao.addCandidate(candBean)=="SUCCESS"){
            return candBean.getId()+":"+candBean.getResult();
        }
        else
            return "error";
    }
    public ArrayList<CandidateBean> displayAll(String criteria){
        ArrayList<CandidateBean> data = new ArrayList<CandidateBean>();
        CandidateDAO cDao = new CandidateDAO();
        try{
            if(criteria!="PASS"&&criteria!="FAIL"&&criteria!="ALL")
                throw new WrongDataException();
            data = cDao.getByResult(criteria);
            return data;
        }
        catch(WrongDataException e){
            data=null;
            return data;
        }
    }
    public static void main(String[] args) {
        /* 
        CandidateDAO obj = new CandidateDAO();
        CandidateBean bean = new CandidateBean();
        CandidateMain cMain = new CandidateMain();
        bean.setName("naresh");
        bean.setM1(13);
        bean.setM2(29);
        bean.setM3(19);
        System.out.println(cMain.addCandidate(bean));
        System.out.println(cMain.displayAll("ALL").get(1).getName());
        //project over
        */
    }
}
