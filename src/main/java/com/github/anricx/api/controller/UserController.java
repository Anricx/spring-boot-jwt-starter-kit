package com.github.anricx.api.controller;

import com.github.anricx.persistent.entity.User;
import com.github.anricx.security.AccessToken;
import com.github.anricx.api.response.UserAuthenticateResponse;
import com.github.anricx.api.response.UserProfileResponse;
import com.github.anricx.security.SecurityConstants;
import com.github.anricx.service.UserService;
import io.swagger.annotations.*;
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
@Api(tags = "Use APIs", value = "User Authenticate/Profile APIs")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Get user simple profile by username")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Username is not found!"),
    })
    @GetMapping("/profile")
    public UserProfileResponse profile(@ApiParam(value = "Username", required = true) @RequestParam String username) {
        User user = userService.profile(username);

        return modelMapper.map(user, UserProfileResponse.class);
    }

    @ApiOperation(value = "Authenticates user and returns it's AccessToken.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")
    })
    @PostMapping("/authenticate")
    public UserAuthenticateResponse authenticate(@ApiParam(value = "Username", required = true) @RequestParam String username,
                                                 @ApiParam(value = "Password", required = true) @RequestParam String password,
                                                 HttpServletResponse httpServletResponse) {
        AccessToken accessToken = userService.signin(username, password);

        // Inject Header...
        httpServletResponse.addHeader(SecurityConstants.TOKEN_HEADER, accessToken.toString());

        return UserAuthenticateResponse.builder()
                .tokenType(accessToken.getType())
                .accessToken(accessToken.getValue())
                .build();
    }

    @ApiOperation(value = "Refresh current AccessToken", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
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

    @ApiOperation(value = "Returns current user's data", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    @GetMapping(value = "/whoami")
    public UserProfileResponse whoami(HttpServletRequest httpServletRequest) {
        return modelMapper.map(userService.whoami(httpServletRequest), UserProfileResponse.class);
    }

//    @ApiOperation(value = "Logout current Session", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @DeleteMapping(value = "/token")
//    public UserLogoutResponse logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        httpServletResponse.setHeader(SecurityConstants.TOKEN_HEADER, "");
//        return UserLogoutResponse.builder()
//                .build();
//    }

//    @ApiOperation(value = "Creates user and returns its JWT token")
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 422, message = "Username is already in use"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
//    })
//    @PostMapping("/signup")
//    public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
//        return userService.signup(modelMapper.map(user, User.class));
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "Deletes specific user by username", authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 404, message = "The user doesn't exist"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
//    })
//    @DeleteMapping(value = "/{username}")
//    public String delete(@ApiParam("Username") @PathVariable String username) {
//        userService.delete(username);
//        return username;
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation(value = "Returns specific user by username", response = UserResponseDTO.class, authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 404, message = "The user doesn't exist"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
//    })
//    @GetMapping(value = "/{username}")
//    public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//        return modelMapper.map(userService.search(username), UserResponseDTO.class);
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
//    @ApiOperation(value = "Returns current user's data", response = UserResponseDTO.class, authorizations = {@Authorization(value= SecurityConstants.BearerAPIKey)})
//    @ApiResponses(value = {
//            @ApiResponse(code = 400, message = "Something went wrong"),
//            @ApiResponse(code = 403, message = "Access denied"),
//            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
//    })
//    @GetMapping(value = "/me")
//    public UserResponseDTO whoami(HttpServletRequest req) {
//        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
//    }

}
