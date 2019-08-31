package com.github.anricx.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.anricx.persistent.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfileResponse {

    @ApiModelProperty(position = 0)
    private Integer id;
    @ApiModelProperty(value = "用户编号")
    private String username;
    @ApiModelProperty(value = "用户实名")
    private String realName;
    @ApiModelProperty(position = 2)
    private String email;
    @ApiModelProperty(position = 3)
    List<Role> roles;

}
