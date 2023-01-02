package com.wipro.candidate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.util.*;
public class CandidateDAO {
    private Connection connect = DBUtil.getDBConn();
    public String addCandidate(CandidateBean candidateBean){
        try {
            PreparedStatement pre_stmt = connect.prepareStatement("insert into CANDIDATE_TBL values(?,?,?,?,?,?,?)");
            pre_stmt.setString(1, candidateBean.getId());
            pre_stmt.setString(2, candidateBean.getName());
            pre_stmt.setInt(3, candidateBean.getM1());
            pre_stmt.setInt(4, candidateBean.getM2());
            pre_stmt.setInt(5, candidateBean.getM3());
            pre_stmt.setString(6, candidateBean.getResult());
            pre_stmt.setString(7, candidateBean.getGrade());
            //.excuteupdate() returns no of rows alters or created
            if(pre_stmt.executeUpdate()==1)
                return "SUCCESS";
            else
                return "FAIL";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }
    //return cadidate of the given criteria
    public ArrayList<CandidateBean> getByResult(String criteria){
        ArrayList<CandidateBean> data = new ArrayList<CandidateBean>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs=null;
            if(criteria=="PASS"||criteria=="FAIL")
                rs = stmt.executeQuery("select * from CANDIDATE_TBL where Result ='"+criteria+"'");
            if(criteria=="ALL")
                rs=stmt.executeQuery("select * from CANDIDATE_TBL");
            while(rs.next()){
                CandidateBean bean = new CandidateBean();
                bean.setId(rs.getString(1));
                bean.setName(rs.getString(2));
                bean.setM1(rs.getInt(3));
                bean.setM2(rs.getInt(4));
                bean.setM3(rs.getInt(5));
                bean.setResult(rs.getString(6));
                bean.setGrade(rs.getString(7));
                data.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    //funtion to generate candidate id
    public String generateCandidateId(String name){
        String  new_str  = name.charAt(0)+""+name.charAt(1);
        int n=0;
        try {
            Statement stmt= connect.createStatement();
            ResultSet rs = stmt.executeQuery("select CANDID_SEQ.nextval from dual");
            rs.next();
            n=rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new_str.toUpperCase()+""+n;
    }
}
