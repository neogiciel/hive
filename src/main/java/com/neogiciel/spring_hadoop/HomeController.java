package com.neogiciel.spring_hadoop;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neogiciel.spring_hadoop.hive.HiveManager;
import com.neogiciel.spring_hadoop.util.Trace;

//import com.neogiciel.spring_hadoop.util.Trace;

@RestController
@RequestMapping("/")
public class HomeController {
    
    /*
     * hive
    */
    @GetMapping(value = "/hive",produces="application/json") 
    public String hive()  {
        Trace.info("Appel Test");

        HiveManager bdd = new HiveManager();
        List liste = bdd.getListEcole();
        
        for (Object object : liste) {
            Trace.info(object.toString());
        }
       return getJSON("liste", "liste").toString();
    }

     /*
      * getJSON
      */
     public JSONObject getJSON(String value,String key){
        JSONObject obj = new JSONObject();
        try{
            obj.put(value, key);
        }catch(Exception e){
            Trace.info("Erreur = "+e.getMessage());
        }
        return obj;
     }

  

}
