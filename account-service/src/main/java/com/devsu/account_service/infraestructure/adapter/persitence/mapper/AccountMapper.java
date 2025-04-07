package com.devsu.account_service.infraestructure.adapter.persitence.mapper;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity toEntity(Account account);

    Account toDomain(AccountEntity accountEntity);

    List<Account> toAccounts(List<AccountEntity> accountEntities);

    void updateAccount(@MappingTarget AccountEntity accountEntity, Account account);
}
