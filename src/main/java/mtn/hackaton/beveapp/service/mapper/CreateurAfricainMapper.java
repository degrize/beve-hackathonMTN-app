package mtn.hackaton.beveapp.service.mapper;

import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreateurAfricain} and its DTO {@link CreateurAfricainDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreateurAfricainMapper extends EntityMapper<CreateurAfricainDTO, CreateurAfricain> {}
