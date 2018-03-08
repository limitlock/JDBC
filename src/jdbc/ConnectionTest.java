package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

	public static void main(String[] args) {
		Connection conn = null;
		
		
		try {
			// 1. 드라이버 로딩
			// 동적으로 mysql.jdbc.driver가 생성된다. DBMS마다 클래스명이 다르다.
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.cafe24.jdbc.MyDriver");
			
			// 2. 연결하기
			String url = "jdbc:mysql://localhost/dev";
			// DriverManager가 mysql.driver 라이브러리에 주소,이름,비밀번호를 넘겨주고
			// 값을 넘겨받은 mysql.driver가 mysql에 접속 후 적절한 반환값(interface)을 넘겨준다.
			conn = DriverManager.getConnection(url, "dev", "dev");
			
			
			System.out.println("성공");
			

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패" + e);
		} catch (SQLException e) {
			System.out.println("에러" + e);
		} finally {
			try {
				if (conn != null && conn.isClosed() == false) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
