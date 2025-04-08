package com.devsu.account_service.adapter.mapper;

import com.devsu.account_service.adapter.dto.request.AccountRequest;
import com.devsu.account_service.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

    Account toAccount(AccountRequest accountRequest);
}
