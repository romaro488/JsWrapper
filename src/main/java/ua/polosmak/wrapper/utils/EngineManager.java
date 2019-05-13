package ua.polosmak.wrapper.utils;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Component
@PropertySource("classpath:config.properties")
public class EngineManager {
    private static final Logger LOG = Logger.getLogger(EngineManager.class);

    @Value("${engine.name}")
    private String engineName;

    /**
     * Get engine entity by engine name.
     *
     * @return copy of engine.
     */
    public ScriptEngine getEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName(engineName);
        LOG.debug("New engine was created");
        return engine;
    }

    /**
     * Compile script.
     *
     * @param script specified script.
     * @return true if code is compiling.
     */
    public boolean compile(String script, ScriptEngine engine) {
        try {
            ((Compilable) engine).compile(script);
            LOG.debug("Script compiled successful: \n" + script);
            return true;
        } catch (ScriptException e) {
            LOG.warn("Script \"" + script + "\" didn't compile");
            return false;
        }
    }
}
