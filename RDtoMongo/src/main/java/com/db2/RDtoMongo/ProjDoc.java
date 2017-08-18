package com.db2.RDtoMongo;

import java.util.List;

public class ProjDoc {
	
	private List<ProjectDocument> projectDocumentList;

	public List<ProjectDocument> getProjectDocumentList() {
		return projectDocumentList;
	}

	public void setProjectDocumentList(List<ProjectDocument> projectDocumentList) {
		this.projectDocumentList = projectDocumentList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectDocumentList == null) ? 0 : projectDocumentList.hashCode());
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
		ProjDoc other = (ProjDoc) obj;
		if (projectDocumentList == null) {
			if (other.projectDocumentList != null)
				return false;
		} else if (!projectDocumentList.equals(other.projectDocumentList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjDoc [projectDocumentList=" + projectDocumentList + "]";
	} 

}
