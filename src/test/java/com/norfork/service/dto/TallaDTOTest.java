package com.norfork.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.norfork.web.rest.TestUtil;

public class TallaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TallaDTO.class);
        TallaDTO tallaDTO1 = new TallaDTO();
        tallaDTO1.setId(1L);
        TallaDTO tallaDTO2 = new TallaDTO();
        assertThat(tallaDTO1).isNotEqualTo(tallaDTO2);
        tallaDTO2.setId(tallaDTO1.getId());
        assertThat(tallaDTO1).isEqualTo(tallaDTO2);
        tallaDTO2.setId(2L);
        assertThat(tallaDTO1).isNotEqualTo(tallaDTO2);
        tallaDTO1.setId(null);
        assertThat(tallaDTO1).isNotEqualTo(tallaDTO2);
    }
}
