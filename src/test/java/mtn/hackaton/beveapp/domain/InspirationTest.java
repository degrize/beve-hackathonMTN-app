package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspirationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspiration.class);
        Inspiration inspiration1 = new Inspiration();
        inspiration1.setId(1L);
        Inspiration inspiration2 = new Inspiration();
        inspiration2.setId(inspiration1.getId());
        assertThat(inspiration1).isEqualTo(inspiration2);
        inspiration2.setId(2L);
        assertThat(inspiration1).isNotEqualTo(inspiration2);
        inspiration1.setId(null);
        assertThat(inspiration1).isNotEqualTo(inspiration2);
    }
}
