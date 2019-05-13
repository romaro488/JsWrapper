package ua.polosmak.wrapper.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.FailedCompilationException;
import ua.polosmak.wrapper.exception.NoSuchScriptException;
import ua.polosmak.wrapper.service.ScriptService;
import ua.polosmak.wrapper.utils.IdGeneratorUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScriptController.class)
public class ScriptControllerTest {
    private static final String TEST_ID = IdGeneratorUtils.generateScriptId();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScriptService scriptService;

    @Test
    public void shouldGetAllScripts() throws Exception {
        Script script = new Script();
        script.setScript("print('test')");
        List<Script> scripts = new ArrayList<>();
        scripts.add(script);

        given(this.scriptService.getAllScripts()).willReturn(scripts);
        mockMvc.perform(get("/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldStartExecuteScript() throws Exception {
        Script script = new Script();
        script.setScript("print('test')");
        given(this.scriptService.addScript(Matchers.any(Script.class))).willReturn(script);
        mockMvc.perform(post("/add")
                .content("print('test')"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {
        given(this.scriptService.addScript(Matchers.any(Script.class))).willThrow(FailedCompilationException.class);
        mockMvc.perform(post("/add")
                .content("print('test')"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRemoveScriptExecutor() throws Exception {
        Mockito.doNothing().when(scriptService).deleteScript(TEST_ID);
        mockMvc.perform(delete("/delete/" + TEST_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenScriptNotExist() throws Exception {
        Mockito.doThrow(NoSuchScriptException.class).when(scriptService).deleteScript(TEST_ID);
        mockMvc.perform(delete("/delete/" + TEST_ID))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetScriptById() throws Exception {
        Script script = new Script();
        script.setScript("print('test')");
        given(this.scriptService.getScriptById(TEST_ID)).willReturn(script);

        mockMvc.perform(get("/script/" + TEST_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenScriptNotExistById() throws Exception {
        given(this.scriptService.getScriptById(TEST_ID)).willThrow(NoSuchScriptException.class);
        mockMvc.perform(get("/script/" + TEST_ID))
                .andExpect(status().isBadRequest());
    }
}