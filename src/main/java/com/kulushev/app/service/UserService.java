package com.kulushev.app.service;

import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.Role;
import com.kulushev.app.exception.notFound.UserNotFoundException;
import com.kulushev.app.repository.UserRepository;
import com.kulushev.app.transformer.UserTransformer;
import com.kulushev.app.transformer.UserTransformer1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository repo;
    private final UserTransformer t;
    private final UserTransformer1 t1;

    @Lazy
    @Autowired
    private UserService  self;

    @Override
    @Transactional
    public DefaultOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = (String) Optional.ofNullable(oauthUser.getAttribute("email"))
                .orElse("Email не предоставлен");

        String id = oauthUser.getAttribute("id").toString();
        String name = oauthUser.getAttribute("name");
        String login = oauthUser.getAttribute("login");

        UserEntity user = repo.findByLogin(login)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setId(id);
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setLogin(login);
                    newUser.setRole(Role.USER);
                    return repo.saveAndFlush(newUser);
                });

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        Map <String, Object> attributes = new HashMap<>();
        attributes.put("id", user.getId());
        attributes.put("name", user.getName());
        attributes.put("login", user.getLogin());
        attributes.put("email", user.getEmail());

        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(authorities, attributes,"id" );
        return oAuth2User;
    }

    @Transactional
    public List<UserRespDto> getAllUsers() {
        return repo.findAll().stream()
                .map(t::entityToDto)
                .toList();
    }

    @Transactional
    public UserRespDto getUserById(String id) {
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
        return t.entityToDto(repo.findByName(name)
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
    public void deleteUserById(String id) {
        if (!self.userExistsById(id)) {
            throw new UserNotFoundException("User not found");
        }
         repo.deleteById(id);
    }

    @Transactional
    public UserRespDto getUserWithInfoAboutOrders(String id) throws Exception {
        var entity = repo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (entity.getOrders().isEmpty()) {
            throw new Exception("У вас пока нет заказов");
        }
        return t1.entityToDto(entity);
    }

    @Transactional
    public boolean userExistsById(String id) {
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
