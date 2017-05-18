package com.sample.springboot;
 
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.sample.springboot.model.Document;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringBootRestApi/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllDocuments(){
        System.out.println("Testing listAllDocuments API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> DocumentsMap = restTemplate.getForObject(REST_SERVICE_URI+"/Document/", List.class);
         
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
        Document Document = restTemplate.getForObject(REST_SERVICE_URI+"/Document/1", Document.class);
        System.out.println(Document);
    }
     
    /* POST */
    private static long createDocument() {
        System.out.println("Testing create Document API----------");
        RestTemplate restTemplate = new RestTemplate();
        Document Document = new Document("Test-Doc01.pdf","/opt/data/2017/5/16/10/10/10", "johnd","2017-05-16 10:10:10");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/Document/", Document, Document.class);
        System.out.println("Location : "+uri.toASCIIString());
        return Document.getId();
    }
 
    /* PUT */
    private static void updateDocument(long id) {
        System.out.println("Testing update Document API----------");
        RestTemplate restTemplate = new RestTemplate();
        Document Document  = new Document("Test-Doc01-renamed.pdf","/opt/data/2017/5/16/10/10/10", "johnd","2017-05-16 10:10:15");
        Document.setId(id);
        restTemplate.put(REST_SERVICE_URI+"/Document/"+id, Document);
        System.out.println(Document);
    }
 
    /* DELETE */
    private static void deleteDocument() {
        System.out.println("Testing delete Document API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/Document/3");
    }
 
 
    /* DELETE */
    private static void deleteAllDocuments() {
        System.out.println("Testing all delete Documents API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/Document/");
    }
 
    public static void main(String args[]){
        listAllDocuments();
        getDocument();
        //new Document("Test-Doc01.pdf","/opt/data/2017/5/16/10/10/10", "johnd","2017-05-16 10:10:10");
        long id=createDocument();
        listAllDocuments();
        updateDocument(id);
        listAllDocuments();
        deleteDocument();
        listAllDocuments();
        deleteAllDocuments();
        listAllDocuments();
    }
}