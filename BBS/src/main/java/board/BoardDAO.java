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
	
	//생성자
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
	}//생성자 BoardDAO
	
	// 게시물 작성하기
	public int write(String boardTitle, String userId, String boardContent) {
	    String sql = "INSERT INTO \"board\" (boardid, boardtitle, userid, boarddate, boardcontent, boardavailable) "
	            + "VALUES(nextval('seq_board'), ?, ?, ?, ?, ?)";
	    int result = 0;
	    try {
	        pstmt = conn.prepareStatement(sql);
	        // 게시글번호 시퀀스를 nextval('seq_board')로 바로 사용
	        pstmt.setString(1, boardTitle);   // 게시글 제목
	        pstmt.setString(2, userId);       // 게시글 작성자
	        pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
	        	// 현재 시스템 시간을 밀리초 단위로 가져와서 그 값을 이용하여 Timestamp 객체를 생성
	        pstmt.setString(4, boardContent); // 내용
	        pstmt.setInt(5, 1);              // 게시글 게시여부

	        result = pstmt.executeUpdate();
	        System.out.println("BoardDAO : " + result);
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.println("Exception 발생");
	        e.printStackTrace();
	    }
	    return result; // DB 오류
	}

	// 글 목록
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

	//페이징
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
	        return rs.next();  // 다음 페이지가 있으면 true, 없으면 false 반환
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}//nextPage
	
	//게시글 작성자가 삭제하지않은 게시글 count
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
	
	//게시글 읽기
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

	//게시글 수정하기
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
	
	//게시글 삭제하기
	public int delete(int boardId) {
		int cnt = -1;
		//직접적인 삭제보다는 update로 
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
