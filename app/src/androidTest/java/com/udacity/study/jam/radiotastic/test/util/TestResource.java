/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.test.util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author Tom Koptel
 * @since 1.9
 */
public class TestResource {
    public static enum DataFormat {
        JSON, XML;
    }

    private final DataFormat mFormat;

    public static TestResource get(DataFormat format) {
        return new TestResource(format);
    }

    public static TestResource getXml() {
        return new TestResource(DataFormat.XML);
    }

    public static TestResource getJson() {
        return new TestResource(DataFormat.JSON);
    }

    private TestResource(DataFormat format) {
        mFormat = format;
    }

    public <T> T from(Class<T> clazz, String fileName) {
        if (mFormat == DataFormat.XML) {
            return fromXML(clazz, fileName);
        }
        if (mFormat == DataFormat.JSON) {
            return fromXML(clazz, fileName);
        }
        throw new UnsupportedOperationException();
    }

    public String rawData(String fileName) {
        InputStream inputStream = getStream(fileName);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return writer.toString();
    }

    public InputStream getStream(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName + "." + mFormat.toString().toLowerCase());
    }

    public byte[] getBytes(String fileName) throws IOException {
        InputStream stream = getStream(fileName);
        try {
            return IOUtils.toByteArray(stream);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public <T> T fromXML(Class<T> clazz, String fileName) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}