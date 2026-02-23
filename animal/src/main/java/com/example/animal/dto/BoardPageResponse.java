package com.example.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardPageResponse {
    private List<Board> items;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
}
