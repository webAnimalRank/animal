package com.example.animal.controller;

import com.example.animal.dto.Board;
import com.example.animal.dto.BoardMutationResponse;
import com.example.animal.dto.BoardPageResponse;
import com.example.animal.dto.MemberDto;
import com.example.animal.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "https://animal-2g13.onrender.com",
        "https://0ef352f5.animal-guide.pages.dev"
    },
    allowCredentials = "true"
)
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
    public ResponseEntity<BoardMutationResponse> create(@RequestBody Board board, HttpSession session) {
        MemberDto loginMember = requireLoginMember(session);
        board.setMemberNo(loginMember.getMemberNo());
        board.setBoardWriter(loginMember.getMemberName());

        int result = boardService.createBoard(board);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BoardMutationResponse(result, board.getBoardNo()));
    }

    @PutMapping("/{boardNo}")
    public BoardMutationResponse update(@PathVariable int boardNo, @RequestBody Board board, HttpSession session) {
        MemberDto loginMember = requireLoginMember(session);
        board.setMemberNo(loginMember.getMemberNo());
        board.setBoardWriter(loginMember.getMemberName());

        boolean ok = boardService.updateBoard(boardNo, board, loginMember.getMemberNo());
        return new BoardMutationResponse(ok ? 1 : 0, ok ? boardNo : null);
    }

    @DeleteMapping("/{boardNo}")
    public BoardMutationResponse delete(@PathVariable int boardNo, HttpSession session) {
        MemberDto loginMember = requireLoginMember(session);

        boolean ok = boardService.deleteBoard(boardNo, loginMember.getMemberNo());
        return new BoardMutationResponse(ok ? 1 : 0, ok ? boardNo : null);
    }

    @GetMapping("/my")
    public List<Board> myBoards(HttpSession session) {
        MemberDto member = requireLoginMember(session);
        return boardService.getBoardsByMember(member.getMemberNo());
    }

    private int normalizePage(int page) {
        return Math.max(page, DEFAULT_PAGE);
    }

    private int normalizeSize(int size) {
        return Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
    }

    private MemberDto requireLoginMember(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("loginMember");
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login is required.");
        }
        return member;
    }
}
