// src/main/java/com/example/demo/board/controller/BoardController.java
package com.example.demo.board.controller;

import com.example.demo.board.dto.Board;
import com.example.demo.board.service.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardServiceImpl boardService;

    // 게시글 목록 (초기 페이지)
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = boardService.getAllBoards();
        model.addAttribute("boardList", boardList);
        return "board/boardList"; // ⭐ views/board/boardList.jsp 를 찾아감
    }

    // 게시글 작성 폼 표시
    @GetMapping("/writeForm")
    public String writeForm() {
        return "board/boardWrite"; // ⭐ views/board/boardWrite.jsp 를 찾아감
    }

    // 게시글 작성 처리 (파일 업로드 포함)
    @PostMapping("/write")
    public String write(@RequestParam("title") String title,
                        @RequestParam("content") String content,
                        @RequestParam(value = "file", required = false) MultipartFile file,
                        RedirectAttributes rttr) {

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUserId("guest");       // 로그인 기능 없으므로 임시 사용자 ID
        board.setUserName("익명 사용자"); // 로그인 기능 없으므로 임시 사용자 이름

        try {
            boardService.writeBoard(board, file);
            rttr.addFlashAttribute("msg", "게시글이 성공적으로 작성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msg", "게시글 작성 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/board/writeForm";
        }

        return "redirect:/board/list";
    }

    // 게시글 상세 보기 (파일 정보도 함께 전달)
    @GetMapping("/view")
    public String view(@RequestParam("postId") int postId, Model model) {
        Board board = boardService.getBoardById(postId);
        if (board == null) {
            model.addAttribute("msg", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/board/list";
        }
        boardService.incrementViewCount(postId); // 조회수 증가
        model.addAttribute("board", board);
        return "board/boardView"; // ⭐ views/board/boardView.jsp 를 찾아감
    }

    // 게시글 수정 폼
    @GetMapping("/editForm")
    public String editForm(@RequestParam("postId") int postId, Model model) {
        Board board = boardService.getBoardById(postId);
        if (board == null) {
            return "redirect:/board/list";
        }
        model.addAttribute("board", board);
        return "board/boardEdit"; // ⭐ views/board/boardEdit.jsp 를 찾아감
    }

    // 게시글 수정 처리 (파일 업로드 기능 포함)
    @PostMapping("/edit")
    public String edit(@RequestParam("postId") int postId,
                       @RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @RequestParam(value = "file", required = false) MultipartFile file,
                       RedirectAttributes rttr) {

        Board existingBoard = boardService.getBoardById(postId);
        if (existingBoard == null) {
            rttr.addFlashAttribute("msg", "수정할 게시글을 찾을 수 없습니다.");
            return "redirect:/board/list";
        }

        existingBoard.setTitle(title);
        existingBoard.setContent(content);

        try {
            boardService.updateBoard(existingBoard, file);
            rttr.addFlashAttribute("msg", "게시글이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msg", "게시글 수정 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/board/editForm?postId=" + postId;
        }

        return "redirect:/board/view?postId=" + postId;
    }

    // 게시글 삭제 처리
    @GetMapping("/delete")
    public String delete(@RequestParam("postId") int postId, RedirectAttributes rttr) {
        try {
            boardService.deleteBoard(postId);
            rttr.addFlashAttribute("msg", "게시글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msg", "게시글 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/board/list";
    }
}