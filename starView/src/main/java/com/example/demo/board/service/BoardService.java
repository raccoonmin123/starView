package com.example.demo.board.service;

import com.example.demo.board.dto.Board;
import java.util.List;

public interface BoardService {
 List<Board> getAllBoards();
 Board getBoardById(int postId);
 void addBoard(Board board);
 void updateBoard(Board board);
 void deleteBoard(int postId);
}