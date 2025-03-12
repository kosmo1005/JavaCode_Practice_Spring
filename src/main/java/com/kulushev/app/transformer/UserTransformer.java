package com.kulushev.app.transformer;

import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTransformer {

    UserRespDto entityToDto(UserEntity userEntity);
    UserEntity dtoToEntity(UserReqDto userReqDto);
}
