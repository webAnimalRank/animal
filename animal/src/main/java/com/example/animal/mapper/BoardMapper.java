package com.example.animal.mapper;

import com.example.animal.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    long countBoards(@Param("kind") String kind,
                     @Param("search") String search,
                     @Param("keyword") String keyword);

    List<Board> selectBoardPage(@Param("kind") String kind,
                                @Param("search") String search,
                                @Param("keyword") String keyword,
                                @Param("offset") int offset,
                                @Param("size") int size);

    Board selectBoardDetail(@Param("boardNo") int boardNo);

    int insertBoard(Board board);

    int updateBoard(@Param("boardNo") int boardNo,
                    @Param("board") Board board,
                    @Param("memberNo") int memberNo);

    int softDelete(@Param("boardNo") int boardNo,
                   @Param("memberNo") int memberNo);

    List<Board> selectBoardsByMember(int memberNo);
}
