package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAuthorSelectApp {

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
			query += " select  b.book_id, ";
			query += "         b.title, ";
			query += "         b.pubs, ";
			query += "         to_char(b.pub_date, 'yyyy-mm-dd') pubDate, ";
			query += "         a.author_id, ";
			query += "         a.author_name, ";
			query += "         a.author_desc ";
			query += " from book b, author a ";
			query += " where b.author_id = a.author_id ";
			
			pstmt = conn.prepareCall(query);
			rs = pstmt.executeQuery();
				
			// 4.결과처리
			while(rs.next()) {
				int bookId = rs.getInt("book_id");
				String bookTitle = rs.getString("title");
				String bookPubs = rs.getString("pubs");
				String bookDate = rs.getString("pubDate");
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				
				System.out.println(bookId + ", " + bookTitle + ", " + bookPubs + ", " + bookDate + ", " + authorId + ", " + authorName + ", " + authorDesc);
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
