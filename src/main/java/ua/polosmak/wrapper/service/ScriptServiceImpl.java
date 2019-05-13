package ua.polosmak.wrapper.service;

import org.springframework.stereotype.Service;
import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.ScriptServiceException;
import ua.polosmak.wrapper.executor.ScriptExecutor;
import ua.polosmak.wrapper.executor.ScriptExecutorHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Resource(name = "${handler}")
    private ScriptExecutorHandler executorHandler;

    @Override
    public List<Script> getAllScripts() {
        List<Script> scripts = new ArrayList<>();
        List<ScriptExecutor> executors = executorHandler.getAllScriptExecutors();
        for (ScriptExecutor executor : executors) {
            scripts.add(executor.getScript());
        }
        return scripts;
    }

    @Override
    public Script addScript(Script script) throws Exception {
        ScriptExecutor scriptExecutor = executorHandler.addAndExecuteScript(script);
        return scriptExecutor.getScript();
    }

    @Override
    public Script getScriptById(String scriptId) throws ScriptServiceException {
        ScriptExecutor scriptExecutor = executorHandler.getScriptExecutorById(scriptId);
        return scriptExecutor.getScript();
    }

    @Override
    public void deleteScript(String scriptId) throws ScriptServiceException {
        executorHandler.stopExecutorScript(scriptId);
    }
}
