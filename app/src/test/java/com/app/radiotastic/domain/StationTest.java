package com.app.radiotastic.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Tom Koptel
 */
public class StationTest {
    @Test
    public void verifyEqualsContract() {
        EqualsVerifier.forClass(Station.class).verify();
    }
}