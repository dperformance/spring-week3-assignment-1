package com.codesoom.assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    void AppTest() {
        App app = new App();
        assertThat(app.getGreeting()).isNotNull();
        assertThat(app.getGreeting()).isEqualTo("Hello, world!");
    }

}