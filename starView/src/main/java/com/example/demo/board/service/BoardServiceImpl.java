// src/main/java/com/example/demo/board/service/BoardServiceImpl.java
package com.example.demo.board.service;

import com.example.demo.board.dao.BoardDAOImpl;
import com.example.demo.board.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl {

    @Autowired
    private BoardDAOImpl boardDAO;

    @Value("${file.upload.dir}") // application.properties 에 설정할 업로드 디렉토리 경로
    private String uploadDir;

    // 모든 게시글 조회
    public List<Board> getAllBoards() {
        return boardDAO.selectAllBoards();
    }

    // 게시글 ID로 조회
    public Board getBoardById(int postId) {
        return boardDAO.selectBoardById(postId);
    }

    // 게시글 작성 (파일 업로드 로직 포함)
    @Transactional
    public void writeBoard(Board board, MultipartFile file) throws IOException {
        String savedFileName = null;
        String originalFileName = null;

        if (file != null && !file.isEmpty()) {
            originalFileName = file.getOriginalFilename();
            savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // 디렉토리가 없으면 생성
            }

            File destFile = new File(uploadPath, savedFileName);
            try {
                file.transferTo(destFile); // 파일 저장
                board.setFileName(originalFileName);
                board.setFilePath("/upload/" + savedFileName); // ⭐ 웹에서 접근할 수 있는 URL 경로
            } catch (IOException e) {
                throw new IOException("파일 저장에 실패했습니다.", e);
            }
        }

        board.setRegDate(LocalDateTime.now());
        board.setViewCount(0);
        boardDAO.insertBoard(board);
    }

    // 게시글 수정 (파일 업로드 로직 포함)
    @Transactional
    public void updateBoard(Board board, MultipartFile file) throws IOException {
        // 기존 파일 정보 가져오기
        Board existingBoard = boardDAO.selectBoardById(board.getPostId());
        if (existingBoard == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }

        // 기존 파일 정보 유지 (새 파일이 업로드되지 않을 경우 대비)
        board.setFileName(existingBoard.getFileName());
        board.setFilePath(existingBoard.getFilePath());

        if (file != null && !file.isEmpty()) {
            // 기존 파일이 있다면 물리적 삭제
            if (existingBoard.getFilePath() != null) {
                // filePath는 "/upload/uuid_filename.ext" 형식이므로, 실제 파일명만 추출
                String actualFileName = existingBoard.getFilePath().substring("/upload/".length());
                File oldFile = new File(uploadDir, actualFileName);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // 새 파일 저장
            String originalFileName = file.getOriginalFilename();
            String savedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            File destFile = new File(uploadPath, savedFileName);
            try {
                file.transferTo(destFile);
                board.setFileName(originalFileName);
                board.setFilePath("/upload/" + savedFileName);
            } catch (IOException e) {
                throw new IOException("파일 저장에 실패했습니다.", e);
            }
        }
        boardDAO.updateBoard(board);
    }

    // 조회수 증가
    public void incrementViewCount(int postId) {
        boardDAO.incrementViewCount(postId);
    }

    // 게시글 삭제 (파일도 함께 삭제)
    @Transactional
    public void deleteBoard(int postId) {
        // DB에서 게시글 삭제 전에 파일 경로를 가져와서 물리적 파일 삭제
        Board boardToDelete = boardDAO.selectBoardById(postId);
        if (boardToDelete != null && boardToDelete.getFilePath() != null) {
            // filePath는 "/upload/uuid_filename.ext" 형식이므로, 실제 파일명만 추출
            String actualFileName = boardToDelete.getFilePath().substring("/upload/".length());
            File file = new File(uploadDir, actualFileName);
            if (file.exists()) {
                file.delete(); // 실제 파일 삭제
            }
        }
        boardDAO.deleteBoard(postId); // DB에서 게시글 삭제
    }
}