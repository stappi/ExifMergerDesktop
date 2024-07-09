package com.stappi.exifmergerdesktop.utilities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UtilitiesTest {

    @Test
    public void testIsEmpty() {
        assertTrue(Utilities.isEmpty(null));
        assertTrue(Utilities.isEmpty(""));
        assertTrue(Utilities.isEmpty(" "));
        assertFalse(Utilities.isEmpty("a"));
    }
}
