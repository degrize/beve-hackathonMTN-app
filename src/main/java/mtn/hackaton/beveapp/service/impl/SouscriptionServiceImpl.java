package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.Souscription;
import mtn.hackaton.beveapp.repository.SouscriptionRepository;
import mtn.hackaton.beveapp.service.SouscriptionService;
import mtn.hackaton.beveapp.service.dto.SouscriptionDTO;
import mtn.hackaton.beveapp.service.mapper.SouscriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Souscription}.
 */
@Service
@Transactional
public class SouscriptionServiceImpl implements SouscriptionService {

    private final Logger log = LoggerFactory.getLogger(SouscriptionServiceImpl.class);

    private final SouscriptionRepository souscriptionRepository;

    private final SouscriptionMapper souscriptionMapper;

    public SouscriptionServiceImpl(SouscriptionRepository souscriptionRepository, SouscriptionMapper souscriptionMapper) {
        this.souscriptionRepository = souscriptionRepository;
        this.souscriptionMapper = souscriptionMapper;
    }

    @Override
    public SouscriptionDTO save(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to save Souscription : {}", souscriptionDTO);
        Souscription souscription = souscriptionMapper.toEntity(souscriptionDTO);
        souscription = souscriptionRepository.save(souscription);
        return souscriptionMapper.toDto(souscription);
    }

    @Override
    public SouscriptionDTO update(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to save Souscription : {}", souscriptionDTO);
        Souscription souscription = souscriptionMapper.toEntity(souscriptionDTO);
        souscription = souscriptionRepository.save(souscription);
        return souscriptionMapper.toDto(souscription);
    }

    @Override
    public Optional<SouscriptionDTO> partialUpdate(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to partially update Souscription : {}", souscriptionDTO);

        return souscriptionRepository
            .findById(souscriptionDTO.getId())
            .map(existingSouscription -> {
                souscriptionMapper.partialUpdate(existingSouscription, souscriptionDTO);

                return existingSouscription;
            })
            .map(souscriptionRepository::save)
            .map(souscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SouscriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Souscriptions");
        return souscriptionRepository.findAll(pageable).map(souscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SouscriptionDTO> findOne(Long id) {
        log.debug("Request to get Souscription : {}", id);
        return souscriptionRepository.findById(id).map(souscriptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Souscription : {}", id);
        souscriptionRepository.deleteById(id);
    }
}
