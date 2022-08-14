package mtn.hackaton.beveapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.domain.Inspiration;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import mtn.hackaton.beveapp.service.dto.InspirationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreateurAfricain} and its DTO {@link CreateurAfricainDTO}.
 */
@Mapper(componentModel = "spring")
public interface CreateurAfricainMapper extends EntityMapper<CreateurAfricainDTO, CreateurAfricain> {
    @Mapping(target = "inspirations", source = "inspirations", qualifiedByName = "inspirationNomSet")
    CreateurAfricainDTO toDto(CreateurAfricain s);

    @Mapping(target = "removeInspiration", ignore = true)
    CreateurAfricain toEntity(CreateurAfricainDTO createurAfricainDTO);

    @Named("inspirationNom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    InspirationDTO toDtoInspirationNom(Inspiration inspiration);

    @Named("inspirationNomSet")
    default Set<InspirationDTO> toDtoInspirationNomSet(Set<Inspiration> inspiration) {
        return inspiration.stream().map(this::toDtoInspirationNom).collect(Collectors.toSet());
    }
}
