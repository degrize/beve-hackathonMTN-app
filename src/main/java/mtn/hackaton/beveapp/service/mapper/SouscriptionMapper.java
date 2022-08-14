package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.Souscription;
import mtn.hackaton.beveapp.service.dto.SouscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Souscription} and its DTO {@link SouscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SouscriptionMapper extends EntityMapper<SouscriptionDTO, Souscription> {}
