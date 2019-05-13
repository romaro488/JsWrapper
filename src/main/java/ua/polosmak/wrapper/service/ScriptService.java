package ua.polosmak.wrapper.service;

import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.ScriptServiceException;

import java.util.List;

/**
 * Script service.
 */
public interface ScriptService {

    /**
     * Get all scripts.
     *
     * @return list of scripts.
     */
    List<Script> getAllScripts();

    /**
     * Add specified script.
     *
     * @param script specified script.
     * @return script executorservice.
     * @throws ScriptServiceException if compilation failed.
     */
    Script addScript(Script script) throws Exception;

    /**
     * Get script by id.
     *
     * @param scriptId script id.
     * @return script executorService.
     */
    Script getScriptById(String scriptId) throws ScriptServiceException;

    /**
     * Remove specified script by id.
     *
     * @param scriptId specified id.
     * @throws ScriptServiceException if script does not exist by specified id.
     */
    void deleteScript(String scriptId) throws ScriptServiceException;

}
