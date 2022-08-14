package mtn.hackaton.beveapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieCreateurMapperTest {

    private CategorieCreateurMapper categorieCreateurMapper;

    @BeforeEach
    public void setUp() {
        categorieCreateurMapper = new CategorieCreateurMapperImpl();
    }
}
