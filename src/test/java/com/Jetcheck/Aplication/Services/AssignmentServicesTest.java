package com.Jetcheck.Aplication.Services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentServicesTest {

    @BeforeEach
    void setUp() {
        System.out.println("Pruebas Antes del testeo");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Pruebas Despues del testeo");
    }

    @Test
    public void testMethod1(){
        System.out.println("Mi primer metodo de testeo");
    }
}