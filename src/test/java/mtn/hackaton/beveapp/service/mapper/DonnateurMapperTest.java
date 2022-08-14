package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonnateurMapperTest {

    private DonnateurMapper donnateurMapper;

    @BeforeEach
    public void setUp() {
        donnateurMapper = new DonnateurMapperImpl();
    }
}
