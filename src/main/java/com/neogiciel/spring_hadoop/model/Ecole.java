package com.neogiciel.spring_hadoop.model;

import java.util.List;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

//@Entity
//@Table(name = "ECOLE")
public class Ecole {
   
   // @Id //@GeneratedValue
   // @GeneratedValue(strategy=GenerationType.IDENTITY)
   // @Column(name = "ID")
    public int id;
    
    //@Column(name="PRENOM")
    public String prenom;

    //@Column(name="NOM")
    public String nom;

    //@Column(name="AGE")
    public String age;


    /*
     * Constructeur
    */
    public Ecole(){
    }

    public Ecole(int id, String prenom, String nom, String age){
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
    }



}
