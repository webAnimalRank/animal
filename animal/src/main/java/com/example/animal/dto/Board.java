package com.example.animal.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Board {
    private Integer boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private Integer memberNo;
    private Integer isactive;

    private LocalDate createDate;
    private LocalDate updateDate;

    // JOIN 결과 (member 테이블에 member_name이 있다고 가정)
    private String memberName;
}
