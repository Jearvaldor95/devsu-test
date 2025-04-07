package com.devsu.account_service.infraestructure.adapter.persitence.mapper;

import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")

public interface MovementMapper {

    MovementEntity toEntity(Movement movement);

    Movement toDomain(MovementEntity movementEntity);

    List<Movement> toMovements(List<MovementEntity> movementEntities);

    void updateMovement(@MappingTarget MovementEntity movementEntity, Movement movement);
}
