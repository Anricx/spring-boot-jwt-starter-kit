package com.github.anricx.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.anricx.persistent.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfileResponse {

    private Integer id;
    @Schema(title = "用户编号")
    private String username;
    @Schema(title = "用户实名")
    private String realName;
    private String email;
    List<Role> roles;

}
