package com.example.demo.board.dao;

import com.example.demo.board.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository // 스프링 빈으로 등록
public class BoardDAOImpl implements BoardDAO {

 @Autowired
 private JdbcTemplate jdbcTemplate;

 // Board 객체와 ResultSet을 매핑하는 RowMapper
 private RowMapper<Board> boardRowMapper = new RowMapper<Board>() {
     @Override
     public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
         Board board = new Board();
         board.setPostId(rs.getInt("post_id"));
         board.setUserId(rs.getString("userid"));
         board.setUserName(rs.getString("name")); // member 테이블의 name 컬럼을 가져와 매핑
         board.setTitle(rs.getString("title"));
         board.setContent(rs.getString("content"));
         board.setRegDate(rs.getObject("reg_date", LocalDateTime.class));
         board.setModDate(rs.getObject("mod_date", LocalDateTime.class));
         board.setViewCount(rs.getInt("view_count"));
         return board;
     }
 };

 @Override
 public List<Board> selectAllBoards() {
     // member 테이블과 조인하여 작성자 이름을 함께 가져옵니다.
     String sql = "SELECT b.*, m.name FROM board b JOIN member m ON b.userid = m.userid ORDER BY b.post_id DESC";
     return jdbcTemplate.query(sql, boardRowMapper);
 }

 @Override
 public Board selectBoardById(int postId) {
     String sql = "SELECT b.*, m.name FROM board b JOIN member m ON b.userid = m.userid WHERE b.post_id = ?";
     return jdbcTemplate.queryForObject(sql, boardRowMapper, postId);
 }

 @Override
 public int insertBoard(Board board) {
     String sql = "INSERT INTO board (userid, title, content) VALUES (?, ?, ?)";
     return jdbcTemplate.update(sql, board.getUserId(), board.getTitle(), board.getContent());
 }

 @Override
 public int updateBoard(Board board) {
     String sql = "UPDATE board SET title = ?, content = ? WHERE post_id = ?";
     return jdbcTemplate.update(sql, board.getTitle(), board.getContent(), board.getPostId());
 }

 @Override
 public int deleteBoard(int postId) {
     String sql = "DELETE FROM board WHERE post_id = ?";
     return jdbcTemplate.update(sql, postId);
 }

 @Override
 public void increaseViewCount(int postId) {
     String sql = "UPDATE board SET view_count = view_count + 1 WHERE post_id = ?";
     jdbcTemplate.update(sql, postId);
 }
}
