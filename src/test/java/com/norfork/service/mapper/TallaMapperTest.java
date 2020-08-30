package com.norfork.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TallaMapperTest {

    private TallaMapper tallaMapper;

    @BeforeEach
    public void setUp() {
        tallaMapper = new TallaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(tallaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tallaMapper.fromId(null)).isNull();
    }
}
