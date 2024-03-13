package com.practice.orm.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUserDTO {

    private Long id;

    private String username;

    private String email;

    private List<Long> friends_id;
}
