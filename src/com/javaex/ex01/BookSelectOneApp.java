package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSelectOneApp {

	public static void main(String[] args) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
				
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
				
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select  book_id, ";
			query += " 		   title, ";
			query += "	  	   pubs, ";
			query += "		   to_char(pub_date, 'yyyy-mm-dd') pubDate, ";
			query += "         author_id ";
			query += " from book ";
			query += " where book_id = ? ";
			
			pstmt = conn.prepareCall(query);
			pstmt.setInt(1,4);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				int bookId = rs.getInt("book_id"); //만약 쿼리문에서 별명을 정해준다면 여기서도 별명으로 검색해야한다. 컬럼명을 검색하기 때문
				String bookTitle = rs.getString("title");
				String bookPubs = rs.getString("pubs");
				String bookDate = rs.getString("pubDate");
				int authoId = rs.getInt("author_id");
				
				System.out.println(bookId + ", " + bookTitle + ", " + bookPubs + ", " + bookDate + ", " + authoId);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			
		// 5. 자원정리
		try {
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
			System.out.println("error:" + e);
			}
		}
	}

}
