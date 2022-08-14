package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieCreateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieCreateur.class);
        CategorieCreateur categorieCreateur1 = new CategorieCreateur();
        categorieCreateur1.setId(1L);
        CategorieCreateur categorieCreateur2 = new CategorieCreateur();
        categorieCreateur2.setId(categorieCreateur1.getId());
        assertThat(categorieCreateur1).isEqualTo(categorieCreateur2);
        categorieCreateur2.setId(2L);
        assertThat(categorieCreateur1).isNotEqualTo(categorieCreateur2);
        categorieCreateur1.setId(null);
        assertThat(categorieCreateur1).isNotEqualTo(categorieCreateur2);
    }
}
