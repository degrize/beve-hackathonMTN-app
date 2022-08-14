package mtn.hackaton.beveapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieCreateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieCreateurDTO.class);
        CategorieCreateurDTO categorieCreateurDTO1 = new CategorieCreateurDTO();
        categorieCreateurDTO1.setId(1L);
        CategorieCreateurDTO categorieCreateurDTO2 = new CategorieCreateurDTO();
        assertThat(categorieCreateurDTO1).isNotEqualTo(categorieCreateurDTO2);
        categorieCreateurDTO2.setId(categorieCreateurDTO1.getId());
        assertThat(categorieCreateurDTO1).isEqualTo(categorieCreateurDTO2);
        categorieCreateurDTO2.setId(2L);
        assertThat(categorieCreateurDTO1).isNotEqualTo(categorieCreateurDTO2);
        categorieCreateurDTO1.setId(null);
        assertThat(categorieCreateurDTO1).isNotEqualTo(categorieCreateurDTO2);
    }
}
