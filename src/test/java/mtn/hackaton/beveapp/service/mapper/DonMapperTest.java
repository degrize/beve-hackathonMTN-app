package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonMapperTest {

    private DonMapper donMapper;

    @BeforeEach
    public void setUp() {
        donMapper = new DonMapperImpl();
    }
}
