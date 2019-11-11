package com.lp2.sigmonio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestUnitTest {
    private int a;
    private int b;

    private TestUnit test = new TestUnit();

    @BeforeEach
    void setUp() {
        a = 1;
        b = 1;
    }

    @Test
    void testSum() {
        assertEquals(2,test.sum(a,b));
    }

    @Test
    void testEquals() {
        assertTrue(test.equals(a,b));
    }
}