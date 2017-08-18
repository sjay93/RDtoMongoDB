package com.db2.RDtoMongo;

import java.util.List;

public class DeptDoc {
	
	private List<DepartmentDocument> departmentDocumentsList ;

	public List<DepartmentDocument> getDepartmentDocumentsList() {
		return departmentDocumentsList;
	}

	public void setDepartmentDocumentsList(List<DepartmentDocument> departmentDocumentsList) {
		this.departmentDocumentsList = departmentDocumentsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentDocumentsList == null) ? 0 : departmentDocumentsList.hashCode());
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
		DeptDoc other = (DeptDoc) obj;
		if (departmentDocumentsList == null) {
			if (other.departmentDocumentsList != null)
				return false;
		} else if (!departmentDocumentsList.equals(other.departmentDocumentsList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptDoc [departmentDocumentsList=" + departmentDocumentsList + "]";
	}
	
	

}
