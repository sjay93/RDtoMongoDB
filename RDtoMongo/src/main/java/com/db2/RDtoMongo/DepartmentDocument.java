package com.db2.RDtoMongo;

import java.util.List;

public class DepartmentDocument {
	
	private String Dnumber;
	private String Dname;
	private String Lname;
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	private List<Location> locationList;
	
	public String getDnumber() {
		return Dnumber;
	}
	public void setDnumber(String dnumber) {
		Dnumber = dnumber;
	}
	public String getDname() {
		return Dname;
	}
	public void setDname(String dname) {
		Dname = dname;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Dname == null) ? 0 : Dname.hashCode());
		result = prime * result + ((Dnumber == null) ? 0 : Dnumber.hashCode());
		result = prime * result + ((Lname == null) ? 0 : Lname.hashCode());
		result = prime * result + ((locationList == null) ? 0 : locationList.hashCode());
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
		DepartmentDocument other = (DepartmentDocument) obj;
		if (Dname == null) {
			if (other.Dname != null)
				return false;
		} else if (!Dname.equals(other.Dname))
			return false;
		if (Dnumber == null) {
			if (other.Dnumber != null)
				return false;
		} else if (!Dnumber.equals(other.Dnumber))
			return false;
		if (Lname == null) {
			if (other.Lname != null)
				return false;
		} else if (!Lname.equals(other.Lname))
			return false;
		if (locationList == null) {
			if (other.locationList != null)
				return false;
		} else if (!locationList.equals(other.locationList))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DepartmentDocument [Dnumber=" + Dnumber + ", Dname=" + Dname + ", Lname=" + Lname + ", locationList="
				+ locationList + "]";
	}
	
}
