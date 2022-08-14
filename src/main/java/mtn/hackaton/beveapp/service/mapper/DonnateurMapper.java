package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.Donnateur;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Donnateur} and its DTO {@link DonnateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonnateurMapper extends EntityMapper<DonnateurDTO, Donnateur> {}
