package com.example.animal.service;

import com.example.animal.dto.Board;
import com.example.animal.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private static final int MIN_PAGE = 1;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 50;
    private static final String DEFAULT_KIND = "notice";
    private static final String DEFAULT_CREATE_KIND = "free";
    private static final String DEFAULT_SEARCH = "titleContent";
    private static final Set<String> ALLOWED_BOARD_KINDS = Set.of("notice", "free");
    private static final Set<String> ALLOWED_SEARCH_TYPES = Set.of("title", "content", "titleContent", "writer");

    private final BoardMapper boardMapper;

    @Override
    public List<Board> getBoards(String kind, String search, String keyword, int page, int size) {
        String safeKind = normalizeKind(kind);
        String safeSearch = normalizeSearch(search);
        String safeKeyword = normalizeKeyword(keyword);
        int safePage = normalizePage(page);
        int safeSize = normalizeSize(size);
        int offset = (safePage - 1) * safeSize;

        return boardMapper.selectBoardPage(safeKind, safeSearch, safeKeyword, offset, safeSize);
    }

    @Override
    public long getTotalBoards(String kind, String search, String keyword) {
        return boardMapper.countBoards(normalizeKind(kind), normalizeSearch(search), normalizeKeyword(keyword));
    }

    @Override
    public Board getBoardDetail(int boardNo) {
        return boardMapper.selectBoardDetail(boardNo);
    }

    @Override
    public int createBoard(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board request body is required.");
        }

        applyCreateDefaults(board);
        return boardMapper.insertBoard(board);
    }

    @Override
    public boolean updateBoard(int boardNo, Board board, int memberNo) {
        if (board == null) {
            throw new IllegalArgumentException("Board request body is required.");
        }

        applyUpdateDefaults(board, memberNo);
        return boardMapper.updateBoard(boardNo, board, memberNo) == 1;
    }

    @Override
    public boolean deleteBoard(int boardNo, int memberNo) {
        if (memberNo <= 0) {
            throw new IllegalArgumentException("memberNo is required.");
        }

        return boardMapper.softDelete(boardNo, memberNo) == 1;
    }

    @Override
    public List<Board> getBoardsByMember(int memberNo) {
        return boardMapper.selectBoardsByMember(memberNo);
    }

    private int normalizePage(int page) {
        return Math.max(page, MIN_PAGE);
    }

    private int normalizeSize(int size) {
        return Math.min(Math.max(size, MIN_SIZE), MAX_SIZE);
    }

    private String normalizeKind(String kind) {
        if (kind == null || kind.isBlank()) {
            return DEFAULT_KIND;
        }
        String trimmed = kind.trim();
        return ALLOWED_BOARD_KINDS.contains(trimmed) ? trimmed : DEFAULT_KIND;
    }

    private String normalizeCreateKind(String kind) {
        if (kind == null || kind.isBlank()) {
            return DEFAULT_CREATE_KIND;
        }
        String trimmed = kind.trim();
        return ALLOWED_BOARD_KINDS.contains(trimmed) ? trimmed : DEFAULT_CREATE_KIND;
    }

    private String normalizeSearch(String search) {
        if (search == null || search.isBlank()) {
            return DEFAULT_SEARCH;
        }
        String trimmed = search.trim();
        return ALLOWED_SEARCH_TYPES.contains(trimmed) ? trimmed : DEFAULT_SEARCH;
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        String trimmed = keyword.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void applyCreateDefaults(Board board) {
        validateBoardContent(board);
        board.setBoardKind(normalizeCreateKind(board.getBoardKind()));
    }

    private void applyUpdateDefaults(Board board, int memberNo) {
        if (memberNo <= 0) {
            throw new IllegalArgumentException("memberNo is required.");
        }

        validateBoardContent(board);
        board.setMemberNo(memberNo);
        board.setBoardKind(normalizeCreateKind(board.getBoardKind()));
    }

    private void validateBoardContent(Board board) {
        String title = trimToNull(board.getBoardTitle());
        if (title == null) {
            throw new IllegalArgumentException("boardTitle is required.");
        }
        board.setBoardTitle(title);

        String content = trimToNull(board.getBoardContent());
        if (content == null) {
            throw new IllegalArgumentException("boardContent is required.");
        }
        board.setBoardContent(content);

        if (board.getMemberNo() == null || board.getMemberNo() <= 0) {
            throw new IllegalArgumentException("memberNo is required.");
        }

        String writer = trimToNull(board.getBoardWriter());
        if (writer == null) {
            throw new IllegalArgumentException("boardWriter is required.");
        }
        board.setBoardWriter(writer);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
