package org.cb.minilms.config.security.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class VaultClientSecret {

    @Value("${vault.client.id.client.admin}")
    private String adminClientId;

    @Value("${vault.client.secrets.client.admin}")
    private String adminClientSecret;

    @Value("${vault.client.id.client.user}")
    private String userClientId;

    @Value("${vault.client.secrets.client.user}")
    private String userClientSecret;

    private Map<String, String> clientSecretsById;

    @PostConstruct
    public void init() {
        clientSecretsById = new HashMap<>();
        clientSecretsById.put(adminClientId, adminClientSecret);
        clientSecretsById.put(userClientId, userClientSecret);
    }

    public String getSecretByClientId(String clientId) {
        return clientSecretsById.get(clientId);
    }

    public Map<String, String> getAllClientSecrets() {
        return clientSecretsById;
    }

    public String getAdminClientId() {
        return adminClientId;
    }

    public String getUserClientSecret() {
        return userClientSecret;
    }
}