package com.kulushev.app.service;

import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.exception.UserAlreadyExist;
import com.kulushev.app.exception.UserNotFoundException;
import com.kulushev.app.repository.JDBC.JDBCUserRepository;
import com.kulushev.app.transformer.UserTransformer;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JDBCUserRepository repo;
    private final UserTransformer t;

    @Transactional
    public List<UserRespDto> getAllUsers() {
        return repo.findAll().stream()
                .map(t::entityToDto)
                .toList();
    }

    @Transactional
    public UserRespDto getUserById(UUID id) {
        return t.entityToDto(repo.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public UserRespDto findUserByEmail(String email) {
        return t.entityToDto(repo.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public UserRespDto findUserByName(String name) {
        return t.entityToDto(repo.findByName(name).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public UserRespDto createUser (UserReqDto dto) {
        var entity = t.dtoToEntity(dto);
        if (repo.findByEmail(entity.getEmail()).isPresent()) {
            throw new UserAlreadyExist("User with this email already exists");
        }
        var savedEntity = repo.save(entity);
        return t.entityToDto(savedEntity);
    }

    @Transactional
    public void deleteUserById(UUID id) {
        if (repo.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
         repo.deleteById(id);
    }

    @Transactional
    public boolean userExists(UUID id) {
        return repo.findById(id).isPresent();
    }
}
