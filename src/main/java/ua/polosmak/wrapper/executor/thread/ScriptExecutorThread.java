package ua.polosmak.wrapper.executor.thread;

import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.executor.ScriptExecutor;
import ua.polosmak.wrapper.utils.Constant;

import javax.script.ScriptEngine;

public class ScriptExecutorThread extends ScriptExecutor implements Runnable {

    private Thread currentThread;

    public ScriptExecutorThread(ScriptEngine engine, Script script) {
        super.engine = engine;
        this.script = script;
        engine.getContext().setWriter(printWriter);
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    @Override
    public void run() {
        try {
            currentThread = Thread.currentThread();
            script.setStatus(Constant.STATUS_RUNNING);
            Object result = getExecutionResult();
            script.setResult(result);
            script.setStatus(Constant.STATUS_FINISH);
        } catch (Throwable e) {
            script.setStatus(Constant.STATUS_INTERRUPT + ", cause: " + e);
            clear();
        }
    }
}
