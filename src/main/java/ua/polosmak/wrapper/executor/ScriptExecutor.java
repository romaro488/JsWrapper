package ua.polosmak.wrapper.executor;

import ua.polosmak.wrapper.entity.Script;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * Script executorService that contains script and use multithreading to execute it.
 */
public abstract class ScriptExecutor {
    protected Script script;
    protected ScriptEngine engine;
    private StringWriter stringWriter = new StringWriter();
    protected PrintWriter printWriter = new PrintWriter(stringWriter);

    public Script getScript() {
        return script;
    }

    /**
     * Clear contents of a PrintWriter after writing.
     * For example after OutOfMemoryError.
     */
    protected void clear() {
        printWriter.flush();
        stringWriter.getBuffer().setLength(0);
    }

    /**
     * Get result of script execution.
     *
     * @return function result or console output.
     * @throws ScriptException scriptException
     */
    protected Object getExecutionResult() throws ScriptException {
        Object functionResult = engine.eval(script.getScript());
        if (Objects.nonNull(functionResult)) {
            return functionResult;
        }
        return stringWriter.getBuffer();
    }
}
