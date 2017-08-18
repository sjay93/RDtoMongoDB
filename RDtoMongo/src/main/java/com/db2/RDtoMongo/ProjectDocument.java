package com.db2.RDtoMongo;

import java.util.List;

public class ProjectDocument {
	
	private String Pnumber;
	private String Pname;
	private String Dname;
	private List<Employee> employeeList;
	
	public String getPnumber() {
		return Pnumber;
	}
	public void setPnumber(String pnumber) {
		Pnumber = pnumber;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	public String getDname() {
		return Dname;
	}
	public void setDname(String dname) {
		Dname = dname;
	}
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Dname == null) ? 0 : Dname.hashCode());
		result = prime * result + ((Pname == null) ? 0 : Pname.hashCode());
		result = prime * result + ((Pnumber == null) ? 0 : Pnumber.hashCode());
		result = prime * result + ((employeeList == null) ? 0 : employeeList.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectDocument other = (ProjectDocument) obj;
		if (Dname == null) {
			if (other.Dname != null)
				return false;
		} else if (!Dname.equals(other.Dname))
			return false;
		if (Pname == null) {
			if (other.Pname != null)
				return false;
		} else if (!Pname.equals(other.Pname))
			return false;
		if (Pnumber == null) {
			if (other.Pnumber != null)
				return false;
		} else if (!Pnumber.equals(other.Pnumber))
			return false;
		if (employeeList == null) {
			if (other.employeeList != null)
				return false;
		} else if (!employeeList.equals(other.employeeList))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProjectDocument [Pnumber=" + Pnumber + ", Pname=" + Pname + ", Dname=" + Dname + ", employeeList="
				+ employeeList + "]";
	}
	
	
	
	
}
