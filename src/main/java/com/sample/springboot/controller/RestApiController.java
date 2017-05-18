package com.sample.springboot.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.sample.springboot.model.Document;
import com.sample.springboot.service.DocumentService;
import com.sample.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	DocumentService documentService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Documents---------------------------------------------

	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public ResponseEntity<List<Document>> listAllDocuments() {
		List<Document> Documents = documentService.findAllDocuments();
		if (Documents.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Document>>(Documents, HttpStatus.OK);
	}

	// -------------------Retrieve Content of Document------------------------------------------

	@RequestMapping(value = "/document/content/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentContent(@PathVariable("id") long id,HttpServletResponse response)  {
		logger.info("Fetching Content of Document with id {}", id);
		Document document = documentService.findById(id);
		if (document == null) {
			logger.error("Document with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Document with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		String fName=document.getPath()+"/"+document.getName();
		try {
			FileInputStream is=new FileInputStream(fName);

			response.setHeader("Content-Disposition", "attachment;filename="+document.getName());
			IOUtils.copy(is, response.getOutputStream());
		      response.flushBuffer();

		      ResponseEntity<Object> resp= new ResponseEntity<Object>(null, HttpStatus.OK);
		      
		      return resp;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(new CustomErrorType("Physical Document with id " + id 
					+ " not found: "+document.getPath()+"/"+document.getName()), HttpStatus.NOT_FOUND);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(new CustomErrorType("File output error for Document with id " + id), HttpStatus.NOT_FOUND);
		}
	}
	// -------------------Retrieve Single Document------------------------------------------

	@RequestMapping(value = "/document/meta/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDocument(@PathVariable("id") long id) {
		logger.info("Fetching Document with id {}", id);
		Document Document = documentService.findById(id);
		if (Document == null) {
			logger.error("Document with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Document with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Document>(Document, HttpStatus.OK);
	}

	// -------------------Create a Document-------------------------------------------

	@RequestMapping(value = "/document", method = RequestMethod.POST)
	public ResponseEntity<?> createDocument(@RequestParam(value="file", required=true) MultipartFile file ,
            @RequestParam(value="createdBy", required=true) String createdBy,
            @RequestParam(value="createdAt", required=true) String createdAt) {
		logger.info("Creating Document : {}");
		Document document=new Document(file.getOriginalFilename(),createdAt.replace(":", "-"),createdBy,createdAt);

		if (documentService.isDocumentExist(document)) {
			logger.error("Unable to create. A Document with name {} already exist", document.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Document with name " + 
			document.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		documentService.saveDocument(document,file);

		return new ResponseEntity<String>("Document saved as id="+document.getId(), HttpStatus.OK);
	}

	

}