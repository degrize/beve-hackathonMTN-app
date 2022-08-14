package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.ReseauxSociaux;
import mtn.hackaton.beveapp.repository.ReseauxSociauxRepository;
import mtn.hackaton.beveapp.service.ReseauxSociauxService;
import mtn.hackaton.beveapp.service.dto.ReseauxSociauxDTO;
import mtn.hackaton.beveapp.service.mapper.ReseauxSociauxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReseauxSociaux}.
 */
@Service
@Transactional
public class ReseauxSociauxServiceImpl implements ReseauxSociauxService {

    private final Logger log = LoggerFactory.getLogger(ReseauxSociauxServiceImpl.class);

    private final ReseauxSociauxRepository reseauxSociauxRepository;

    private final ReseauxSociauxMapper reseauxSociauxMapper;

    public ReseauxSociauxServiceImpl(ReseauxSociauxRepository reseauxSociauxRepository, ReseauxSociauxMapper reseauxSociauxMapper) {
        this.reseauxSociauxRepository = reseauxSociauxRepository;
        this.reseauxSociauxMapper = reseauxSociauxMapper;
    }

    @Override
    public ReseauxSociauxDTO save(ReseauxSociauxDTO reseauxSociauxDTO) {
        log.debug("Request to save ReseauxSociaux : {}", reseauxSociauxDTO);
        ReseauxSociaux reseauxSociaux = reseauxSociauxMapper.toEntity(reseauxSociauxDTO);
        reseauxSociaux = reseauxSociauxRepository.save(reseauxSociaux);
        return reseauxSociauxMapper.toDto(reseauxSociaux);
    }

    @Override
    public ReseauxSociauxDTO update(ReseauxSociauxDTO reseauxSociauxDTO) {
        log.debug("Request to save ReseauxSociaux : {}", reseauxSociauxDTO);
        ReseauxSociaux reseauxSociaux = reseauxSociauxMapper.toEntity(reseauxSociauxDTO);
        reseauxSociaux = reseauxSociauxRepository.save(reseauxSociaux);
        return reseauxSociauxMapper.toDto(reseauxSociaux);
    }

    @Override
    public Optional<ReseauxSociauxDTO> partialUpdate(ReseauxSociauxDTO reseauxSociauxDTO) {
        log.debug("Request to partially update ReseauxSociaux : {}", reseauxSociauxDTO);

        return reseauxSociauxRepository
            .findById(reseauxSociauxDTO.getId())
            .map(existingReseauxSociaux -> {
                reseauxSociauxMapper.partialUpdate(existingReseauxSociaux, reseauxSociauxDTO);

                return existingReseauxSociaux;
            })
            .map(reseauxSociauxRepository::save)
            .map(reseauxSociauxMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReseauxSociauxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReseauxSociauxes");
        return reseauxSociauxRepository.findAll(pageable).map(reseauxSociauxMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReseauxSociauxDTO> findOne(Long id) {
        log.debug("Request to get ReseauxSociaux : {}", id);
        return reseauxSociauxRepository.findById(id).map(reseauxSociauxMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReseauxSociaux : {}", id);
        reseauxSociauxRepository.deleteById(id);
    }
}
