package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonnateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donnateur.class);
        Donnateur donnateur1 = new Donnateur();
        donnateur1.setId(1L);
        Donnateur donnateur2 = new Donnateur();
        donnateur2.setId(donnateur1.getId());
        assertThat(donnateur1).isEqualTo(donnateur2);
        donnateur2.setId(2L);
        assertThat(donnateur1).isNotEqualTo(donnateur2);
        donnateur1.setId(null);
        assertThat(donnateur1).isNotEqualTo(donnateur2);
    }
}
