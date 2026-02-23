package com.example.animal.controller;

import com.example.animal.dto.Board;
import com.example.animal.dto.BoardMutationResponse;
import com.example.animal.dto.BoardPageResponse;
import com.example.animal.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {

    private static final String DEFAULT_KIND = "notice";
    private static final String DEFAULT_SEARCH = "titleContent";
    private static final String DEFAULT_KEYWORD = "";
    private static final int DEFAULT_PAGE = 1;
    private static final int MAX_PAGE_SIZE = 50;

    private final BoardService boardService;

    @GetMapping
    public BoardPageResponse list(
            @RequestParam(defaultValue = DEFAULT_KIND) String kind,
            @RequestParam(defaultValue = DEFAULT_SEARCH) String search,
            @RequestParam(defaultValue = DEFAULT_KEYWORD) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        int safePage = normalizePage(page);
        int safeSize = normalizeSize(size);

        List<Board> items = boardService.getBoards(kind, search, keyword, safePage, safeSize);
        long totalItems = boardService.getTotalBoards(kind, search, keyword);
        int totalPages = (int) Math.ceil((double) totalItems / safeSize);

        return new BoardPageResponse(items, safePage, safeSize, totalItems, totalPages);
    }

    @GetMapping("/{boardNo}")
    public Board detail(@PathVariable int boardNo) {
        return boardService.getBoardDetail(boardNo);
    }

    @PostMapping
    public BoardMutationResponse create(@RequestBody Board board) {
        int result = boardService.createBoard(board);
        return new BoardMutationResponse(result, board.getBoardNo());
    }

    @PutMapping("/{boardNo}")
    public BoardMutationResponse update(@PathVariable int boardNo, @RequestBody Board board) {
        boolean ok = boardService.updateBoard(boardNo, board);
        return new BoardMutationResponse(ok ? 1 : 0, null);
    }

    @DeleteMapping("/{boardNo}")
    public BoardMutationResponse delete(@PathVariable int boardNo) {
        boolean ok = boardService.deleteBoard(boardNo);
        return new BoardMutationResponse(ok ? 1 : 0, null);
    }

    private int normalizePage(int page) {
        return Math.max(page, DEFAULT_PAGE);
    }

    private int normalizeSize(int size) {
        return Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
    }
}
