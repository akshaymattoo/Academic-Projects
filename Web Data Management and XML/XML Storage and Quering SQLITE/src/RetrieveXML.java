

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RetrieveXML {
	private static Connection c = null;

	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:mydb.sqlite");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}  
		return c;
	}

	public static void closeConnection(Connection con){
		
		try{
			if(con!=null)
				con.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
public static String getXML(){
		
		
		Connection connection =null;
		Statement statement = null;
		ResultSet rs =null;
		StringBuilder finalXML= new StringBuilder();
		try{
			connection = RetrieveXML.getConnection();
			statement = connection.createStatement();
			String insertDept ="select * from department";
			rs =  statement.executeQuery(insertDept);
			rs.next();
			finalXML.append("<department>");
			finalXML.append("<deptname>");
			finalXML.append(rs.getString("department"));
			finalXML.append("</deptname>");
			
			rs = null;
			String insertGrad ="select * from graduatestudent";
			rs =  statement.executeQuery(insertGrad);
			while(rs.next()){
				finalXML.append("<gradstudent>");
				finalXML.append("<name>");
				finalXML.append("<lastname>");
				finalXML.append(rs.getString("lastname"));
				finalXML.append("</lastname>");
				finalXML.append("<firstname>");
				finalXML.append(rs.getString("firstname"));
				finalXML.append("</firstname>");
				finalXML.append("</name>");
				finalXML.append("<phone>");
				finalXML.append(rs.getInt("phone"));
				finalXML.append("</phone>");
				finalXML.append("<email>");
				finalXML.append(rs.getString("email"));
				finalXML.append("</email>");
				finalXML.append("<address>");
				
				finalXML.append("<city>");
				finalXML.append(rs.getString("city"));
				finalXML.append("</city>");
				
				finalXML.append("<state>");
				finalXML.append(rs.getString("state"));
				finalXML.append("</state>");
				
				finalXML.append("<zip>");
				finalXML.append(rs.getInt("zip"));
				finalXML.append("</zip>");
				
				finalXML.append("</address>");
				
				finalXML.append("<office>");
				finalXML.append(rs.getString("office"));
				finalXML.append("</office>");
				
				finalXML.append("<url>");
				finalXML.append(rs.getString("url"));
				finalXML.append("</url>");
				
				finalXML.append("<gpa>");
				finalXML.append(rs.getDouble("gpa"));
				finalXML.append("</gpa>");
				
				finalXML.append("</gradstudent>");
			}
			
			
			rs = null;
			String insertStaff ="select * from staff";
			rs =  statement.executeQuery(insertStaff);
			while(rs.next()){
				finalXML.append("<staff>");
				finalXML.append("<name>");
				finalXML.append("<lastname>");
				finalXML.append(rs.getString("lastname"));
				finalXML.append("</lastname>");
				finalXML.append("<firstname>");
				finalXML.append(rs.getString("firstname"));
				finalXML.append("</firstname>");
				finalXML.append("</name>");
				finalXML.append("<phone>");
				finalXML.append(rs.getInt("phone"));
				finalXML.append("</phone>");
				finalXML.append("<email>");
				finalXML.append(rs.getString("email"));
				finalXML.append("</email>");
				finalXML.append("<office>");
				finalXML.append(rs.getString("office"));
				finalXML.append("</office>");
				finalXML.append("</staff>");
			}
			
			
			
			rs = null;
			String insertFaculty ="select * from faculty";
			rs =  statement.executeQuery(insertFaculty);
			while(rs.next()){
				finalXML.append("<faculty>");
				finalXML.append("<name>");
				finalXML.append("<lastname>");
				finalXML.append(rs.getString("lastname"));
				finalXML.append("</lastname>");
				finalXML.append("<firstname>");
				finalXML.append(rs.getString("firstname"));
				finalXML.append("</firstname>");
				finalXML.append("</name>");
				finalXML.append("<phone>");
				finalXML.append(rs.getInt("phone"));
				finalXML.append("</phone>");
				finalXML.append("<email>");
				finalXML.append(rs.getString("email"));
				finalXML.append("</email>");
				finalXML.append("<office>");
				finalXML.append(rs.getString("office"));
				finalXML.append("</office>");
				finalXML.append("</faculty>");
			}
			
			rs = null;
			String insertUnderGrad ="select * from undergradstudent";
			rs =  statement.executeQuery(insertUnderGrad);
			while(rs.next()){
				finalXML.append("<undergradstudent>");
				finalXML.append("<name>");
				finalXML.append("<lastname>");
				finalXML.append(rs.getString("lastname"));
				finalXML.append("</lastname>");
				finalXML.append("<firstname>");
				finalXML.append(rs.getString("firstname"));
				finalXML.append("</firstname>");
				finalXML.append("</name>");
				finalXML.append("<phone>");
				finalXML.append(rs.getInt("phone"));
				finalXML.append("</phone>");
				finalXML.append("<email>");
				finalXML.append(rs.getString("email"));
				finalXML.append("</email>");
				finalXML.append("<address>");
				
				finalXML.append("<city>");
				finalXML.append(rs.getString("city"));
				finalXML.append("</city>");
				
				finalXML.append("<state>");
				finalXML.append(rs.getString("state"));
				finalXML.append("</state>");
				
				finalXML.append("<zip>");
				finalXML.append(rs.getInt("zip"));
				finalXML.append("</zip>");
				
				finalXML.append("</address>");
				
				finalXML.append("<gpa>");
				finalXML.append(rs.getDouble("gpa"));
				finalXML.append("</gpa>");
				
				finalXML.append("</undergradstudent>");
			}
			
			finalXML.append("</department>");
		}catch(Exception e ){
			
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(statement!=null)
					statement.close();
				if(connection!=null)
					RetrieveXML.closeConnection(connection);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return finalXML.toString();
	}

	public static void main(String args[]) {
		System.out.println(getXML());
	}
}