package ua.polosmak.wrapper.executor.thread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.FailedCompilationException;
import ua.polosmak.wrapper.exception.NoSuchScriptException;
import ua.polosmak.wrapper.exception.ScriptServiceException;
import ua.polosmak.wrapper.executor.ScriptExecutor;
import ua.polosmak.wrapper.executor.ScriptExecutorHandler;
import ua.polosmak.wrapper.utils.EngineManager;

import javax.script.ScriptEngine;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("threadHandler")
@PropertySource("classpath:config.properties")
public class ScriptExecutorThreadHandler implements ScriptExecutorHandler {
    private static final Logger LOG = Logger.getLogger(ScriptExecutorThreadHandler.class);

    @Autowired
    private EngineManager engineManager;
    private Map<String, ScriptExecutorThread> threads = new ConcurrentHashMap<>();

    @Override
    public List<ScriptExecutor> getAllScriptExecutors() {
        List<ScriptExecutor> scripts = new ArrayList<>(threads.values());
        LOG.debug("Amount of threads: " + scripts.size() + ", was returned");
        return Collections.unmodifiableList(scripts);
    }

    @Override
    public ScriptExecutor addAndExecuteScript(Script script) throws ScriptServiceException {
        ScriptEngine engine = engineManager.getEngine();
        if (!engineManager.compile(script.getScript(), engine)) {
            throw new FailedCompilationException();
        }
        ScriptExecutorThread scriptExecutor = new ScriptExecutorThread(engine, script);
        Thread thread = new Thread(scriptExecutor);
        thread.start();
        threads.put(script.getId(), scriptExecutor);
        LOG.debug("New executor " + thread.getName() + " start executing");
        return scriptExecutor;
    }

    @Override
    public void stopExecutorScript(String scriptId) throws ScriptServiceException {
        ScriptExecutorThread scriptExecutor = threads.get(scriptId);
        if (Objects.isNull(scriptExecutor)) {
            throw new NoSuchScriptException();
        }
        stopThread(scriptExecutor.getCurrentThread());
        threads.remove(scriptId);
        LOG.debug("ScriptExecutor with id: '" + scriptId + "' was removed from handler");
    }

    @Override
    public ScriptExecutor getScriptExecutorById(String scriptId) throws ScriptServiceException {
        ScriptExecutor scriptExecutor = threads.get(scriptId);
        if (Objects.isNull(scriptExecutor)) {
            throw new NoSuchScriptException();
        }
        return scriptExecutor;
    }

    private void stopThread(Thread thread) {
        LOG.debug("Start killing executor " + thread.getName());
        while (thread.isAlive()) {
            System.out.println(thread.isAlive());
            thread.interrupt();
        }
        LOG.debug("Thread " + thread.getName() + " was killed");
    }
}

