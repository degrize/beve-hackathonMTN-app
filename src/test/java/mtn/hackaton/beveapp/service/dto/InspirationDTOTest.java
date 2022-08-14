package mtn.hackaton.beveapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspirationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspirationDTO.class);
        InspirationDTO inspirationDTO1 = new InspirationDTO();
        inspirationDTO1.setId(1L);
        InspirationDTO inspirationDTO2 = new InspirationDTO();
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
        inspirationDTO2.setId(inspirationDTO1.getId());
        assertThat(inspirationDTO1).isEqualTo(inspirationDTO2);
        inspirationDTO2.setId(2L);
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
        inspirationDTO1.setId(null);
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
    }
}
