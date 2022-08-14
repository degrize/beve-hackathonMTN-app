package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.Transaction;
import mtn.hackaton.beveapp.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {}
