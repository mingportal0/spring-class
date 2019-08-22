package com.ehr;

public class DTO {
	private int    total     ;
	private int    num       ;
	
	public DTO() {
		
	}
	public DTO(int total, int num) {
		this.total = total;
		this.num = num;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "DTO [total=" + total + ", num=" + num + "]";
	}
}
