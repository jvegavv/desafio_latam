package com.example.latam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TemaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void function_revisa_base_de_datos() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/api/mensaje/tema/-1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    }
    
}
