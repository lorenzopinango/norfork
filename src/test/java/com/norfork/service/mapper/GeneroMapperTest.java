package com.norfork.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class GeneroMapperTest {

    private GeneroMapper generoMapper;

    @BeforeEach
    public void setUp() {
        generoMapper = new GeneroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(generoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(generoMapper.fromId(null)).isNull();
    }
}
