package com.example.animal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.animal.dto.Board;
import com.example.animal.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<Board> getBoards(String search, String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        int safeSize = Math.min(Math.max(size, 1), 50);
        int offset = (safePage - 1) * safeSize;

        return boardMapper.selectBoardPage(search, keyword, offset, safeSize);
    }

    @Override
    public long getTotalBoards(String search, String keyword) {
        return boardMapper.countBoards(search, keyword);
    }

    @Override
    public Board getBoardDetail(int boardNo) {
        return boardMapper.selectBoardDetail(boardNo);
    }

    @Override
    public int createBoard(Board board) {
        // 기본값 방어
        if (board.getMemberNo() == null) board.setMemberNo(1); // 임시값(원하면 제거)
        if (board.getBoardWriter() == null || board.getBoardWriter().isBlank()) board.setBoardWriter("익명");
        return boardMapper.insertBoard(board); // board.boardNo 자동 세팅됨(useGeneratedKeys)
    }

    @Override
    public boolean updateBoard(int boardNo, Board board) {
        return boardMapper.updateBoard(boardNo, board) == 1;
    }

    @Override
    public boolean deleteBoard(int boardNo) {
        return boardMapper.softDelete(boardNo) == 1;
    }
}
