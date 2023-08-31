package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//생성자
	public UserDAO() {
	    try {
	        String dbURL = "jdbc:postgresql://127.0.0.1:5432/postgres";
	        String dbId = "postgres";
	        String dbPassword = "1231";
	        Class.forName("org.postgresql.Driver"); 
	        conn = DriverManager.getConnection(dbURL, dbId, dbPassword);
	        
	        if (conn != null) {
	            System.out.println("Connection successful!!");
	        } else {
	        	System.out.println("Connection fail!!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}//생성자 UserDAO
	
	//로그인시도하는 함수
	public int login(String userId, String userPassword) {
		//String sql = "SELECT userpassword FROM user WHERE userid = ?";
		String sql = "SELECT userpassword FROM \"user\" WHERE userid = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//입력한 아이디가 있는 경우
				if(rs.getString(1).equals(userPassword)) {
					//로그인 성공
					return 1;
				}else {
					//아이디는 존재, 비밀번호 불일치
					return 0;
				}
			}
			//입력한 아이디가 없는 경우
			return -1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		//DB오류
		return -2;
	}//login
	
	//회원가입
	public int join(User user) {
		String sql = "INSERT INTO \"user\" values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
			//정상적으로 INSERT되면 -1보다 큰 값이 INSERT된다.
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}//join
	
	
}//class UserDAO
