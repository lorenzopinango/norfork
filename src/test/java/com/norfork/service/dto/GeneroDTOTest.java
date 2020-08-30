package com.norfork.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.norfork.web.rest.TestUtil;

public class GeneroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneroDTO.class);
        GeneroDTO generoDTO1 = new GeneroDTO();
        generoDTO1.setId(1L);
        GeneroDTO generoDTO2 = new GeneroDTO();
        assertThat(generoDTO1).isNotEqualTo(generoDTO2);
        generoDTO2.setId(generoDTO1.getId());
        assertThat(generoDTO1).isEqualTo(generoDTO2);
        generoDTO2.setId(2L);
        assertThat(generoDTO1).isNotEqualTo(generoDTO2);
        generoDTO1.setId(null);
        assertThat(generoDTO1).isNotEqualTo(generoDTO2);
    }
}
