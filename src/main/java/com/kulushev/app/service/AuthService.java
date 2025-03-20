package com.kulushev.app.service;

import com.kulushev.app.dto.auth.AuthRespDto;
import com.kulushev.app.dto.auth.RefreshReqDto;
import com.kulushev.app.dto.auth.SignInReqDto;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.Role;
import com.kulushev.app.exception.auth.AuthenticationFailedException;
import com.kulushev.app.exception.auth.TooManyLoginAttemptsException;
import com.kulushev.app.exception.UserAlreadyExist;
import com.kulushev.app.exception.notFound.UserNotFoundException;
import com.kulushev.app.security.MyJwtMaker;
import com.kulushev.app.transformer.UserTransformer;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final MyJwtMaker myJwtMaker;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserTransformer t;

    @Transactional
    public UserRespDto signUp(UserReqDto userReqDto) throws Exception {
       if (userReqDto.password().isBlank()){
           throw new AuthenticationFailedException("Password must not be blank");
       }
        if (userService.userExistsByLogin(userReqDto.login())) {
            throw new UserAlreadyExist("User with this login already exists");
        }
        if (userService.userExistsByEmail(userReqDto.email())) {
            throw new UserAlreadyExist("User with this email already exists");
        }

        try {
            var userEntity = t.dtoToEntity(userReqDto);
            userEntity.setHashOfPassword(passwordEncoder.encode(userReqDto.password()));
            userEntity.setRole(Role.USER);
            userEntity.setAccountLocked(false);
            return userService.createUser(userEntity);
        } catch (Exception e) {
            throw new Exception("Unexpected error occurred while creating user");
        }
    }

    @Transactional
    public AuthRespDto signIn(SignInReqDto signInReqDto) throws Exception {
        var userEntity = new UserEntity();

        try {
            userEntity = userService.loadUserByUsername(signInReqDto.login());

            if (userEntity.isAccountLocked()) {
                throw new TooManyLoginAttemptsException("Your account has been blocked. Please contact the administrator.");
            }
            if (signInReqDto.password().isBlank()){
                throw new AuthenticationFailedException("Password must not be blank");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInReqDto.login(), signInReqDto.password()));
            userEntity.setCountOfFailedAuth(0);
            var jwt = myJwtMaker.generateToken(userEntity);
            var refresh = myJwtMaker.generateRefreshToken(new HashMap<>(), userEntity);

            return new AuthRespDto(jwt, refresh, t.entityToDto(userEntity));

        } catch (BadCredentialsException e){
            userEntity.setCountOfFailedAuth(userEntity.getCountOfFailedAuth() + 1);
            if (userEntity.getCountOfFailedAuth() >= 5) {
                userEntity.setAccountLocked(true);
                userService.updateUser(userEntity);
                throw new AuthenticationFailedException("Invalid password. Your account has been blocked. Please contact the administrator.");
            }
            userService.updateUser(userEntity);
            throw new AuthenticationFailedException("Invalid password. Attempt " + userEntity.getCountOfFailedAuth() + " of 5.");

        } catch (TooManyLoginAttemptsException | UserNotFoundException | AuthenticationFailedException e) {
            throw e;
        }

        catch (Exception e) {
            throw new Exception("Unexpected error occurred while sign in");
        }
    }

    public AuthRespDto refreshToken (RefreshReqDto reqDto){
        if (reqDto.refreshToken().isBlank()){
            throw new AuthenticationFailedException("Refresh token must not be blank");
        }
        var login = myJwtMaker.extractLogin(reqDto.refreshToken());
        var userEntity = userService.loadUserByUsername(login);
        if (!myJwtMaker.isTokenValid(reqDto.refreshToken(), userEntity)) {
            throw new AuthenticationFailedException("Invalid token");
        }
        var jwt = myJwtMaker.generateToken(userEntity);

        return new AuthRespDto(jwt, reqDto.refreshToken(), t.entityToDto(userEntity));

    }

}
