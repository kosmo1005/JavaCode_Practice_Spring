package com.kulushev.app.service;

import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.exception.notFound.UserNotFoundException;
import com.kulushev.app.repository.UserRepository;
import com.kulushev.app.transformer.UserTransformer;
import com.kulushev.app.views.UserFullNameProjection;
import com.kulushev.app.views.UserWithInfoAboutOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final UserTransformer t;

    @Lazy
    @Autowired
    private UserService  self;

    @Override
    public UserEntity loadUserByUsername(String login){
        return repo.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким логином не найден"));
    }


    @Transactional
    public List<UserRespDto> getAllUsers() {
        return repo.findAll().stream()
                .map(t::entityToDto)
                .toList();
    }

    @Transactional
    public UserRespDto getUserById(UUID id) {
        return t.entityToDto(repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional
    public UserRespDto findUserByEmail(String email) {
        return t.entityToDto(repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional
    public UserRespDto findUserByName(String name) {
        return t.entityToDto(repo.findByFirstName(name)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @Transactional
    public UserRespDto createUser (UserEntity user) {
        return t.entityToDto(repo.save(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserRespDto updateUser (UserEntity user) {
        return t.entityToDto(repo.save(user));
    }

    @Transactional
    public void deleteUserById(UUID id) {
        if (self.userExistsById(id)) {
            throw new UserNotFoundException("User not found");
        }
         repo.deleteById(id);
    }

    @Transactional
    public UserFullNameProjection getFullNameById(UUID id) {
        return repo.findFullNameById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public UserWithInfoAboutOrders getUserWithInfoAboutOrders(UUID id) {
        return repo.findUserWithInfoAboutOrders(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public boolean userExistsById(UUID id) {
        return repo.findById(id).isPresent();
    }

    @Transactional
    public boolean userExistsByLogin(String login) {
        return repo.findByLogin(login).isPresent();
    }
    @Transactional
    public boolean userExistsByEmail(String email) {
        return repo.findByEmail(email).isPresent();
    }





}
