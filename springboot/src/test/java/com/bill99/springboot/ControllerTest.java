package com.bill99.springboot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApp.class)
@WebAppConfiguration
public class ControllerTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private WebApplicationContext context;
	private MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

	@Test
    public void home() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/test/show").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("@@ControllerTest home result = {}", result.getResponse().getContentAsString());
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode, 200);
    }

}
