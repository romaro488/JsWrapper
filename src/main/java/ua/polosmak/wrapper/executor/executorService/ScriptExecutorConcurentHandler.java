package ua.polosmak.wrapper.executor.executorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component("executorservice")
@PropertySource("classpath:config.properties")
public class ScriptExecutorConcurentHandler implements ScriptExecutorHandler {
    private static Logger LOG = Logger.getLogger(ScriptExecutorConcurentHandler.class);

    @Value("${timeout}")
    private int timeout;

    @Autowired
    private EngineManager engineManager;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<String, FutureTask<ScriptExecutor>> futureTaskMap = new ConcurrentHashMap<>();
    private List<ScriptExecutorConcurrent> scriptExecutors = new ArrayList<>();

    @Override
    public List<ScriptExecutor> getAllScriptExecutors() {
        List<ScriptExecutor> scripts = new ArrayList<>();
        for (Map.Entry<String, FutureTask<ScriptExecutor>> stringFutureEntry : futureTaskMap.entrySet()) {
            ScriptExecutor scriptExecutor = getScriptExecutorFromFutureTask(stringFutureEntry.getValue(), stringFutureEntry.getKey());
            scripts.add(scriptExecutor);
        }
        LOG.debug("Amount of threads: " + scripts.size() + ", was returned");
        return Collections.unmodifiableList(scripts);
    }

    @Override
    public ScriptExecutor addAndExecuteScript(Script script) throws ScriptServiceException, ExecutionException, InterruptedException {
        ScriptEngine engine = engineManager.getEngine();
        if (!engineManager.compile(script.getScript(), engine)) {
            throw new FailedCompilationException();
        }
        ScriptExecutorConcurrent scriptExecutor = new ScriptExecutorConcurrent(script, engine);
        FutureTask<ScriptExecutor> future = new FutureTask<>(scriptExecutor);
        executorService.execute(future);
        futureTaskMap.put(script.getId(), future);
        scriptExecutors.add(scriptExecutor);
        return future.get();
    }

    @Override
    public void stopExecutorScript(String scriptId) throws ScriptServiceException {
        FutureTask<ScriptExecutor> future = futureTaskMap.get(scriptId);
        if (future == null) {
            throw new NoSuchScriptException();
        }
        stopThread(future);
        scriptExecutors.remove(getScriptExecutorFromListById(scriptId));
        futureTaskMap.remove(scriptId);
        LOG.debug("ScriptExecutor with id: '" + scriptId + "' was removed from handler");
    }

    @Override
    public ScriptExecutor getScriptExecutorById(String scriptId) throws ScriptServiceException {
        FutureTask<ScriptExecutor> future = futureTaskMap.get(scriptId);
        if (future == null) {
            throw new NoSuchScriptException();
        }
        return getScriptExecutorFromFutureTask(future, scriptId);
    }

    private void stopThread(FutureTask futureTask) {
        while (futureTask.isCancelled()) {
            futureTask.cancel(false);
        }
    }

    private ScriptExecutor getScriptExecutorFromFutureTask(FutureTask<ScriptExecutor> futureTask, String scriptExecutorId) {
        try {
            return futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) { //TimeoutException
            return getScriptExecutorFromListById(scriptExecutorId);
        }
    }

    private ScriptExecutorConcurrent getScriptExecutorFromListById(String scriptExecutorId) {
        for (ScriptExecutorConcurrent scriptExecutor : scriptExecutors) {
            if (scriptExecutorId.equals(scriptExecutor.getScript().getId())) {
                return scriptExecutor;
            }
        }
        return null;
    }
}
