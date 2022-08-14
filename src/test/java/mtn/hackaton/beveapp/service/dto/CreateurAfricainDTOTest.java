package mtn.hackaton.beveapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreateurAfricainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreateurAfricainDTO.class);
        CreateurAfricainDTO createurAfricainDTO1 = new CreateurAfricainDTO();
        createurAfricainDTO1.setId(1L);
        CreateurAfricainDTO createurAfricainDTO2 = new CreateurAfricainDTO();
        assertThat(createurAfricainDTO1).isNotEqualTo(createurAfricainDTO2);
        createurAfricainDTO2.setId(createurAfricainDTO1.getId());
        assertThat(createurAfricainDTO1).isEqualTo(createurAfricainDTO2);
        createurAfricainDTO2.setId(2L);
        assertThat(createurAfricainDTO1).isNotEqualTo(createurAfricainDTO2);
        createurAfricainDTO1.setId(null);
        assertThat(createurAfricainDTO1).isNotEqualTo(createurAfricainDTO2);
    }
}
