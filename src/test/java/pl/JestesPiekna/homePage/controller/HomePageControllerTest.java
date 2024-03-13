package pl.JestesPiekna.homePage.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@WebMvcTest(HomePageController.class)
class HomePageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testShowHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/YouAreBeautiful"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("homePageAll"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testShowMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/securedMainPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("homePage"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testShowHomePageWithDifferentPath() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/DifferentPath"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}