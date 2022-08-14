package mtn.hackaton.beveapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureCreateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureCreateurDTO.class);
        NatureCreateurDTO natureCreateurDTO1 = new NatureCreateurDTO();
        natureCreateurDTO1.setId(1L);
        NatureCreateurDTO natureCreateurDTO2 = new NatureCreateurDTO();
        assertThat(natureCreateurDTO1).isNotEqualTo(natureCreateurDTO2);
        natureCreateurDTO2.setId(natureCreateurDTO1.getId());
        assertThat(natureCreateurDTO1).isEqualTo(natureCreateurDTO2);
        natureCreateurDTO2.setId(2L);
        assertThat(natureCreateurDTO1).isNotEqualTo(natureCreateurDTO2);
        natureCreateurDTO1.setId(null);
        assertThat(natureCreateurDTO1).isNotEqualTo(natureCreateurDTO2);
    }
}
