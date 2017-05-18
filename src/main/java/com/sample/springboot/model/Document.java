package com.sample.springboot.model;
	
public class Document {

	private long id;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private String name;
	private String path;
	private String createdAt;
	private String createdBy;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Document(){
	}
	
	public Document(String name, String path,String createdBy,String createdAt){
		this.name = name;
		this.path = path;
		this.createdAt=createdAt;
		this.createdBy=createdBy;
	}
	public Document(long id,String name, String path,String createdBy,String createdAt){
		this.id=id;
		this.name = name;
		this.path = path;
		this.createdAt=createdAt;
		this.createdBy=createdBy;
	}
	public Document(int id){
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		
		Document other = (Document) obj;
		if(id==other.id)return true;
		if (name.compareTo(other.name)!=0)
			return false;
		if (path.compareTo(other.path)!=0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Document: [pathname: " + path + name+" created by " +createdBy+" at: " + createdAt + "]";
	}


}
