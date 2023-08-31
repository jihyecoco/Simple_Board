package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//������
	public BoardDAO() {
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
	}//������ BoardDAO
	
	// �Խù� �ۼ��ϱ�
	public int write(String boardTitle, String userId, String boardContent) {
	    String sql = "INSERT INTO \"board\" (boardid, boardtitle, userid, boarddate, boardcontent, boardavailable) "
	            + "VALUES(nextval('seq_board'), ?, ?, ?, ?, ?)";
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        // �Խñ۹�ȣ �������� nextval('seq_board')�� �ٷ� ���
	        pstmt.setString(1, boardTitle);   // �Խñ� ����
	        pstmt.setString(2, userId);       // �Խñ� �ۼ���
	        pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
	        	// ���� �ý��� �ð��� �и��� ������ �����ͼ� �� ���� �̿��Ͽ� Timestamp ��ü�� ����
	        pstmt.setString(4, boardContent); // ����
	        pstmt.setInt(5, 1);              // �Խñ� �Խÿ���

	        result = pstmt.executeUpdate();
	        System.out.println("BoardDAO : " + result);
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.println("Exception �߻�");
	        e.printStackTrace();
	    }
	    return result; // DB ����
	}

	// �� ���
	public ArrayList<Board> getList(int pageNumber) {
	    int pageSize = 5;
	    int offset = (pageNumber - 1) * pageSize;
	    
	    System.out.println("pageSize : " + pageSize);
	    System.out.println("offset : " + offset);
	    
	    ArrayList<Board> list = new ArrayList<Board>();

	    String sql = "SELECT boardid, boardtitle, userid, boarddate, boardcontent, boardavailable "
	            + "FROM \"board\" "
	            + "WHERE boardavailable = 1 "
	            + "ORDER BY boardid DESC "
	            + "LIMIT ? OFFSET ?";

	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, pageSize);
	        pstmt.setInt(2, offset);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            Board board = new Board();
	            int boardId = rs.getInt("boardid");
	            String boardTitle = rs.getString("boardtitle");
	            String userId = rs.getString("userid");
	            String boardDate = rs.getString("boarddate");
	            String boardContent = rs.getString("boardcontent");
	            int boardAvailable = rs.getInt("boardavailable");

	            board.setBoardId(boardId);
	            board.setBoardTitle(boardTitle);
	            board.setUserId(userId);
	            board.setBoardDate(boardDate);
	            board.setBoardContent(boardContent);
	            board.setBoardAvailable(boardAvailable);
	            list.add(board);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return list;
	}//getList

	//����¡
	public boolean nextPage(int pageNumber) {
	    int pageSize = 5;
	    int offset = (pageNumber - 1) * pageSize;
	    
	    String sql = "SELECT boardid "
	                + "FROM \"board\" "
	                + "WHERE boardavailable = 1 "
	                + "ORDER BY boardid DESC "
	                + "LIMIT ? OFFSET ?";
	    
	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, pageSize);
	        pstmt.setInt(2, offset);
	        rs = pstmt.executeQuery();
	        return rs.next();  // ���� �������� ������ true, ������ false ��ȯ
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}//nextPage
	
	//�Խñ� �ۼ��ڰ� ������������ �Խñ� count
	public int totalBoardCount() {
		int totalBoardCount = 0;
		
		String sql = "SELECT count(boardid) FROM \"board\" WHERE boardavailable=1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				totalBoardCount = rs.getInt(++totalBoardCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("totalBoardCount : " + totalBoardCount);
		return totalBoardCount;
	}//totalBoardCount
	
	//�Խñ� �б�
	public Board getBoard(int param_boardId) {
	    String sql = "SELECT boardid, boardtitle, userid, boarddate, boardcontent, boardavailable "
	    			+ "FROM \"board\" "
	    			+ "WHERE boardid = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, param_boardId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
	            int boardId = rs.getInt("boardid");
	            String boardTitle = rs.getString("boardtitle");
	            String userId = rs.getString("userid");
	            String boardDate = rs.getString("boarddate");
	            String boardContent = rs.getString("boardcontent");
	            int boardAvailable = rs.getInt("boardavailable");

	            board.setBoardId(boardId);
	            board.setBoardTitle(boardTitle);
	            board.setUserId(userId);
	            board.setBoardDate(boardDate);
	            board.setBoardContent(boardContent);
	            board.setBoardAvailable(boardAvailable);
	            return board;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}//getBoard

	//�Խñ� �����ϱ�
	public int update (int boardId, String boardTitle, String boardContent)  {
		int cnt = -1;
		String sql = "UPDATE \"board\" SET boardavailable=? , boardcontent =? WHERE boardid = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setInt(3, boardId);
			cnt = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}//update
	
	//�Խñ� �����ϱ�
	public int delete(int boardId) {
		int cnt = -1;
		//�������� �������ٴ� update�� 
		//String sql = "DELETE FROM \"board\" WHERE boardid = ? ";
		String sql = "UPDATE \"board\" SET boardavailable=0 WHERE boardid = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			cnt = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}//delete
	
	
}//class BoardDAO
