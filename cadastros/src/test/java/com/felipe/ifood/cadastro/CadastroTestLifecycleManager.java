package com.felipe.ifood.cadastro;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class CadastroTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();
        return new HashMap<>() {
            private static final long serialVersionUID = -5516700805630796405L;
            {

            put("quarkus.datasource.url", POSTGRES.getJdbcUrl());
            put("quarkus.datasource.username", POSTGRES.getUsername());
            put("quarkus.datasource.password", POSTGRES.getPassword());
        }};
    }

    @Override
    public void stop() {
        if (POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
}
