package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.NatureCreateur;
import mtn.hackaton.beveapp.repository.NatureCreateurRepository;
import mtn.hackaton.beveapp.service.NatureCreateurService;
import mtn.hackaton.beveapp.service.dto.NatureCreateurDTO;
import mtn.hackaton.beveapp.service.mapper.NatureCreateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureCreateur}.
 */
@Service
@Transactional
public class NatureCreateurServiceImpl implements NatureCreateurService {

    private final Logger log = LoggerFactory.getLogger(NatureCreateurServiceImpl.class);

    private final NatureCreateurRepository natureCreateurRepository;

    private final NatureCreateurMapper natureCreateurMapper;

    public NatureCreateurServiceImpl(NatureCreateurRepository natureCreateurRepository, NatureCreateurMapper natureCreateurMapper) {
        this.natureCreateurRepository = natureCreateurRepository;
        this.natureCreateurMapper = natureCreateurMapper;
    }

    @Override
    public NatureCreateurDTO save(NatureCreateurDTO natureCreateurDTO) {
        log.debug("Request to save NatureCreateur : {}", natureCreateurDTO);
        NatureCreateur natureCreateur = natureCreateurMapper.toEntity(natureCreateurDTO);
        natureCreateur = natureCreateurRepository.save(natureCreateur);
        return natureCreateurMapper.toDto(natureCreateur);
    }

    @Override
    public NatureCreateurDTO update(NatureCreateurDTO natureCreateurDTO) {
        log.debug("Request to save NatureCreateur : {}", natureCreateurDTO);
        NatureCreateur natureCreateur = natureCreateurMapper.toEntity(natureCreateurDTO);
        natureCreateur = natureCreateurRepository.save(natureCreateur);
        return natureCreateurMapper.toDto(natureCreateur);
    }

    @Override
    public Optional<NatureCreateurDTO> partialUpdate(NatureCreateurDTO natureCreateurDTO) {
        log.debug("Request to partially update NatureCreateur : {}", natureCreateurDTO);

        return natureCreateurRepository
            .findById(natureCreateurDTO.getId())
            .map(existingNatureCreateur -> {
                natureCreateurMapper.partialUpdate(existingNatureCreateur, natureCreateurDTO);

                return existingNatureCreateur;
            })
            .map(natureCreateurRepository::save)
            .map(natureCreateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureCreateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NatureCreateurs");
        return natureCreateurRepository.findAll(pageable).map(natureCreateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureCreateurDTO> findOne(Long id) {
        log.debug("Request to get NatureCreateur : {}", id);
        return natureCreateurRepository.findById(id).map(natureCreateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureCreateur : {}", id);
        natureCreateurRepository.deleteById(id);
    }
}
