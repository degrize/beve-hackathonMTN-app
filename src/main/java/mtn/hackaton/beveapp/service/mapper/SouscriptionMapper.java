package mtn.hackaton.beveapp.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.domain.Souscription;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import mtn.hackaton.beveapp.service.dto.SouscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Souscription} and its DTO {@link SouscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SouscriptionMapper extends EntityMapper<SouscriptionDTO, Souscription> {
    @Mapping(target = "createurAfricains", source = "createurAfricains", qualifiedByName = "createurAfricainLabelSet")
    SouscriptionDTO toDto(Souscription s);

    @Mapping(target = "removeCreateurAfricain", ignore = true)
    Souscription toEntity(SouscriptionDTO souscriptionDTO);

    @Named("createurAfricainLabel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    CreateurAfricainDTO toDtoCreateurAfricainLabel(CreateurAfricain createurAfricain);

    @Named("createurAfricainLabelSet")
    default Set<CreateurAfricainDTO> toDtoCreateurAfricainLabelSet(Set<CreateurAfricain> createurAfricain) {
        return createurAfricain.stream().map(this::toDtoCreateurAfricainLabel).collect(Collectors.toSet());
    }
}
