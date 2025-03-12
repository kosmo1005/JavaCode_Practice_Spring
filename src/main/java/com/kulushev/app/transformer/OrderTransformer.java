package com.kulushev.app.transformer;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderTransformer {

    OrderRespDto entityToDto(OrderEntity orderEntity);
    OrderEntity dtoToEntity(OrderReqDto orderReqDto);
}
