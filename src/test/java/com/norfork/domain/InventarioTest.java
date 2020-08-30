package com.norfork.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.norfork.web.rest.TestUtil;

public class InventarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventario.class);
        Inventario inventario1 = new Inventario();
        inventario1.setId(1L);
        Inventario inventario2 = new Inventario();
        inventario2.setId(inventario1.getId());
        assertThat(inventario1).isEqualTo(inventario2);
        inventario2.setId(2L);
        assertThat(inventario1).isNotEqualTo(inventario2);
        inventario1.setId(null);
        assertThat(inventario1).isNotEqualTo(inventario2);
    }
}
