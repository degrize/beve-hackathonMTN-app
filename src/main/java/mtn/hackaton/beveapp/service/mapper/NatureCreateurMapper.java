package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.NatureCreateur;
import mtn.hackaton.beveapp.service.dto.NatureCreateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NatureCreateur} and its DTO {@link NatureCreateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface NatureCreateurMapper extends EntityMapper<NatureCreateurDTO, NatureCreateur> {}
