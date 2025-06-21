// src/main/java/com/example/demo/board/dao/BoardDAOImpl.java
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

@Repository
public class BoardDAOImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 게시글 삽입 (파일 정보 포함)
    public void insertBoard(Board board) {
        String sql = "INSERT INTO board (title, content, user_id, user_name, reg_date, view_count, file_name, file_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                board.getTitle(),
                board.getContent(),
                board.getUserId(),
                board.getUserName(),
                board.getRegDate(),
                board.getViewCount(),
                board.getFileName(),
                board.getFilePath()
        );
    }

    // 모든 게시글 조회 (파일 정보 포함)
    public List<Board> selectAllBoards() {
        String sql = "SELECT post_id, title, content, user_id, user_name, reg_date, view_count, file_name, file_path FROM board ORDER BY post_id DESC";
        return jdbcTemplate.query(sql, new BoardRowMapper());
    }

    // 게시글 ID로 조회 (파일 정보 포함)
    public Board selectBoardById(int postId) {
        String sql = "SELECT post_id, title, content, user_id, user_name, reg_date, view_count, file_name, file_path FROM board WHERE post_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BoardRowMapper(), postId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 게시글 업데이트 (파일 정보 포함)
    public void updateBoard(Board board) {
        String sql = "UPDATE board SET title = ?, content = ?, file_name = ?, file_path = ? WHERE post_id = ?";
        jdbcTemplate.update(sql,
                board.getTitle(),
                board.getContent(),
                board.getFileName(),
                board.getFilePath(),
                board.getPostId()
        );
    }

    // 조회수 증가
    public void incrementViewCount(int postId) {
        String sql = "UPDATE board SET view_count = view_count + 1 WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    // 게시글 삭제
    public void deleteBoard(int postId) {
        String sql = "DELETE FROM board WHERE post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    // Board DTO에 매핑하는 RowMapper
    private static class BoardRowMapper implements RowMapper<Board> {
        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            Board board = new Board();
            board.setPostId(rs.getInt("post_id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setUserId(rs.getString("user_id"));
            board.setUserName(rs.getString("user_name"));
            board.setRegDate(rs.getTimestamp("reg_date").toLocalDateTime());
            board.setViewCount(rs.getInt("view_count"));
            board.setFileName(rs.getString("file_name"));
            board.setFilePath(rs.getString("file_path"));
            return board;
        }
    }
}