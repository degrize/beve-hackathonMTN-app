package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReseauxSociauxMapperTest {

    private ReseauxSociauxMapper reseauxSociauxMapper;

    @BeforeEach
    public void setUp() {
        reseauxSociauxMapper = new ReseauxSociauxMapperImpl();
    }
}
