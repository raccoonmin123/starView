package com.example.demo.board.dao;

import com.example.demo.board.dto.Board;
import java.util.List;

public interface BoardDAO {
 List<Board> selectAllBoards();
 Board selectBoardById(int postId);
 int insertBoard(Board board);
 int updateBoard(Board board);
 int deleteBoard(int postId);
 void increaseViewCount(int postId);
}