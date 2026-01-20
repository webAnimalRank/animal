package com.example.animal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.animal.dto.Board;
import com.example.animal.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:3000") // 개발용
public class BoardController {

    private final BoardService boardService;

    // 목록 + 검색 + 페이징
    // GET /api/boards?search=title|content|titleContent|writer&keyword=...&page=1&size=10
    @GetMapping
    public Map<String, Object> list(
            @RequestParam(defaultValue = "titleContent") String search,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Board> items = boardService.getBoards(search, keyword, page, size);
        long totalItems = boardService.getTotalBoards(search, keyword);

        int safeSize = Math.min(Math.max(size, 1), 50);
        int totalPages = (int) Math.ceil((double) totalItems / safeSize);

        Map<String, Object> res = new HashMap<>();
        res.put("items", items);
        res.put("page", Math.max(page, 1));
        res.put("size", safeSize);
        res.put("totalItems", totalItems);
        res.put("totalPages", totalPages);
        return res;
    }

    // 상세
    @GetMapping("/{boardNo}")
    public Board detail(@PathVariable int boardNo) {
        return boardService.getBoardDetail(boardNo);
    }

    // 등록
    @PostMapping
    public Map<String, Object> create(@RequestBody Board board) {
        int result = boardService.createBoard(board);

        Map<String, Object> res = new HashMap<>();
        res.put("result", result);           // 1이면 성공
        res.put("boardNo", board.getBoardNo()); // 생성된 PK
        return res;
    }

    // 수정
    @PutMapping("/{boardNo}")
    public Map<String, Object> update(@PathVariable int boardNo, @RequestBody Board board) {
        boolean ok = boardService.updateBoard(boardNo, board);

        Map<String, Object> res = new HashMap<>();
        res.put("result", ok ? 1 : 0);
        return res;
    }

    // 삭제(소프트)
    @DeleteMapping("/{boardNo}")
    public Map<String, Object> delete(@PathVariable int boardNo) {
        boolean ok = boardService.deleteBoard(boardNo);

        Map<String, Object> res = new HashMap<>();
        res.put("result", ok ? 1 : 0);
        return res;
    }
}
