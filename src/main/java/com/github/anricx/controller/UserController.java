package com.github.anricx.controller;

import com.github.anricx.persistent.entity.User;
import com.github.anricx.security.AccessToken;
import com.github.anricx.api.response.UserAuthenticateResponse;
import com.github.anricx.api.response.UserProfileResponse;
import com.github.anricx.security.SecurityConstants;
import com.github.anricx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User APIs
 * Created by dengt on 2019/8/31
 */
@Tag(name = "Use APIs", description = "User Authenticate/Profile APIs")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "Get user simple profile by username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "获取成功"),
                    @ApiResponse(responseCode = "404", description = "Username is not found!"),
            }
    )
    @GetMapping("/profile")
    public UserProfileResponse profile(@Parameter(description = "Username", required = true) @RequestParam String username) {
        User user = userService.profile(username);

        return modelMapper.map(user, UserProfileResponse.class);
    }

    @Operation(
            summary = "Authenticates user and returns it's AccessToken.",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Something went wrong"),
                    @ApiResponse(responseCode = "422", description = "Invalid username/password supplied")
            }
    )
    @PostMapping("/authenticate")
    public UserAuthenticateResponse authenticate(@Parameter(description = "Username", required = true) @RequestParam String username,
                                                 @Parameter(description = "Password", required = true) @RequestParam String password,
                                                 HttpServletResponse httpServletResponse) {
        AccessToken accessToken = userService.signin(username, password);

        // Inject Header...
        httpServletResponse.addHeader(SecurityConstants.TOKEN_HEADER, accessToken.toString());

        return UserAuthenticateResponse.builder()
                .tokenType(accessToken.getType())
                .accessToken(accessToken.getValue())
                .build();
    }

    @Operation(
            summary = "Refresh current AccessToken",
            security = {
                    @SecurityRequirement(name = SecurityConstants.BearerAPIKey)
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/token")
    public UserAuthenticateResponse token(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        AccessToken accessToken = userService.refresh(httpServletRequest.getRemoteUser());

        // Inject Header...
        httpServletResponse.addHeader(SecurityConstants.TOKEN_HEADER, accessToken.toString());

        return UserAuthenticateResponse.builder()
                .tokenType(accessToken.getType())
                .accessToken(accessToken.getValue())
                .build();
    }

    @Operation(
            summary = "Returns current user's data",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Something went wrong"),
                    @ApiResponse(responseCode = "403", description = "Access denied"),
                    @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
            },
            security = {
                    @SecurityRequirement(name = SecurityConstants.BearerAPIKey)
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping(value = "/whoami")
    public UserProfileResponse whoami(HttpServletRequest httpServletRequest) {
        return modelMapper.map(userService.whoami(httpServletRequest), UserProfileResponse.class);
    }

//    @Operation(summary = "Logout current Session", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @DeleteMapping(value = "/token")
//    public UserLogoutResponse logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        httpServletResponse.setHeader(SecurityConstants.TOKEN_HEADER, "");
//        return UserLogoutResponse.builder()
//                .build();
//    }

//    @Operation(summary = "Creates user and returns its JWT token")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "400", description = "Something went wrong"),
//            @ApiResponse(responseCode = "403", description = "Access denied"),
//            @ApiResponse(responseCode = "422", description = "Username is already in use"),
//            @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
//    })
//    @PostMapping("/signup")
//    public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
//        return userService.signup(modelMapper.map(user, User.class));
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @Operation(summary = "Deletes specific user by username", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "400", description = "Something went wrong"),
//            @ApiResponse(responseCode = "403", description = "Access denied"),
//            @ApiResponse(responseCode = "404", description = "The user doesn't exist"),
//            @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
//    })
//    @DeleteMapping(value = "/{username}")
//    public String delete(@ApiParam("Username") @PathVariable String username) {
//        userService.delete(username);
//        return username;
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @Operation(summary = "Returns specific user by username", response = UserResponseDTO.class, authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "400", description = "Something went wrong"),
//            @ApiResponse(responseCode = "403", description = "Access denied"),
//            @ApiResponse(responseCode = "404", description = "The user doesn't exist"),
//            @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
//    })
//    @GetMapping(value = "/{username}")
//    public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//        return modelMapper.map(userService.search(username), UserResponseDTO.class);
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
//    @Operation(summary = "Returns current user's data", response = UserResponseDTO.class, authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "400", description = "Something went wrong"),
//            @ApiResponse(responseCode = "403", description = "Access denied"),
//            @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
//    })
//    @GetMapping(value = "/me")
//    public UserResponseDTO whoami(HttpServletRequest req) {
//        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
//    }

}
