package scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SchedularDao {
	//username 저장
	private String username;
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	//dao instance
	private static SchedularDao instance;
	private SchedularDao() {};
	public static SchedularDao getInstance() {
		if(instance==null) {
			instance = new SchedularDao();
		}
		return instance;
	}
	
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "SCHEDULAR";
	private final String PW = "1234";
	
	private Connection getConnection() {
		
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, ID, PW);
			//System.out.println("conn: " + conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}//getConnection
	
	private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//closeAll(3)
	
	private void closeAll(Connection conn, PreparedStatement pstmt) {
			
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}//closeAll(2)
	
	//id 중복확인
	public boolean dup_id(String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select * from table_member"
					+ "			where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt. executeQuery();
			String exist_username = "";
			while(rs.next()) {
				exist_username = rs.getString("username");
			}
			if(exist_username.equals(username)) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn,pstmt, rs);
		}
		return false;
	}
	
	//로그인 진행
		public boolean chk_login(String username, String password) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("password: " + password);
			try {
				conn = getConnection();
				String sql = "select * from table_member"
						+ "			where username = ?"
						+ "			and password = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				rs = pstmt. executeQuery();
				String db_username="";
				String db_password="";
				while(rs.next()) {
					db_username = rs.getString("username");
					db_password = rs.getString("password");
				}
				System.out.println("db_password: " + db_password);
				if(username.equals(db_username) && password.equals(db_password)) {
					return true;
				}
			
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				closeAll(conn,pstmt, rs);
			}
			return false;
		}
	
	//이메일 중복확인
	public boolean dup_email(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select * from table_member"
					+ "		where email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			String exist_email = "";
			while(rs.next()) {
				exist_email = rs.getString("email");
			}
			if(exist_email.equals(email)) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn, pstmt);
		}
		return false;
	}
	
	//회원가입
	public boolean addMember(MemberVo mv) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn=getConnection();
			String sql = "insert into table_member(username,password, email)"
					+ "					values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getUsername());
			pstmt.setString(2, mv.getPassword());
			pstmt.setString(3, mv.getEmail());
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn,pstmt);
		}
		return false;
	}//addMember
	
	//priority no 중복확인
	public boolean dup_pno(String username, int pno, String day) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		
		try {
			conn=getConnection();
			String sql = "select * from table_scheduler"
					+ "			where pno = ? "
					+ "		    and day = ?"
					+ "			and username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			pstmt.setString(2, day);
			pstmt.setString(3,  username);
			rs = pstmt.executeQuery();
			int prev_pno = 0;
			String prev_day = null;
			while(rs.next()) {
				prev_pno = rs.getInt("pno");
				prev_day = rs.getString("day");
				
			}
			if(prev_pno == pno && prev_day.equals(day)) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn, pstmt, rs);
		}
		return false;
	}
	//회원정보 수정
		public boolean updateMemberInfo(MemberVo vo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				String sql = "update table_member set"
						+ "		password = ?,"
						+ "		email = ?"
						+ "		where username = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getPassword());
				pstmt.setString(2, vo.getEmail());
				pstmt.setString(3, vo.getUsername());
				int count = pstmt.executeUpdate();
				System.out.println(count);
				if(count == 1) {
					return true;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				closeAll(conn,pstmt);
			}
			return false;
		}//updateMemberInfo
		
	//회원탈퇴
	public boolean deleteMember(String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "delete from  table_member"
					+ "		where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  username);
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn,pstmt);
		}
		return false;
	}//deleteData
	
	//스케줄 입력
	public boolean addData(SchedulerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "insert into  table_scheduler(day, pno, task, fromTime, toTime, username)"
					+ "					values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  vo.getDay());
			pstmt.setInt(2, vo.getPno());
			pstmt.setString(3, vo.getTask());
			pstmt.setString(4, vo.getFromTime());
			pstmt.setString(5, vo.getToTime());
			pstmt.setString(6, vo.getUsername());
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn, pstmt);
		}
		return false;
	}//addData
	
	//스케줄 불러오기
	public List<SchedulerVo> searchData(String day, String username){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn=getConnection();
			String sql = "select*from  table_scheduler"
					+ "			where day = ? "
					+ "			and username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			pstmt.setString(2,  username);
			rs = pstmt.executeQuery();
			List<SchedulerVo> list = new ArrayList<>();
			while(rs.next()) {
				int pno = rs.getInt("pno");
				String task = rs.getString("task");
				String fromTime = rs.getString("fromTime");
				String toTime = rs.getString("toTime");
				list.add(new SchedulerVo(day, pno, task, fromTime, toTime, username));
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}//searchData
	
	//스케줄 삭제
	public boolean deleteData(String day, int pno, String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "delete from  table_scheduler"
					+ "		where day = ?"
					+ "		and pno = ?"
					+ "		and username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			pstmt.setInt(2, pno);
			pstmt.setString(3,  username);
			int count = pstmt.executeUpdate();
			if(count == 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn,pstmt);
		}
		return false;
	}//deleteData
	
	//스케줄 업데이트
	public boolean updateData(SchedulerVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("vo: " + vo);
		try {
			conn = getConnection();
			String sql = "update table_scheduler set"
					+ "		task = ?,"
					+ "		fromTime = ?,"
					+ "		toTime = ?"
					+ "		where username = ? "
					+ "		and pno = ?"
					+ "		and day = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTask());
			pstmt.setString(2, vo.getFromTime());
			pstmt.setString(3, vo.getToTime());
			pstmt.setString(4, vo.getUsername());
			pstmt.setInt(5, vo.getPno());
			pstmt.setString(6, vo.getDay());
			int count = pstmt.executeUpdate();
			System.out.println(count);
			if(count == 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn,pstmt);
		}
		return false;
	}//updateData
	
	public MemberVo getList(String username){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from table_member"
					+ "			where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
//			List<SchedulerVo> list = new ArrayList<>();
			while(rs.next()) {
				String password = rs.getString("password");
				String email = rs.getString("email");
				Date joindate = rs.getDate("joindate");
				MemberVo vo = new MemberVo(username, password, email, joindate);
				return vo;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeAll(conn, pstmt, rs);
		}
		return null;
	}//getList
}
