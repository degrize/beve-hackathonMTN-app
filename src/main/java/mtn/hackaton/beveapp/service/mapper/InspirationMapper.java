package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.Inspiration;
import mtn.hackaton.beveapp.service.dto.InspirationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inspiration} and its DTO {@link InspirationDTO}.
 */
@Mapper(componentModel = "spring")
public interface InspirationMapper extends EntityMapper<InspirationDTO, Inspiration> {}
