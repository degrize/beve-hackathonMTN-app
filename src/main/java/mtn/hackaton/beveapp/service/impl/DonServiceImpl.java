package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.Don;
import mtn.hackaton.beveapp.repository.DonRepository;
import mtn.hackaton.beveapp.service.DonService;
import mtn.hackaton.beveapp.service.dto.DonDTO;
import mtn.hackaton.beveapp.service.mapper.DonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Don}.
 */
@Service
@Transactional
public class DonServiceImpl implements DonService {

    private final Logger log = LoggerFactory.getLogger(DonServiceImpl.class);

    private final DonRepository donRepository;

    private final DonMapper donMapper;

    public DonServiceImpl(DonRepository donRepository, DonMapper donMapper) {
        this.donRepository = donRepository;
        this.donMapper = donMapper;
    }

    @Override
    public DonDTO save(DonDTO donDTO) {
        log.debug("Request to save Don : {}", donDTO);
        Don don = donMapper.toEntity(donDTO);
        don = donRepository.save(don);
        return donMapper.toDto(don);
    }

    @Override
    public DonDTO update(DonDTO donDTO) {
        log.debug("Request to save Don : {}", donDTO);
        Don don = donMapper.toEntity(donDTO);
        don = donRepository.save(don);
        return donMapper.toDto(don);
    }

    @Override
    public Optional<DonDTO> partialUpdate(DonDTO donDTO) {
        log.debug("Request to partially update Don : {}", donDTO);

        return donRepository
            .findById(donDTO.getId())
            .map(existingDon -> {
                donMapper.partialUpdate(existingDon, donDTO);

                return existingDon;
            })
            .map(donRepository::save)
            .map(donMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dons");
        return donRepository.findAll(pageable).map(donMapper::toDto);
    }

    public Page<DonDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donRepository.findAllWithEagerRelationships(pageable).map(donMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonDTO> findOne(Long id) {
        log.debug("Request to get Don : {}", id);
        return donRepository.findOneWithEagerRelationships(id).map(donMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Don : {}", id);
        donRepository.deleteById(id);
    }
}
