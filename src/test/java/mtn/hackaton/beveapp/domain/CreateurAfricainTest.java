package mtn.hackaton.beveapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mtn.hackaton.beveapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreateurAfricainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreateurAfricain.class);
        CreateurAfricain createurAfricain1 = new CreateurAfricain();
        createurAfricain1.setId(1L);
        CreateurAfricain createurAfricain2 = new CreateurAfricain();
        createurAfricain2.setId(createurAfricain1.getId());
        assertThat(createurAfricain1).isEqualTo(createurAfricain2);
        createurAfricain2.setId(2L);
        assertThat(createurAfricain1).isNotEqualTo(createurAfricain2);
        createurAfricain1.setId(null);
        assertThat(createurAfricain1).isNotEqualTo(createurAfricain2);
    }
}
