package com.devsu.account_service.infraestructure.adapter.persitence.mapper;

import com.devsu.account_service.domain.model.Movement;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")

public interface MovementMapper {

    @Mapping(target = "account.accountId", source= "accountId")
    MovementEntity toEntity(Movement movement);

    @Mapping(target = "accountId", source = "account.accountId")
    Movement toDomain(MovementEntity movementEntity);

    List<Movement> toMovements(List<MovementEntity> movementEntities);

    @Mapping(target = "movementId", ignore = true)
    void updateMovement(@MappingTarget MovementEntity movementEntity, Movement movement);
}
