package com.cafe24.hr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.hr.vo.EmployeesVo;

public class EmployeesDao {
	private static final int LIST_COUNT = 100;

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2.연결하기
			String url = "jdbc:mysql://localhost/employees";
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 " + e);
		}
		return conn;
	}

	public EmployeesVo get(Long no) {

		EmployeesVo vo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // Query문 결과
		try {
			vo = new EmployeesVo();
			conn = getConnection();

			// 3. SQL문 준비
			String sql = "select emp_no, date_format(birth_date, '%Y-%m-%d'), first_name, last_name, gender, date_format(hire_date, '%Y-%m-%d') from employees where emp_no = ?;";
			pstmt = conn.prepareStatement(sql);

			// 4. 데이터바인딩(binding)
			pstmt.setLong(1, no);

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 결과 확인

			while (rs.next()) {
				Long empNo = rs.getLong(1);
				String birthDate = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String gender = rs.getString(5);
				String hireDate = rs.getString(6);

				vo.setEmpNo(empNo);
				vo.setBirthDate(birthDate);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setGender(gender);
				vo.setHireDate(hireDate);

			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
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
		return vo;
	}

	public boolean update(EmployeesVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = getConnection();

			// 3. SQL문 준비
			String sql = "update employees" + "   set birth_date = ?," + "	   first_name = ?,"
					+ "       last_name  = ?," + "       gender = ?," + "       hire_date = ?" + "where emp_no = ?";
			pstmt = conn.prepareStatement(sql);

			// 4. 데이터바인딩(binding)
			pstmt.setString(1, vo.getBirthDate());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getLastName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getHireDate());
			pstmt.setLong(6, vo.getEmpNo());

			// 5. SQL문 실행
			int count = pstmt.executeUpdate();

			// 6. 결과 확인
			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
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

		return result;
	}

	public List<EmployeesVo> getList(int page) {
		List<EmployeesVo> list = new ArrayList<EmployeesVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // Query문 결과
		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "select emp_no, date_format(birth_date, '%Y-%m-%d'), first_name, last_name, gender, date_format(hire_date, '%Y-%m-%d') from employees order by hire_date limit ?, ?";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setInt(1, (page - 1) * LIST_COUNT);
			pstmt.setInt(2, LIST_COUNT);

			// 5. 결과처리
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long empNo = rs.getLong(1);
				String birthDate = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String gender = rs.getString(5);
				String hireDate = rs.getString(6);

				EmployeesVo vo = new EmployeesVo();
				vo.setEmpNo(empNo);
				vo.setBirthDate(birthDate);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setGender(gender);
				vo.setHireDate(hireDate);

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				// 자원정리(Clean-Up)
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
