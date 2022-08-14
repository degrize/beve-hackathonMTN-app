package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InspirationMapperTest {

    private InspirationMapper inspirationMapper;

    @BeforeEach
    public void setUp() {
        inspirationMapper = new InspirationMapperImpl();
    }
}
