package com.example.animal.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Board {
    private Integer boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private Integer memberNo;
    private Integer isactive;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // JOIN 결과 (member 테이블에 member_name이 있다고 가정)
    private String memberName;
}
