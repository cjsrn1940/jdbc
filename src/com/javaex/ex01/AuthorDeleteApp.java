package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorDeleteApp {

	public static void main(String[] args) {

		/****************
		 자식테이블이 존재할경우 
		 
		 constraint book_fk foreign key(author_id) 
    	 references author(author_id)
    	 on delete cascade
    	 
    	 이런 식으로 on delete cascade를 넣어서 부모테이블을 삭제할시 같이 삭제되도록한다
		 ****************/
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		
		try {
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, 4);
			
			int count = pstmt.executeUpdate();
			
			// 4.결과처리
			if(count>0) {
				System.out.println(count + "건이 삭제되었습니다.");
			} else {
				System.out.println("삭제에 실패했습니다.");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
//				if (rs != null) {
//					rs.close();
//				} 
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				} catch (SQLException e) {
					System.out.println("error:" + e);
			}
		}
	}

}
