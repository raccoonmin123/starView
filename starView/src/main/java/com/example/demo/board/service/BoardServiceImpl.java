package com.example.demo.board.service;

import com.example.demo.board.dao.BoardDAO;
import com.example.demo.board.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위해

import java.util.List;

@Service // 스프링 빈으로 등록
public class BoardServiceImpl implements BoardService {

 @Autowired
 private BoardDAO boardDAO;

 @Override
 public List<Board> getAllBoards() {
     return boardDAO.selectAllBoards();
 }

 @Override
 @Transactional // 메서드 실행 중 예외 발생 시 롤백 (예: 조회수 증가 후 상세 조회)
 public Board getBoardById(int postId) {
     boardDAO.increaseViewCount(postId); // 조회수 증가
     return boardDAO.selectBoardById(postId); // 게시글 정보 가져오기
 }

 @Override
 public void addBoard(Board board) {
     boardDAO.insertBoard(board);
 }

 @Override
 public void updateBoard(Board board) {
     boardDAO.updateBoard(board);
 }

 @Override
 public void deleteBoard(int postId) {
     boardDAO.deleteBoard(postId);
 }
}