package com.github.anricx.security.crypto;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dengt on 2019/8/30
 */
public class MyPBPasswordEncoderTest {

    private final PBPasswordEncoder encoder = new PBPasswordEncoder();

    @Test
    public void encode() {
        Assert.assertEquals("/1.2", encoder.encode("0000", "0000", 0));
        Assert.assertEquals("1/2.", encoder.encode("0000", "0000", 1));
        Assert.assertEquals("0/.-", encoder.encode("1234", "0019", 0));
        Assert.assertEquals("0/.-32", encoder.encode("123456", "0019", 0));
        Assert.assertEquals("@?>*0/", encoder.encode("ABC123", "0019", 0));
        Assert.assertEquals("`a^*0/", encoder.encode("adc123", "0019", 0));
        Assert.assertEquals("cbscx", encoder.encode("bcpfs", "admin", 0));
    }

}