package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SouscriptionMapperTest {

    private SouscriptionMapper souscriptionMapper;

    @BeforeEach
    public void setUp() {
        souscriptionMapper = new SouscriptionMapperImpl();
    }
}
