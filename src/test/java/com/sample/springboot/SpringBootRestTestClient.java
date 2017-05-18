package com.sample.springboot;
 
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import com.sample.springboot.model.Document;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringBootRestApi/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllDocuments(){
        System.out.println("Testing listAllDocuments API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> DocumentsMap = restTemplate.getForObject(REST_SERVICE_URI+"/document", List.class);
         
        if(DocumentsMap!=null){
            for(LinkedHashMap<String, Object> map : DocumentsMap){
                System.out.println("Document : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No Document exist----------");
        }
    }
     
    /* GET */
    private static void getDocument(){
        System.out.println("Testing getDocument API----------");
        RestTemplate restTemplate = new RestTemplate();
        Document document = restTemplate.getForObject(REST_SERVICE_URI+"/document/100", Document.class);
        Assert.assertEquals(document, null);
        System.out.println(document);
    }
     
 
    public static void main(String[] args){
        listAllDocuments();
        getDocument();
    }
}