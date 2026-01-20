package com.example.animal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.animal.dto.Board;

@Mapper
public interface BoardMapper {

    // 목록 페이징 + 검색
    long countBoards(@Param("search") String search,
                     @Param("keyword") String keyword);

    List<Board> selectBoardPage(@Param("search") String search,
                                @Param("keyword") String keyword,
                                @Param("offset") int offset,
                                @Param("size") int size);

    // 상세
    Board selectBoardDetail(@Param("boardNo") int boardNo);

    // 등록
    int insertBoard(Board board);

    // 수정
    int updateBoard(@Param("boardNo") int boardNo,
                    @Param("board") Board board);

    // 삭제(소프트)
    int softDelete(@Param("boardNo") int boardNo);
}
