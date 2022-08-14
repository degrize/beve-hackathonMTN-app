package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.ReseauxSociaux;
import mtn.hackaton.beveapp.service.dto.ReseauxSociauxDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReseauxSociaux} and its DTO {@link ReseauxSociauxDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReseauxSociauxMapper extends EntityMapper<ReseauxSociauxDTO, ReseauxSociaux> {}
