package com.cafe24.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // Query문 결과
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2.연결하기
			String url = "jdbc:mysql://localhost/dev";
			conn = DriverManager.getConnection(url, "dev", "dev");

			// 3. Statement 객체 생성
			stmt = conn.createStatement();

			// 4. SQL 실행
			String sql = "select name,owner,species, date_format(birth, '%Y-%m-%d') from pet;";
			// 보통 밑에 2가지를 사용한다.
			rs = stmt.executeQuery(sql); // select
			// stmt.executeUpdate(sql); // insert, update, delete

			while (rs.next()) {
				String name = rs.getString(1);
				String owner = rs.getString(2);
				String species = rs.getString(3);
				String date = rs.getString(4);

				System.out.println(name + ":" + owner + ":" + species + ":" + date);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패" + e);
		} catch (SQLException e) {
			System.out.println("에러" + e);
		} finally {
			try {
				// 자원정리(Clean-Up)
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
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
