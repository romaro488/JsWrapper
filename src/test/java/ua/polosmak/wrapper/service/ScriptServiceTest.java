package ua.polosmak.wrapper.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.polosmak.wrapper.JsWrapperApplication;
import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.ScriptServiceException;
import ua.polosmak.wrapper.utils.Constant;
import ua.polosmak.wrapper.utils.IdGeneratorUtils;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JsWrapperApplication.class)
public class ScriptServiceTest {

    private static final String TEST_UUID_FOR_ADD = "550e8400-e29b-41d4-a716-446655440000";
    private static final String TEST_UUID_FOR_REMOVE = "550e8400-e29b-41d4-a716-446655440001";
    private static final String TEST_UUID_FOR_GET_BY_ID = "550e8400-e29b-41d4-a716-446655440002";
    private static final Script TEST_SCRIPT_FOR_ADD = new Script();
    private static final Script TEST_SCRIPT_FOR_REMOVE = new Script();
    private static final Script TEST_SCRIPT_FOR_GET_BY_ID = new Script();
    private static final String TEST_SCRIPT = "print('test')";
    @Autowired
    private ScriptService service;

    @Before
    public void before() throws Exception {
        TEST_SCRIPT_FOR_ADD.setId(TEST_UUID_FOR_ADD);
        TEST_SCRIPT_FOR_ADD.setScript(TEST_SCRIPT);
        TEST_SCRIPT_FOR_REMOVE.setId(TEST_UUID_FOR_REMOVE);
        TEST_SCRIPT_FOR_REMOVE.setScript(TEST_SCRIPT);
        TEST_SCRIPT_FOR_GET_BY_ID.setId(TEST_UUID_FOR_GET_BY_ID);
        TEST_SCRIPT_FOR_GET_BY_ID.setScript(TEST_SCRIPT);
        service.addScript(TEST_SCRIPT_FOR_GET_BY_ID);
        for (int i = 0; i < 8; i++) {
            Script jscript = new Script();
            jscript.setId(IdGeneratorUtils.generateScriptId());
            jscript.setScript(TEST_SCRIPT);
            service.addScript(jscript);
        }
    }

    @Test
    public void shouldReturnAllScripts() throws Exception {
        assertTrue(service.getAllScripts().size() > 0);
    }

    @Test
    public void shouldExecuteScript() throws Exception {
        service.addScript(TEST_SCRIPT_FOR_ADD);
        Thread.sleep(500);
        assertEquals(Constant.STATUS_FINISH, service.getScriptById(TEST_UUID_FOR_ADD).getStatus());
    }

    @Test(expected = ScriptServiceException.class)
    public void shouldRemoveScript() throws Exception {
        service.addScript(TEST_SCRIPT_FOR_REMOVE);
        Thread.sleep(100);
        service.deleteScript(TEST_UUID_FOR_REMOVE);
        service.getScriptById(TEST_UUID_FOR_REMOVE);
    }

    @Test
    public void shouldReturnScriptById() throws Exception {
        assertNotNull(service.getScriptById(TEST_UUID_FOR_GET_BY_ID));
    }
}