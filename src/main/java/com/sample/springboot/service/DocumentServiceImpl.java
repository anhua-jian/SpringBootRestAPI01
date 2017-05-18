package com.sample.springboot.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sample.springboot.model.Document;



@Service("DocumentService")
public class DocumentServiceImpl implements DocumentService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Document> documents;
	
	static{
		documents= populateDummyDocuments();
	}

	public List<Document> findAllDocuments() {
		return documents;
	}
	
	public Document findById(long id) {
		for(Document Document : documents){
			if(Document.getId() == id){
				return Document;
			}
		}
		return null;
	}
	
	public Document findByName(String name) {
		for(Document Document : documents){
			if(Document.getName().equalsIgnoreCase(name)){
				return Document;
			}
		}
		return null;
	}
	
	public void saveDocument(Document document,MultipartFile file) {
		if(file==null)return;
		document.setId(counter.incrementAndGet());
		Path currentRelativePath = Paths.get("");
		String path=currentRelativePath.toAbsolutePath().normalize().toString()+"/"+document.getPath();
		mkdir(path);
		File f=new File(path+"/"+document.getName());
		FileOutputStream os;
		document.setPath(path);
		documents.add(document);
		try {
			os = new FileOutputStream(f);
			os.write(file.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDocument(Document Document) {
		int index = documents.indexOf(Document);
		documents.set(index, Document);
	}
	private void mkdir(String path) {      
	      File f = null;
	      
	      try {
	      
	         // returns pathnames for files and directory
	         f = new File(path);
	         f.mkdir();
	      } catch(Exception e) {
	      
	         // if any error occurs
	         e.printStackTrace();
	      }
	   }
	public void deleteDocumentById(long id) {
		
		for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext(); ) {
		    Document Document = iterator.next();
		    if (Document.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isDocumentExist(Document Document) {
		return findByName(Document.getName())!=null;
	}
	
	public void deleteAllDocuments(){
		documents.clear();
	}

	private static List<Document> populateDummyDocuments(){
		List<Document> Documents = new ArrayList<Document>();
		
		Documents.add(new Document(counter.incrementAndGet(),"Doc01.pdf","/opt/data/2017/5/16/10/10/10", "johnd","2017-05-16 10:10:10"));
		Documents.add(new Document(counter.incrementAndGet(),"Doc02.pdf","/opt/data/2017/5/16/10/10/15", "maryc","2017-05-16 10:10:15"));
		Documents.add(new Document(counter.incrementAndGet(),"Doc03.pdf","/opt/data/2017/5/16/10/10/20", "joshb","2017-05-16 10:10:20"));
		Documents.add(new Document(counter.incrementAndGet(),"Doc04.pdf","/opt/data/2017/5/16/10/10/25", "alicel","2017-05-16 10:10:25"));
		
		return Documents;
	}



}
