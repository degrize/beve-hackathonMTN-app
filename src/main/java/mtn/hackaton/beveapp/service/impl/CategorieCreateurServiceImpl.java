package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.CategorieCreateur;
import mtn.hackaton.beveapp.repository.CategorieCreateurRepository;
import mtn.hackaton.beveapp.service.CategorieCreateurService;
import mtn.hackaton.beveapp.service.dto.CategorieCreateurDTO;
import mtn.hackaton.beveapp.service.mapper.CategorieCreateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieCreateur}.
 */
@Service
@Transactional
public class CategorieCreateurServiceImpl implements CategorieCreateurService {

    private final Logger log = LoggerFactory.getLogger(CategorieCreateurServiceImpl.class);

    private final CategorieCreateurRepository categorieCreateurRepository;

    private final CategorieCreateurMapper categorieCreateurMapper;

    public CategorieCreateurServiceImpl(
        CategorieCreateurRepository categorieCreateurRepository,
        CategorieCreateurMapper categorieCreateurMapper
    ) {
        this.categorieCreateurRepository = categorieCreateurRepository;
        this.categorieCreateurMapper = categorieCreateurMapper;
    }

    @Override
    public CategorieCreateurDTO save(CategorieCreateurDTO categorieCreateurDTO) {
        log.debug("Request to save CategorieCreateur : {}", categorieCreateurDTO);
        CategorieCreateur categorieCreateur = categorieCreateurMapper.toEntity(categorieCreateurDTO);
        categorieCreateur = categorieCreateurRepository.save(categorieCreateur);
        return categorieCreateurMapper.toDto(categorieCreateur);
    }

    @Override
    public CategorieCreateurDTO update(CategorieCreateurDTO categorieCreateurDTO) {
        log.debug("Request to save CategorieCreateur : {}", categorieCreateurDTO);
        CategorieCreateur categorieCreateur = categorieCreateurMapper.toEntity(categorieCreateurDTO);
        categorieCreateur = categorieCreateurRepository.save(categorieCreateur);
        return categorieCreateurMapper.toDto(categorieCreateur);
    }

    @Override
    public Optional<CategorieCreateurDTO> partialUpdate(CategorieCreateurDTO categorieCreateurDTO) {
        log.debug("Request to partially update CategorieCreateur : {}", categorieCreateurDTO);

        return categorieCreateurRepository
            .findById(categorieCreateurDTO.getId())
            .map(existingCategorieCreateur -> {
                categorieCreateurMapper.partialUpdate(existingCategorieCreateur, categorieCreateurDTO);

                return existingCategorieCreateur;
            })
            .map(categorieCreateurRepository::save)
            .map(categorieCreateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieCreateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieCreateurs");
        return categorieCreateurRepository.findAll(pageable).map(categorieCreateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategorieCreateurDTO> findOne(Long id) {
        log.debug("Request to get CategorieCreateur : {}", id);
        return categorieCreateurRepository.findById(id).map(categorieCreateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieCreateur : {}", id);
        categorieCreateurRepository.deleteById(id);
    }
}
