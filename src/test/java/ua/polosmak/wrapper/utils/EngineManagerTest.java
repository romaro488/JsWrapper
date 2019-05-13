package ua.polosmak.wrapper.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.polosmak.wrapper.JsWrapperApplication;

import javax.script.ScriptEngine;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JsWrapperApplication.class)
public class EngineManagerTest {
    private static final String WRONG_SCRIPT = "---";
    private static final String TEST_SCRIPT = "print('test')";
    @Autowired
    private EngineManager engineManager;

    public void shouldGetEngine() throws Exception {
        assertNotNull(engineManager.getEngine());
    }

    @Test
    public void shouldCompileFailed() throws Exception {
        ScriptEngine scriptEngine = engineManager.getEngine();
        assertFalse(engineManager.compile(WRONG_SCRIPT, scriptEngine));
    }

    @Test
    public void shouldCompileSuccess() throws Exception {
        ScriptEngine scriptEngine = engineManager.getEngine();
        assertTrue(engineManager.compile(TEST_SCRIPT, scriptEngine));
    }
}