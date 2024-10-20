package dev.mosaleh.Library.Management.System.config;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigurationTest {

    @Autowired
    private static MockMvc mockMvc;

    @Test
    public void testUnsecuredPaths() throws Exception {
        // Test that /api/auth/** paths are accessible without authentication
        mockMvc.perform(get("/api/auth/signup"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/auth/signin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testSecuredPaths() throws Exception {
        // Test a secured path (you would need to add a secured path to the security config)
        mockMvc.perform(get("/api/secured"))
                .andExpect(status().isForbidden());
    }
}
