package com.in28minutes.springboot.rest.example.student;

public class Id implements IKeyRequest {
	private String id;
	
	public Id(String id) {
		this.id = id;
	}

	@Override
	public String logKey() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
