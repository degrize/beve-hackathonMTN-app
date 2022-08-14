package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.domain.Don;
import mtn.hackaton.beveapp.domain.Donnateur;
import mtn.hackaton.beveapp.domain.Transaction;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import mtn.hackaton.beveapp.service.dto.DonDTO;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
import mtn.hackaton.beveapp.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Don} and its DTO {@link DonDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonMapper extends EntityMapper<DonDTO, Don> {
    @Mapping(target = "transaction", source = "transaction", qualifiedByName = "transactionNumeromtn")
    @Mapping(target = "createurAfricain", source = "createurAfricain", qualifiedByName = "createurAfricainLabel")
    @Mapping(target = "donnateur", source = "donnateur", qualifiedByName = "donnateurPrenom")
    DonDTO toDto(Don s);

    @Named("transactionNumeromtn")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeromtn", source = "numeromtn")
    TransactionDTO toDtoTransactionNumeromtn(Transaction transaction);

    @Named("createurAfricainLabel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    CreateurAfricainDTO toDtoCreateurAfricainLabel(CreateurAfricain createurAfricain);

    @Named("donnateurPrenom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "prenom", source = "prenom")
    DonnateurDTO toDtoDonnateurPrenom(Donnateur donnateur);
}
