package ua.polosmak.wrapper.executor.executorService;

import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.ScriptServiceException;
import ua.polosmak.wrapper.executor.ScriptExecutor;
import ua.polosmak.wrapper.utils.Constant;

import javax.script.ScriptEngine;
import java.util.concurrent.Callable;

public class ScriptExecutorConcurrent extends ScriptExecutor implements Callable<ScriptExecutor> {

    public ScriptExecutorConcurrent(Script script, ScriptEngine engine) {
        this.script = script;
        this.engine = engine;
        engine.getContext().setWriter(printWriter);
    }

    @Override
    public ScriptExecutor call() throws ScriptServiceException {
        try {
            script.setStatus(Constant.STATUS_RUNNING);
            Object executionResult = getExecutionResult();
            script.setStatus(Constant.STATUS_FINISH);
            script.setResult(executionResult);
        } catch (Throwable e) {
            script.setStatus(Constant.STATUS_INTERRUPT + ", cause: " + e);
            clear();
            throw new ScriptServiceException(e.toString());
        }
        return this;
    }
}
