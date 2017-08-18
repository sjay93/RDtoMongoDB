package com.db2.RDtoMongo;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConvertMain {
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/company";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	
	/**
	 * 
	 * @param list2
	 * @return
	 * @throws UnknownHostException
	 * Method to get project,department,employee details from mySql
	 * and formed complex object for department document
	 */
	private static DeptDoc getRecordsForDepartmentDocumnet() throws SQLException {
		Connection dbConnection = null;
		DeptDoc deptDoc = new DeptDoc();
		List<String> checkList = new ArrayList<String>();
		List<DepartmentDocument> departmentDocumentList = new ArrayList<DepartmentDocument>();
		DepartmentDocument obj2 = null;
		PreparedStatement preparedSelectStatement = null;
		ResultSet resultSetObject = null;

		String previousResult = "SELECT D.Dnumber, D.DNAME, E.LNAME, L.DLOCATION FROM EMPLOYEE E JOIN DEPARTMENT D ON E.DNO = D.DNUMBER AND D.MGR_SSN = E.SSN JOIN DEPT_LOCATIONS L ON L.DNUMBER = D.DNUMBER";

		try {
			dbConnection = getDBConnection();
			preparedSelectStatement = dbConnection.prepareStatement(previousResult);
			resultSetObject = preparedSelectStatement.executeQuery();
			List<Location> locationList = null;
			while (resultSetObject.next()) {
				String dName = resultSetObject.getString("Dname");
				String dNumber = resultSetObject.getString("Dnumber");
				String lName = resultSetObject.getString("LNAME");
				String dLocation = resultSetObject.getString("Dlocation");
				DepartmentDocument documentObj = null;
				if (!checkList.contains(dName)) {
					if (obj2 != null) {
						departmentDocumentList.add(obj2);
						locationList = null;
					}
					checkList.add(dName);
					documentObj = new DepartmentDocument();
					documentObj.setLname(lName);
					documentObj.setDnumber(dNumber);
					documentObj.setDname(dName);
					obj2 = documentObj;
					locationList = new ArrayList<Location>();

				}
				Location locationObject = new Location();
				locationObject.setLname(dLocation);
				locationList.add(locationObject);
				obj2.setLocationList(locationList);
				if (resultSetObject.isLast()) {
					departmentDocumentList.add(obj2);
				}
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedSelectStatement != null) {
				preparedSelectStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

			if (resultSetObject != null) {
				resultSetObject.close();
			}

		}
		deptDoc.setDepartmentDocumentsList(departmentDocumentList);
		return deptDoc;
	}

	/**
	 * 
	 * @param list2
	 * @return
	 * @throws UnknownHostException
	 * Method to get project,department,employee details from mySql
	 * and formed complex object for project document
	 */
	private static ProjDoc getRecordsForProjectDocumnet() throws SQLException {
		Connection dbConnection = null;
		List<String> checkList = new ArrayList<String>();
		ProjDoc projDoc = new ProjDoc();
		List<ProjectDocument> projectDocumentList = new ArrayList<ProjectDocument>();
		ProjectDocument obj1 = null;
		PreparedStatement preparedSelectStatement = null;
		ResultSet resultSetObject = null;

		String previousResult = "Select P.Pnumber, P.Pname, D.Dname, E.Fname, E.LName, W.Hours FROM project P JOIN department D ON P.Dnum = D.DNUMBER JOIN employee E ON P.Dnum=E.DNO JOIN works_on W ON P.PNUMBER = W.PNO AND E.SSN = W.ESSN ";

		try {
			dbConnection = getDBConnection();
			preparedSelectStatement = dbConnection.prepareStatement(previousResult);
			resultSetObject = preparedSelectStatement.executeQuery();
			List<Employee> employeeList = null;
			while (resultSetObject.next()) {
				String pName = resultSetObject.getString("Pname");
				String dName = resultSetObject.getString("Dname");
				String pNumber = resultSetObject.getString("Pnumber");
				String fName = resultSetObject.getString("Fname");
				String lName = resultSetObject.getString("LName");
				String hours = resultSetObject.getString("Hours");
				ProjectDocument documentObj = null;
				if (!checkList.contains(pName)) {
					if (obj1 != null) {
						projectDocumentList.add(obj1);
						employeeList = null;
					}
					checkList.add(pName);
					documentObj = new ProjectDocument();
					documentObj.setPname(pName);
					documentObj.setPnumber(pNumber);
					documentObj.setDname(dName);
					obj1 = documentObj;
					employeeList = new ArrayList<Employee>();

				}
				Employee employeeObject = new Employee();
				employeeObject.setFname(fName);
				employeeObject.setHours(hours);
				employeeObject.setLname(lName);
				employeeList.add(employeeObject);
				obj1.setEmployeeList(employeeList);
				if (resultSetObject.isLast()) {
					projectDocumentList.add(obj1);
				}
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedSelectStatement != null) {
				preparedSelectStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

			if (resultSetObject != null) {
				resultSetObject.close();
			}

		}
		projDoc.setProjectDocumentList(projectDocumentList);
		return projDoc;
	}
	
	/**
	 * 
	 * @param list2
	 * @return
	 * @throws UnknownHostException
	 * Method save the complex project object into MongoDb
	 */
	public static void saveProjecttoMongoDB(ProjDoc list1) throws UnknownHostException {
		MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/RDB");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> coll = db.getCollection("ProjCollection");
		List<ProjectDocument> projectDocuments = list1.getProjectDocumentList();
		Gson gson = new Gson();
		for (ProjectDocument proj : projectDocuments) {
			String jsonInString = gson.toJson(proj);
			//System.out.println("JSON = " + jsonInString);
			Document myDoc = Document.parse(jsonInString);
			// DBObject dbObject = (DBObject)JSON.parse(jsonInString);
			// BasicDBObject dbObject = (BasicDBObject)
			// JSON.parse(jsonInString);
			coll.insertOne(myDoc);

		}
		client.close();
	}
	/**
	 * 
	 * @param list2
	 * @return
	 * @throws UnknownHostException
	 * Method save the complex department object into MongoDb
	 */
	public static void saveDepartmenttoMongoDB(DeptDoc list2)
			throws UnknownHostException {
		MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/RDB");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(uri.getDatabase());

		MongoCollection<Document> coll = db.getCollection("DeptCollection");
		List<DepartmentDocument> departmentDocuments = list2.getDepartmentDocumentsList();
		Gson gson = new Gson();

		for (DepartmentDocument proj : departmentDocuments) {
			String jsonInString = gson.toJson(proj);
			//System.out.println("JSON = " + jsonInString);
			Document myDoc = Document.parse(jsonInString);
			// DBObject dbObject = (DBObject)JSON.parse(jsonInString);
			// BasicDBObject dbObject = (BasicDBObject)
			// JSON.parse(jsonInString);
			coll.insertOne(myDoc);

		}
		client.close();
	}

	/**
	 * Method to convert Project object to XML String.
	 * The output is available in the console.
	 **/
	public static void JsonToXmlProject(ProjDoc projDocument){
		Gson gson = new Gson();
		String json = gson.toJson(projDocument);
		JSONObject jsn = new JSONObject(json);
		String xml = XML.toString(jsn);
		System.out.println(xml);
	}

	/**
	 * Method to convert Department object to XML String.
	 * The output is available in the console.
	 **/
	public static void JsonToXmlDepartment(DeptDoc deptDocument){
		Gson gson = new Gson();
		String json = gson.toJson(deptDocument);
		JSONObject jsn = new JSONObject(json);
		String xml = XML.toString(jsn);
		System.out.println(xml);
	}
	public static void main(String[] args) throws SQLException, UnknownHostException {
		ProjDoc list1 = getRecordsForProjectDocumnet();
		DeptDoc list2 = getRecordsForDepartmentDocumnet();
		JsonToXmlProject(list1);  		// Print the XML object of the project document 
		JsonToXmlDepartment(list2); 	// Print the XML object of the department document 
		saveProjecttoMongoDB(list1);	// Save Complex type project object to MongoDB 
		saveDepartmenttoMongoDB(list2);	// Save complex type department object to MongoDb
	}
}
