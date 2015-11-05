package com.app.radiotastic.presentation.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Tom Koptel
 */
public class StationModelTest {
    @Test
    public void verifyEqualsContract() {
        EqualsVerifier.forClass(StationModel.class).verify();
    }
}