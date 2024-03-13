package com.practice.orm.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateCommentDTO {
    private Long id;

    private String Content;
    private String username;
    private List<Long> subCommentsId;
}
