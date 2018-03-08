package com.cafe24.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {

	public static void main(String[] args) {

		insert("성탄이", "최종규", "개", "M", "2015-05-01", null);

	}

	public static void insert(String name, String owner, String species, String gender, String birth, String death) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2.연결하기
			String url = "jdbc:mysql://localhost/dev";
			conn = DriverManager.getConnection(url, "dev", "dev");

			// 3. SQL문 준비
			// ? 파라미터
			String sql = "insert into pet values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			// 4. 데이터바인딩(binding)
			pstmt.setString(1, name);
			pstmt.setString(2, owner);
			pstmt.setString(3, species);
			pstmt.setString(4, gender);
			pstmt.setString(5, birth);
			pstmt.setString(6, death);

			// 5. SQL문 실행
			int count = pstmt.executeUpdate();
			
			// 6. 결과 확인
			if(count == 0) {
				System.out.println("실패");
			} else {
				System.out.println("성공");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패" + e);
		} catch (SQLException e) {
			System.out.println("에러" + e);
		} finally {
			try {
				// 자원정리(Clean-Up)
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null && conn.isClosed() == false) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
