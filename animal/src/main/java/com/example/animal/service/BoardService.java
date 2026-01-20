package com.example.animal.service;

import com.example.animal.dto.Board;
import java.util.List;

public interface BoardService {

    // 목록 + 검색 + 페이징
    List<Board> getBoards(String search, String keyword, int page, int size);
    long getTotalBoards(String search, String keyword);

    // 상세
    Board getBoardDetail(int boardNo);

    // 등록
    int createBoard(Board board);

    // 수정
    boolean updateBoard(int boardNo, Board board);

    // 삭제(소프트)
    boolean deleteBoard(int boardNo);
}
