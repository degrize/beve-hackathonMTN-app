package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureCreateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureCreateur.class);
        NatureCreateur natureCreateur1 = new NatureCreateur();
        natureCreateur1.setId(1L);
        NatureCreateur natureCreateur2 = new NatureCreateur();
        natureCreateur2.setId(natureCreateur1.getId());
        assertThat(natureCreateur1).isEqualTo(natureCreateur2);
        natureCreateur2.setId(2L);
        assertThat(natureCreateur1).isNotEqualTo(natureCreateur2);
        natureCreateur1.setId(null);
        assertThat(natureCreateur1).isNotEqualTo(natureCreateur2);
    }
}
