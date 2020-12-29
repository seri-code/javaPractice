package auth.services.mall.icia;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.AuthBean;

/* Database 연동
 * 1. Oracle Driver loading >> Class.forName("oracle.jdbc.driver.OracleDriver");
 * 2. Connection >> DriverManager.getConnection(Oracle Url, Oracle User, password);
 *    - Oracle Url >> "jdbc:oracle:thin:@106.243.194.230:7007:xe"
 * 3. Statement --> 많은 레코드를 추출(where 절이 없는 경우 유용) 
 * 	  PreparedStatement --> Where절이 있는 경우 유용
 * 4. execute --> query -->  ResultSet  --> Record단위로 Bean에 저장  --> ArrayList
 * 	  executeUpdate --> DML  --> 적용된 레코드 수를 int로 리턴
 * 5. Transaction
 * 6. Connection.close();
 * */
public class DataAccessObject {
	private Connection connection;
	private Statement statement;
	private PreparedStatement pstatement;
	private int updRecords;
	private ResultSet rs;
	
	public DataAccessObject() {
		
		
	}
	
	// 오라클 연결  :: Connection 개체 생성
	public void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//좌측 커넥션 연결이 되어야함
			this.connection = DriverManager.getConnection("jdbc:oracle:thin:@106.243.194.230:7005:xe", "sol", "1234");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	

	
	public void setAutoCommit(boolean isAuto) {
		try {
			this.connection.setAutoCommit(isAuto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Transaction 처리 :: COMMIT  || ROLLBACK
	public void setTransaction(boolean isCommit) {
		try {
			if(isCommit) {
				this.connection.commit();
			}else {
				this.connection.rollback();
			}
		}catch(Exception e) {}
	}
	
	// 오라클 연결 해제 :: Connection.close()
	public void closeConnection() {
		try {
			if(!this.connection.isClosed()) {
				this.connection.close();
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 아이디 유무 확인 + 아이디 중복 체크
	public int isMember(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;
		
		String query="SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID = ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			
			this.rs = this.pstatement.executeQuery();
			while(rs.next()) {
				count = rs.getInt("CNT");
			}
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return count;
	}
	
	// 현재 id 활성화 여부 확인
	public int isActive(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;
		
		String query="SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID=? AND MM_STATE = ?";
		//state값을 안 넣어줘서 오류 발생..
		
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, "A");
			
			this.rs = this.pstatement.executeQuery();
			while(rs.next()) {
				count = rs.getInt("CNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	// userId + userPassword 일치여부
	public int isAccess(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;
		
		String query="SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID=? AND MM_PASSWORD = ?";
	
		
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, auth.getmPassword());
			
			this.rs = this.pstatement.executeQuery();
			while(rs.next()) {
				count = rs.getInt("CNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// ACCESSLOG INS 
	public int insAccessLog(AuthBean auth) {
		this.pstatement = null;
		int count = 0;
		
		String dml="INSERT INTO AL(AL_ID, AL_TIME, AL_TYPE) VALUES(?, DEFAULT, ?)";
	
		
		try {
			this.pstatement = this.connection.prepareStatement(dml);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setInt(2, auth.getAccessType());
			
			count = this.pstatement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 회원정보 추출 :: 회원아이디, 회원이름, 로그인시간
	public ArrayList<AuthBean> searchMemberInfo(AuthBean auth) {
		ArrayList<AuthBean> memberList = new ArrayList<AuthBean>();
		
		this.pstatement = null;
		this.rs = null;
		String query = "SELECT * FROM DBA5.MINFO WHERE MID =? AND ALTYPE=?";
	
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setInt(2, auth.getAccessType());
			
			this.rs= this.pstatement.executeQuery();
			while(rs.next()) {
				AuthBean ab = new AuthBean();
				ab.setmId(rs.getNString("MID"));
				ab.setmName(rs.getNString("MNAME"));
				ab.setAccessTime(rs.getNString("ALTIME"));
				
				memberList.add(ab);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return memberList;
	}

	public int joinMember(AuthBean auth) {
		int count = 0;
		this.pstatement = null;
		
		String dml = "INSERT INTO MM(MM_ID, MM_PASSWORD, MM_NAME, MM_PHONE, MM_STATE) VALUES(?,?,?,?,?)";
		
		try {
			this.pstatement = this.connection.prepareStatement(dml);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, auth.getmPassword());
			this.pstatement.setNString(3, auth.getmName());
			this.pstatement.setNString(4, auth.getPhone());
			this.pstatement.setNString(5, auth.getState());
			
			count = this.pstatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} return count;
		
		
	}
	}
	

