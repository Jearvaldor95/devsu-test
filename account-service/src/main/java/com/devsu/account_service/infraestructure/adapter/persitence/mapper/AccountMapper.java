package com.devsu.account_service.infraestructure.adapter.persitence.mapper;

import com.devsu.account_service.domain.model.Account;
import com.devsu.account_service.infraestructure.adapter.persitence.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity toEntity(Account account);

    Account toDomain(AccountEntity accountEntity);

    List<Account> toAccounts(List<AccountEntity> accountEntities);

    @Mappings({
            @Mapping(target = "accountId", ignore = true),
            @Mapping(target = "accountNumber", ignore = true),
            @Mapping(target = "customerId", ignore = true)

    })
    void updateAccount(@MappingTarget AccountEntity accountEntity, Account account);
}
