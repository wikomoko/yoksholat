package com.example.marisholat.Model;


import java.util.List;


public class ResponseCariKota{
	private List<DataModelSemua> data;
	private boolean status;

	public List<DataModelSemua> getData(){
		return data;
	}

	public boolean isStatus(){
		return status;
	}
}