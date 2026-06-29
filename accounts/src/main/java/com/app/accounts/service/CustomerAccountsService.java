package com.app.accounts.service;

import com.app.accounts.dto.CustomerAccountDto;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.dto.PatchCustomerAccountDto;
import com.app.accounts.entity.AccountsEntity;
import com.app.accounts.entity.CustomersEntity;
import com.app.accounts.mapper.CustomerAccountsMapper;
import com.app.accounts.repository.AccountsRepository;
import com.app.accounts.repository.CustomersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerAccountsService {

    private final CustomersRepository customersRepository;
    private final AccountsRepository accountsRepository;
    private final CustomerAccountsMapper customerAccountsMapper;
    private final ObjectMapper objectMapper;

    public void addNewCustomer(CustomerDto customer) {

        CustomersEntity customerEntity = customerAccountsMapper.toCustomerEntity(customer);
        AccountsEntity accountEntity = customerAccountsMapper.toAccountEntity(customer);
        //before saving, set the rest of the details of the entities
        customerEntity.setUpdatedBy("SYSTEM");
        Long accountId = ThreadLocalRandom.current().nextLong(10_000, 1_000_000); // Generates a random number between 10,000 and 999,999
        Long accNum = ThreadLocalRandom.current().nextLong(100_000_000, 1_000_000_000);
        long routingNum = ThreadLocalRandom.current().nextLong(10_000, 999_999);
        accountEntity.setAccountId(accountId);
        accountEntity.setAccountNumber(accNum + "-" + routingNum);
        customersRepository.save(customerEntity);
        accountEntity.setCustomerId(customerEntity.getCustomerId());
        accountEntity.setUpdatedBy("SYSTEM");
        accountsRepository.save(accountEntity);
    }

    public CustomerAccountDto findCustomerById(Long customerId) {
        return customerAccountsMapper.toCustomerAccountDto(customersRepository.findById(customerId).orElse(null), accountsRepository.findFirstByCustomerId(customerId).orElse(null));
    }

    //    @SneakyThrows//Throws JsonPatchException
    public void patchCustomerDetail(Long accountId, JsonPatch patchCustomerAccountDtl) {
        AccountsEntity accountEntity = accountsRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Customer Account not found !"));
        CustomersEntity customerEntity = customersRepository.findById(accountEntity.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer Detail not found !"));
        //convert entities to dto -> PatchCustomerAccountDto
        PatchCustomerAccountDto patchCustomerAccountDto = customerAccountsMapper.toPatchCustomerAccountDto(customerEntity, accountEntity);
        //convert PatchCustomerAccountDto to JsonNode
        JsonNode targetCustAccountNode = objectMapper.convertValue(patchCustomerAccountDto, JsonNode.class);
        //Apply instructions (op, path, value) to the target JsonNode
        JsonNode patchedCustAccountNode;
        PatchCustomerAccountDto patchedCustAccountDto;
        CustomersEntity patchedCustomerEntity;
        AccountsEntity patchedAccountEntity;
        try {

            //Convert JsonNode back to your DTO Object
            patchedCustAccountNode = patchCustomerAccountDtl.apply(targetCustAccountNode);
            patchedCustAccountDto = objectMapper.treeToValue(patchedCustAccountNode, PatchCustomerAccountDto.class);
            //convert/Map patched Dto to entities
            patchedCustomerEntity = customerAccountsMapper.toCustomerEntity(patchedCustAccountDto, customerEntity);
            patchedAccountEntity = customerAccountsMapper.toAccountEntity(patchedCustAccountDto, accountEntity);
            customersRepository.save(patchedCustomerEntity);
            accountsRepository.save(patchedAccountEntity);

        } catch (JsonPatchException | JsonProcessingException e) {
            log.error("Error while patching customer account details: ", e);
            throw new RuntimeException(e);
        }
    }
}
