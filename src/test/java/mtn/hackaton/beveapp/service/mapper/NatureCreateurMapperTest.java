package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NatureCreateurMapperTest {

    private NatureCreateurMapper natureCreateurMapper;

    @BeforeEach
    public void setUp() {
        natureCreateurMapper = new NatureCreateurMapperImpl();
    }
}
