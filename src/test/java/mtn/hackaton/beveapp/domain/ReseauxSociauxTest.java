package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReseauxSociauxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReseauxSociaux.class);
        ReseauxSociaux reseauxSociaux1 = new ReseauxSociaux();
        reseauxSociaux1.setId(1L);
        ReseauxSociaux reseauxSociaux2 = new ReseauxSociaux();
        reseauxSociaux2.setId(reseauxSociaux1.getId());
        assertThat(reseauxSociaux1).isEqualTo(reseauxSociaux2);
        reseauxSociaux2.setId(2L);
        assertThat(reseauxSociaux1).isNotEqualTo(reseauxSociaux2);
        reseauxSociaux1.setId(null);
        assertThat(reseauxSociaux1).isNotEqualTo(reseauxSociaux2);
    }
}
