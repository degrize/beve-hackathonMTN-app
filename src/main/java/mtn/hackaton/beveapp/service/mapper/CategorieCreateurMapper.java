package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.CategorieCreateur;
import mtn.hackaton.beveapp.service.dto.CategorieCreateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieCreateur} and its DTO {@link CategorieCreateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieCreateurMapper extends EntityMapper<CategorieCreateurDTO, CategorieCreateur> {}
