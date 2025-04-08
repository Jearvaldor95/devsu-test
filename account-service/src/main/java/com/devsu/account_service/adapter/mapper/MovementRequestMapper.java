package com.devsu.account_service.adapter.mapper;

import com.devsu.account_service.adapter.dto.request.MovementRequest;
import com.devsu.account_service.domain.model.Movement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementRequestMapper {

    Movement toMovement(MovementRequest movementRequest);
}
