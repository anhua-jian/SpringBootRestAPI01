package com.sample.springboot.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sample.springboot.model.Document;

public interface DocumentService {
	
	Document findById(long id);
	
	Document findByName(String name);
	
	void saveDocument(Document Document,MultipartFile file);
	
	void updateDocument(Document Document);
	
	void deleteDocumentById(long id);

	List<Document> findAllDocuments();
	
	void deleteAllDocuments();
	
	boolean isDocumentExist(Document Document);
	
}
