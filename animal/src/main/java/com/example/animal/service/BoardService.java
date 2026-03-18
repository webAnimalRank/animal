package com.example.animal.service;

import com.example.animal.dto.Board;

import java.util.List;

public interface BoardService {

    List<Board> getBoards(String kind, String search, String keyword, int page, int size);

    long getTotalBoards(String kind, String search, String keyword);

    Board getBoardDetail(int boardNo);

    int createBoard(Board board);

    boolean updateBoard(int boardNo, Board board, int memberNo);

    boolean deleteBoard(int boardNo, int memberNo);

    List<Board> getBoardsByMember(int memberNo);
}
