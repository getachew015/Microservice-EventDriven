package com.app.loans.mapper;

import com.app.loans.dto.LoanAccountDto;
import com.app.loans.dto.NewLoanDto;
import com.app.loans.entity.LoanAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = IGNORE)
public interface LoanAccountMapper {

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "interestRate", expression = "java(getInterestRate(newLoanDto.getLoanType()))")
    @Mapping(target = "startDate", expression = "java(getLoanStartDate(newLoanDto.getLoanType()))")
    @Mapping(target = "endDate", expression = "java(getLoanEndDate(newLoanDto.getLoanType()))")
    @Mapping(target = "activeStatus", expression = "java(true)")
    @Mapping(target = "deletedStatus", expression = "java(false)")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdBy", expression = "java(\"SYSTEM\")")
    @Mapping(target = "updatedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(\"USER\")")
    LoanAccountEntity toLoansEntity(NewLoanDto newLoanDto);
    @Mapping(target = "loanId", source = "loanId")
    @Mapping(target = "customerId", source = "customerId")
    LoanAccountDto toLoansAccountDto(LoanAccountEntity loanAccountEntity);
    @Named("getLoanStartDate")
    default LocalDateTime getLoanStartDate(String loanType) {
        return switch (loanType) {
            case "MORTGAGE", "BUSINESS" -> LocalDateTime.now().plusDays(30);
            case "PERSONAL" -> LocalDateTime.now().plusDays(15);
            case "AUTO" -> LocalDateTime.now().plusDays(7);
            default -> throw new IllegalArgumentException("Invalid loan type: " + loanType);
        };
    }

    @Named("getLoanEndDate")
    default LocalDateTime getLoanEndDate(String loanType) {
        return switch (loanType) {
            case "MORTGAGE" -> LocalDateTime.now().plusYears(30);
            case "PERSONAL" -> LocalDateTime.now().plusYears(10);
            case "AUTO" -> LocalDateTime.now().plusYears(7);
            case "BUSINESS" -> LocalDateTime.now().plusYears(25);
            default -> throw new IllegalArgumentException("Invalid loan type: " + loanType);
        };
    }

    @Named("getLoanStartDate")
    default float getInterestRate(String loanType) {

        return switch (loanType) {
            case "MORTGAGE" -> 0.065f;
            case "PERSONAL" -> 0.120f;
            case "AUTO" -> 0.090f;
            case "BUSINESS" -> 0.150f;
            default -> throw new IllegalArgumentException("Invalid loan type: " + loanType);
        };
    }
}
