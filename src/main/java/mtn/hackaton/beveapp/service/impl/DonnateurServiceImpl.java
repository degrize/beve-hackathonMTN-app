package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.Donnateur;
import mtn.hackaton.beveapp.repository.DonnateurRepository;
import mtn.hackaton.beveapp.service.DonnateurService;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
import mtn.hackaton.beveapp.service.mapper.DonnateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Donnateur}.
 */
@Service
@Transactional
public class DonnateurServiceImpl implements DonnateurService {

    private final Logger log = LoggerFactory.getLogger(DonnateurServiceImpl.class);

    private final DonnateurRepository donnateurRepository;

    private final DonnateurMapper donnateurMapper;

    public DonnateurServiceImpl(DonnateurRepository donnateurRepository, DonnateurMapper donnateurMapper) {
        this.donnateurRepository = donnateurRepository;
        this.donnateurMapper = donnateurMapper;
    }

    @Override
    public DonnateurDTO save(DonnateurDTO donnateurDTO) {
        log.debug("Request to save Donnateur : {}", donnateurDTO);
        Donnateur donnateur = donnateurMapper.toEntity(donnateurDTO);
        donnateur = donnateurRepository.save(donnateur);
        return donnateurMapper.toDto(donnateur);
    }

    @Override
    public DonnateurDTO update(DonnateurDTO donnateurDTO) {
        log.debug("Request to save Donnateur : {}", donnateurDTO);
        Donnateur donnateur = donnateurMapper.toEntity(donnateurDTO);
        donnateur = donnateurRepository.save(donnateur);
        return donnateurMapper.toDto(donnateur);
    }

    @Override
    public Optional<DonnateurDTO> partialUpdate(DonnateurDTO donnateurDTO) {
        log.debug("Request to partially update Donnateur : {}", donnateurDTO);

        return donnateurRepository
            .findById(donnateurDTO.getId())
            .map(existingDonnateur -> {
                donnateurMapper.partialUpdate(existingDonnateur, donnateurDTO);

                return existingDonnateur;
            })
            .map(donnateurRepository::save)
            .map(donnateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonnateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Donnateurs");
        return donnateurRepository.findAll(pageable).map(donnateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonnateurDTO> findOne(Long id) {
        log.debug("Request to get Donnateur : {}", id);
        return donnateurRepository.findById(id).map(donnateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Donnateur : {}", id);
        donnateurRepository.deleteById(id);
    }
}
