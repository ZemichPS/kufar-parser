package by.zemich.kufar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("prod")
public class EurekaClientTest {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
    public void testEurekaClientInitialization() {
        assertNotNull(discoveryClient);
    }
}
