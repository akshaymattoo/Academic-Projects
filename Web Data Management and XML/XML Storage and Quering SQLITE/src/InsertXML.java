

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class InsertXML extends DefaultHandler {

	boolean gradFlag = false;
	boolean staffFlag = false;
	boolean facultyFlag = false;
	boolean undergradFlag = false;

	String reading;

	Department department;
	List<Gradstudent> gradStudentList;
	Gradstudent gradStudent;

	List<Staff> staffList;
	Staff staff;

	List<Faculty> facultyList;
	Faculty faculty;

	List<UnderGradstudent> underGradList;
	UnderGradstudent underGradStudent;

	Name name;
	Address address;

	public InsertXML(String file) {
		super();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(false);
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			xmlReader.setContentHandler(this);
			xmlReader.parse(file);
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public void startElement(String uri, String text, String tag,
			Attributes atts) {

		if (tag.equals("department")) {
			department = new Department();
			gradStudentList = new ArrayList<Gradstudent>();
			staffList = new ArrayList<Staff>();
			facultyList = new ArrayList<Faculty>();
			underGradList = new ArrayList<UnderGradstudent>();
		}

		if (tag.equals("gradstudent")) {
			gradFlag = true;
			gradStudent = new Gradstudent();
		}

		if (tag.equals("name")) {
			name = new Name();
		}

		if (tag.equals("address")) {
			address = new Address();
		}

		if (tag.equals("staff")) {
			staffFlag = true;
			staff = new Staff();
		}

		if (tag.equals("faculty")) {
			facultyFlag = true;
			faculty = new Faculty();
		}

		if (tag.equals("undergradstudent")) {
			undergradFlag = true;
			underGradStudent = new UnderGradstudent();
		}
	}

	public void endElement(String uri, String text, String tag) {

		// in the common flags like phone ,email etc use flags ..

		if (tag.equals("department")) {
			department.setGradStudent(gradStudentList);
			department.setStaffList(staffList);
			department.setFacultyList(facultyList);
			department.setUnderGradstudent(underGradList);
		}

		if (tag.equals("deptname")) {
			department.setDepartmentName(reading);
		}

		if (tag.equals("gradstudent")) {

			gradFlag = false;
			gradStudentList.add(gradStudent);
		}

		if (tag.equals("name")) {
			if (gradFlag == true)
				gradStudent.setName(name);
			if (staffFlag == true)
				staff.setName(name);
			if (facultyFlag == true)
				faculty.setName(name);
			if (undergradFlag == true)
				underGradStudent.setName(name);
		}

		if (tag.equals("lastname")) {
			name.setLastname(reading);
		}

		if (tag.equals("firstname")) {
			name.setFirstname(reading);
		}

		if (tag.equals("phone")) {
			if (gradFlag == true)
				gradStudent.setPhone(Integer.parseInt(reading));
			if (staffFlag == true)
				staff.setPhone(Integer.parseInt(reading));
			if (facultyFlag == true)
				faculty.setPhone(Integer.parseInt(reading));
			if (undergradFlag == true)
				underGradStudent.setPhone(Integer.parseInt(reading));
		}

		if (tag.equals("email")) {
			if (gradFlag == true)
				gradStudent.setEmail(reading);
			if (staffFlag == true)
				staff.setEmail(reading);
			if (facultyFlag == true)
				faculty.setEmail(reading);
			if (undergradFlag == true)
				underGradStudent.setEmail(reading);
		}

		if (tag.equals("address")) {
			if (gradFlag == true)
				gradStudent.setAddress(address);
			if (undergradFlag == true)
				underGradStudent.setAddress(address);
		}

		if (tag.equals("city")) {
			address.setCity(reading);
		}

		if (tag.equals("state")) {

			address.setState(reading);
		}

		if (tag.equals("zip")) {
			address.setZip(Integer.parseInt(reading));
		}

		if (tag.equals("office")) {
			if (gradFlag == true)
				gradStudent.setOffice(Integer.parseInt(reading));
			if (staffFlag == true)
				staff.setOffice(Integer.parseInt(reading));
			if (facultyFlag == true)
				faculty.setOffice(Integer.parseInt(reading));

		}

		if (tag.equals("url")) {
			gradStudent.setUrl(reading);
		}

		if (tag.equals("gpa")) {
			if (gradFlag == true)
				gradStudent.setGpa(Double.parseDouble(reading));
			if (undergradFlag == true)
				underGradStudent.setGpa(Double.parseDouble(reading));

		}

		if (tag.equals("staff")) {
			staffFlag = false;
			staffList.add(staff);
		}

		if (tag.equals("faculty")) {
			facultyFlag = false;
			facultyList.add(faculty);
		}

		if (tag.equals("undergradstudent")) {
			undergradFlag = false;
			underGradList.add(underGradStudent);
		}

	}

	public void characters(char text[], int start, int length) {
		reading = new String(text, start, length);
	}

	public static void main(String[] args) {
		InsertXML sxl = new InsertXML("cs.xml");
		Department dept = sxl.department;

		insertXML(dept);
	}

	
	
	public static void insertXML(Department department) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs =null;
		try {
			connection = RetrieveXML.getConnection();
			statement = connection.createStatement();

			String deptQuery = "insert into department values(null,'"
					+ department.getDepartmentName() + "')";
			//statement.addBatch(deptQuery);
			statement.executeUpdate(deptQuery);
			

			String query  = "select * from department";
			rs =  statement.executeQuery(query);
			rs.next();
			int deptId = rs.getInt("deptid");
			
			
			for (Gradstudent gs : department.getGradStudent()) {
				String insertGradStudent = "insert into graduatestudent values(null,"+deptId+",'"
						+ gs.getName().getFirstname()
						+ "','"
						+ gs.getName().getLastname()
						+ "',"
						+ gs.getPhone()
						+ ",'"
						+ gs.getEmail()
						+ "','"
						+ gs.getAddress().getCity()
						+ "','"
						+ gs.getAddress().getState()
						+ "',"
						+ gs.getAddress().getZip()
						+ ","
						+ gs.getOffice()
						+ ",'" + gs.getUrl() + "'," + gs.getGpa() + ")";
				statement.addBatch(insertGradStudent);
			}


			for (Staff st : department.getStaffList()) {
				String insertStaff = "insert into staff values(null,"+deptId+",'"
						+ st.getName().getFirstname() + "','"
						+ st.getName().getLastname() + "'," + st.getPhone()
						+ ",'" + st.getEmail() + "'," + st.getOffice() + ")";
				statement.addBatch(insertStaff);
			}


			for (Faculty fac : department.getFacultyList()) {
				String insertFaculty = "insert into faculty values(null,"+deptId+",'"
						+ fac.getName().getFirstname() + "','"
						+ fac.getName().getLastname() + "'," + fac.getPhone()
						+ ",'" + fac.getEmail() + "'," + fac.getOffice() + ")";
				statement.addBatch(insertFaculty);
			}


			for (UnderGradstudent ugd : department.getUnderGradstudent()) {
				String insertUndergrad = "insert into undergradstudent  values(null,"+deptId+",'"
						+ ugd.getName().getFirstname()
						+ "','"
						+ ugd.getName().getLastname()
						+ "',"
						+ ugd.getPhone()
						+ ",'"
						+ ugd.getEmail()
						+ "','"
						+ ugd.getAddress().getCity()
						+ "','"
						+ ugd.getAddress().getState()
						+ "',"
						+ ugd.getAddress().getZip() + "," + ugd.getGpa() + ")";
				statement.addBatch(insertUndergrad);
			}
			statement.executeBatch();
			System.out.println("insert successful");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)
					rs.close();
				if(statement!=null)
					statement.close();
				RetrieveXML.closeConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}



class Department{
	
private String departmentName;
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}
public List<Gradstudent> getGradStudent() {
	return gradStudent;
}
public void setGradStudent(List<Gradstudent> gradStudent) {
	this.gradStudent = gradStudent;
}
public List<Staff> getStaffList() {
	return staffList;
}
public void setStaffList(List<Staff> staffList) {
	this.staffList = staffList;
}
public List<Faculty> getFacultyList() {
	return facultyList;
}
public void setFacultyList(List<Faculty> facultyList) {
	this.facultyList = facultyList;
}
public List<UnderGradstudent> getUnderGradstudent() {
	return UnderGradstudent;
}
public void setUnderGradstudent(List<UnderGradstudent> underGradstudent) {
	UnderGradstudent = underGradstudent;
}
private List<Gradstudent> gradStudent;
private List<Staff> staffList;
private List<Faculty> facultyList;
private List<UnderGradstudent> UnderGradstudent;

}


class Faculty{
	
private Name name;
public Name getName() {
	return name;
}
public void setName(Name name) {
	this.name = name;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getOffice() {
	return office;
}
public void setOffice(int office) {
	this.office = office;
}
private int phone;
private String email;
private int office;

}


class Gradstudent{
	
private Name name;
public Name getName() {
	return name;
}
public void setName(Name name) {
	this.name = name;
}
public Address getAddress() {
	return address;
}
public void setAddress(Address address) {
	this.address = address;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getOffice() {
	return office;
}
public void setOffice(int office) {
	this.office = office;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
 
private Address address;
private int phone;
private String email;
private int office;
private String url;
private double gpa;
public double getGpa() {
	return gpa;
}
public void setGpa(double gpa) {
	this.gpa = gpa;
}


}

class Name{
	
	private String lastname;
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	private String firstname;

}

class Address{
	
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	private String state;
	private int zip;

}


class Staff{
	
private Name name;
public Name getName() {
	return name;
}
public void setName(Name name) {
	this.name = name;
}
public int getPhone() {
	return phone;
}
public void setPhone(int phone) {
	this.phone = phone;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getOffice() {
	return office;
}
public void setOffice(int office) {
	this.office = office;
}
private int phone;
private String email;
private int office;

}



class UnderGradstudent{
	
public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
private Name name;
private Address address;
private int phone;
private String email;
private double gpa;
public double getGpa() {
	return gpa;
}
public void setGpa(double gpa) {
	this.gpa = gpa;
}


}
