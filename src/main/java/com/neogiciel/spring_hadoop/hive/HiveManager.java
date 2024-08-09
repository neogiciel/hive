package com.neogiciel.spring_hadoop.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.neogiciel.spring_hadoop.model.Ecole;
import com.neogiciel.spring_hadoop.util.Trace;


public class HiveManager {

    private Connection con;

    

    /*
     * Constructeur
     */
    public HiveManager(){};

    /*
     * openConnection()
     *
     *  hive  beeline -u jdbc:hive2://localhost:10000 -n root
     * docker exec -it spark-master bash
     * 
     */
    void openConnection(){
        try{
            con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "hive", "");
        }catch(SQLException e){
            Trace.info("Erreur = "+e.getMessage());
        }
        
    }
    
    /*
    * openConnection()
    */
    void closeConnection(){
    try{
        con.close();
    }catch(SQLException e){
        Trace.info("Erreur = "+e.getMessage());
    }
    
    }

    /*
     * openConnection()
     */
    ResultSet query(String sql){
        try{
            Statement stmt = con.createStatement();
            Trace.info("sql = "+sql);
            ResultSet res = stmt.executeQuery(sql);
            return res;
        }catch(SQLException e){
            Trace.info("Erreur = "+e.getMessage());
            return null;
        }
        
    }


    public List<Ecole> getListEcole(){

        try{
            
        List<Ecole> list = new ArrayList<>();
       
        this.openConnection();
        ResultSet rs = this.query("SELECT * FROM ecole");

        while (rs.next()) {
            
            int id = 0;
            String prenom = "";
            String nom = "";
            String age = "";

            if(rs.getString("id") != null ) {
                id=Integer.parseInt(rs.getString("id"));  
                Trace.info("id=("+rs.getString("id")+")");
            }
            
            if(rs.getString("prenom") != null ) {
                prenom = rs.getString("prenom");
                prenom = prenom.trim();
                Trace.info("prenom=("+prenom +")");
            }
            if(rs.getString("nom") != null ) {
                nom = rs.getString("nom");
                nom = nom.trim();
                Trace.info("nom=("+nom+")");
            }
            if(rs.getString("age") != null ) {
                age = rs.getString("age");
                Trace.info("age=("+age+")");
            }
            
            Ecole ecole = new Ecole(0,"prenom","nom","age");
            list.add(ecole);
          }
  
        this.closeConnection();
        return list;

        }catch(SQLException e){
            Trace.info("Erreur = "+e.getMessage());
            return null;
        }
    }


    /*
     * openConnection()
     */
    void loadData(String filepath, String tablename){
        try{
            Statement stmt = con.createStatement();
            stmt.execute("drop table if exists " + tablename);
            stmt.execute("create table " + tablename + " (key int, value string)");

            //String sql = "load data local inpath " + filepath + " into table " + tablename;
            //String sql = "load data local inpath '/home/a.txt' into table " + tablename;
            filepath = "/home/a.txt";
            String sql = "load data local inpath '" + filepath + "' into table " + tablename;

            System.out.println("Running: " + sql);
            stmt.execute(sql);

            sql = "SELECT * FROM '" + tablename;
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
            }
           
        }catch(SQLException e){
            Trace.info("Erreur = "+e.getMessage());
        }
        
    }

}
