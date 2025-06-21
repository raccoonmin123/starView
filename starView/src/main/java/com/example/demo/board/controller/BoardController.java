package com.example.demo.board.controller;

import com.example.demo.board.dto.Board;
import com.example.demo.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession; // 세션에서 사용자 ID를 가져오기 위함

@Controller // 스프링 MVC 컨트롤러로 등록
@RequestMapping("/board") // 모든 메서드에 /board 경로가 기본으로 적용
public class BoardController {

 @Autowired
 private BoardService boardService;

 // 게시글 목록 보기
 @GetMapping("/list")
 public String boardList(Model model) {
     model.addAttribute("boardList", boardService.getAllBoards());
     return "boardList"; // boardList.jsp를 찾아감
 }

 // 게시글 작성 폼 보기
 @GetMapping("/writeForm")
 public String writeForm() {
     // 로그인 여부 확인 및 권한 체크 로직 추가 필요
     return "boardWrite"; // boardWrite.jsp를 찾아감
 }

 // 게시글 작성 처리
 @PostMapping("/write")
 public String writeBoard(@ModelAttribute Board board, HttpSession session, RedirectAttributes rttr) {
     // 실제 구현 시, 로그인된 사용자의 ID를 세션에서 가져와 board 객체에 설정
     String loggedInUserId = (String) session.getAttribute("loggedInUserId"); // 예시: 로그인 시 세션에 저장한 ID
     if (loggedInUserId == null) {
         rttr.addFlashAttribute("msg", "로그인 후 이용 가능합니다.");
         return "redirect:/member/loginForm"; // 로그인 페이지로 리다이렉트
     }
     board.setUserId(loggedInUserId);

     boardService.addBoard(board);
     rttr.addFlashAttribute("msg", "게시글이 성공적으로 작성되었습니다.");
     return "redirect:/board/list"; // 목록 페이지로 리다이렉트
 }

 // 게시글 상세 보기
 @GetMapping("/view")
 public String viewBoard(@RequestParam("postId") int postId, Model model) {
     Board board = boardService.getBoardById(postId);
     model.addAttribute("board", board);
     return "boardView"; // boardView.jsp를 찾아감
 }

 // 게시글 수정 폼 보기 (숙제: 직접 구현해보세요!)
 @GetMapping("/editForm")
 public String editForm(@RequestParam("postId") int postId, Model model, HttpSession session, RedirectAttributes rttr) {
     // 1. 게시글 정보 가져오기
     Board board = boardService.getBoardById(postId);
     String loggedInUserId = (String) session.getAttribute("loggedInUserId");

     // 2. 권한 확인 (작성자 본인인지)
     if (loggedInUserId == null || !loggedInUserId.equals(board.getUserId())) {
         rttr.addFlashAttribute("msg", "수정 권한이 없습니다.");
         return "redirect:/board/view?postId=" + postId;
     }

     model.addAttribute("board", board);
     return "boardEdit"; // boardEdit.jsp를 찾아감
 }

 // 게시글 수정 처리 (숙제: 직접 구현해보세요!)
 @PostMapping("/edit")
 public String updateBoard(@ModelAttribute Board board, HttpSession session, RedirectAttributes rttr) {
     // 1. 로그인된 사용자 ID 가져오기 (권한 확인용)
     String loggedInUserId = (String) session.getAttribute("loggedInUserId");
     if (loggedInUserId == null) {
         rttr.addFlashAttribute("msg", "로그인 후 이용 가능합니다.");
         return "redirect:/member/loginForm";
     }

     // 2. 실제 DB에서 게시글 정보 다시 조회하여 작성자 ID 일치 여부 확인
     Board existingBoard = boardService.getBoardById(board.getPostId());
     if (!loggedInUserId.equals(existingBoard.getUserId())) {
         rttr.addFlashAttribute("msg", "수정 권한이 없습니다.");
         return "redirect:/board/view?postId=" + board.getPostId();
     }

     // 3. 게시글 수정
     boardService.updateBoard(board);
     rttr.addFlashAttribute("msg", "게시글이 성공적으로 수정되었습니다.");
     return "redirect:/board/view?postId=" + board.getPostId();
 }

 // 게시글 삭제 처리 (숙제: 직접 구현해보세요!)
 @GetMapping("/delete") // GET 요청보다는 POST나 DELETE 방식 권장
 public String deleteBoard(@RequestParam("postId") int postId, HttpSession session, RedirectAttributes rttr) {
     // 1. 로그인된 사용자 ID 가져오기 (권한 확인용)
     String loggedInUserId = (String) session.getAttribute("loggedInUserId");
     if (loggedInUserId == null) {
         rttr.addFlashAttribute("msg", "로그인 후 이용 가능합니다.");
         return "redirect:/member/loginForm";
     }

     // 2. 실제 DB에서 게시글 정보 다시 조회하여 작성자 ID 일치 여부 확인
     Board existingBoard = boardService.getBoardById(postId);
     if (!loggedInUserId.equals(existingBoard.getUserId())) {
         rttr.addFlashAttribute("msg", "삭제 권한이 없습니다.");
         return "redirect:/board/view?postId=" + postId;
     }

     // 3. 게시글 삭제
     boardService.deleteBoard(postId);
     rttr.addFlashAttribute("msg", "게시글이 성공적으로 삭제되었습니다.");
     return "redirect:/board/list";
 }
}