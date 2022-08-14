package mtn.hackaton.beveapp.service.impl;

import java.util.Optional;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.repository.CreateurAfricainRepository;
import mtn.hackaton.beveapp.service.CreateurAfricainService;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import mtn.hackaton.beveapp.service.mapper.CreateurAfricainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreateurAfricain}.
 */
@Service
@Transactional
public class CreateurAfricainServiceImpl implements CreateurAfricainService {

    private final Logger log = LoggerFactory.getLogger(CreateurAfricainServiceImpl.class);

    private final CreateurAfricainRepository createurAfricainRepository;

    private final CreateurAfricainMapper createurAfricainMapper;

    public CreateurAfricainServiceImpl(
        CreateurAfricainRepository createurAfricainRepository,
        CreateurAfricainMapper createurAfricainMapper
    ) {
        this.createurAfricainRepository = createurAfricainRepository;
        this.createurAfricainMapper = createurAfricainMapper;
    }

    @Override
    public CreateurAfricainDTO save(CreateurAfricainDTO createurAfricainDTO) {
        log.debug("Request to save CreateurAfricain : {}", createurAfricainDTO);
        CreateurAfricain createurAfricain = createurAfricainMapper.toEntity(createurAfricainDTO);
        createurAfricain = createurAfricainRepository.save(createurAfricain);
        return createurAfricainMapper.toDto(createurAfricain);
    }

    @Override
    public CreateurAfricainDTO update(CreateurAfricainDTO createurAfricainDTO) {
        log.debug("Request to save CreateurAfricain : {}", createurAfricainDTO);
        CreateurAfricain createurAfricain = createurAfricainMapper.toEntity(createurAfricainDTO);
        createurAfricain = createurAfricainRepository.save(createurAfricain);
        return createurAfricainMapper.toDto(createurAfricain);
    }

    @Override
    public Optional<CreateurAfricainDTO> partialUpdate(CreateurAfricainDTO createurAfricainDTO) {
        log.debug("Request to partially update CreateurAfricain : {}", createurAfricainDTO);

        return createurAfricainRepository
            .findById(createurAfricainDTO.getId())
            .map(existingCreateurAfricain -> {
                createurAfricainMapper.partialUpdate(existingCreateurAfricain, createurAfricainDTO);

                return existingCreateurAfricain;
            })
            .map(createurAfricainRepository::save)
            .map(createurAfricainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreateurAfricainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreateurAfricains");
        return createurAfricainRepository.findAll(pageable).map(createurAfricainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreateurAfricainDTO> findOne(Long id) {
        log.debug("Request to get CreateurAfricain : {}", id);
        return createurAfricainRepository.findById(id).map(createurAfricainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreateurAfricain : {}", id);
        createurAfricainRepository.deleteById(id);
    }
}
