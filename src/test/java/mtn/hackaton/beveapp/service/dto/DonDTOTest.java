package mtn.hackaton.beveapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonDTO.class);
        DonDTO donDTO1 = new DonDTO();
        donDTO1.setId(1L);
        DonDTO donDTO2 = new DonDTO();
        assertThat(donDTO1).isNotEqualTo(donDTO2);
        donDTO2.setId(donDTO1.getId());
        assertThat(donDTO1).isEqualTo(donDTO2);
        donDTO2.setId(2L);
        assertThat(donDTO1).isNotEqualTo(donDTO2);
        donDTO1.setId(null);
        assertThat(donDTO1).isNotEqualTo(donDTO2);
    }
}
