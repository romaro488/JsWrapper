package ua.polosmak.wrapper.executor;

import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.ScriptServiceException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Handler that manage script execution.
 */
public interface ScriptExecutorHandler {

    /**
     * Get all script executors.
     *
     * @return list of script executors.
     */
    List<ScriptExecutor> getAllScriptExecutors();

    /**
     * Add and start execute specified script.
     *
     * @param script specified script.
     * @return script executorservice.
     * @throws ScriptServiceException if compilation failed.
     */
    ScriptExecutor addAndExecuteScript(Script script) throws ScriptServiceException, ExecutionException, InterruptedException;

    /**
     * Stop execution script by specified id.
     *
     * @param scriptId specified id.
     * @throws ScriptServiceException if script executorService not exist.
     */
    void stopExecutorScript(String scriptId) throws ScriptServiceException;

    /**
     * Get script executorservice by specified id.
     *
     * @param scriptId specified id.
     * @return script executorService.
     */
    ScriptExecutor getScriptExecutorById(String scriptId) throws ScriptServiceException;
}
