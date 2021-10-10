/*
package ru.andreychuk.questionnairesManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.andreychuk.questionnairesManager.controllers.ViewQuestionnairesController;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "admin")
public class ViewQuestionnaireControllerTest {
    @Autowired
    private ViewQuestionnairesController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/view"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath(".//h1").string("Available Questionnaires"));
    }

    @Test
    public void questionnaireListTest() throws Exception {
        this.mockMvc.perform(get("/view"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath(".//ul[contains(@class, 'questionnaires')]").nodeCount(3));
    }

}
*/
