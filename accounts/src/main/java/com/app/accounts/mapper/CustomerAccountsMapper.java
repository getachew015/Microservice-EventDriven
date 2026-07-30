package com.app.accounts.mapper;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.dto.CustomerProfileDto;
import com.app.accounts.dto.NewCustomerDto;
import com.app.accounts.dto.PatchCustomerAccountDto;
import com.app.accounts.entity.AccountsEntity;
import com.app.accounts.entity.CustomersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = IGNORE)
public interface CustomerAccountsMapper {

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "email", qualifiedByName = "maskEmail")
    CustomerAccountDto toCustomerAccountDto(CustomersEntity customer);

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "email", qualifiedByName = "maskEmail")
    CustomerProfileDto toCustomerProfileDto(CustomersEntity customer, AccountsEntity account);

    @Mapping(target = "customerStatus", source = "customer.status")
    @Mapping(target = "accountStatus", source = "account.status")
    PatchCustomerAccountDto toPatchCustomerAccountDto(CustomersEntity customer, AccountsEntity account);

    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", expression = "java(\"SYSTEM\")")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"SYSTEM\")")
    AccountsEntity toAccountEntity(NewCustomerDto customer);

    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", expression = "java(\"SYSTEM\")")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"SYSTEM\")")
    CustomersEntity toCustomerEntity(NewCustomerDto customer);

    @Mapping(target = "status", expression = "java(customerAccountDto.getAccountStatus() != null ? customerAccountDto.getAccountStatus() : oldAccountEntity.getStatus())")
    @Mapping(target = "balance", expression = "java(customerAccountDto.getBalance() != null ? new java.math.BigDecimal(customerAccountDto.getBalance()) : oldAccountEntity.getBalance())")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"SYSTEM\")")
    AccountsEntity toAccountEntity(PatchCustomerAccountDto customerAccountDto, AccountsEntity oldAccountEntity);

    @Mapping(target = "status", expression = "java(customerAccountDto.getCustomerStatus() != null ? customerAccountDto.getCustomerStatus() : existingCustomerRecord.getStatus())")
    @Mapping(target = "firstName", expression = "java(customerAccountDto.getFirstName() != null ? customerAccountDto.getFirstName() : existingCustomerRecord.getFirstName())")
    @Mapping(target = "lastName", expression = "java(customerAccountDto.getLastName() != null ? customerAccountDto.getLastName() : existingCustomerRecord.getLastName())")
    @Mapping(target = "email", expression = "java(customerAccountDto.getEmail() != null ? customerAccountDto.getEmail() : existingCustomerRecord.getEmail())")
    @Mapping(target = "phoneNumber", expression = "java(customerAccountDto.getPhoneNumber() != null ? customerAccountDto.getPhoneNumber() : existingCustomerRecord.getPhoneNumber())")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"SYSTEM\")")
    CustomersEntity toCustomerEntity(PatchCustomerAccountDto customerAccountDto, CustomersEntity existingCustomerRecord);

    @Named("maskEmail")
    default String maskEmail(String email) {
        if (email == null) return null;
        return email.replaceAll("(^[^@]{2})([^@]*)(@[^@]+)", "$1***$3");
    }

}
