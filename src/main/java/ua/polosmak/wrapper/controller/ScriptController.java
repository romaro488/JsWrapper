package ua.polosmak.wrapper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.polosmak.wrapper.entity.Script;
import ua.polosmak.wrapper.exception.NoSuchScriptException;
import ua.polosmak.wrapper.exception.ScriptServiceException;
import ua.polosmak.wrapper.service.ScriptService;
import ua.polosmak.wrapper.utils.Constant;
import ua.polosmak.wrapper.utils.IdGeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ScriptController {

    private ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Response>> getAllScripts() throws ScriptServiceException {
        List<Response> responseList = new ArrayList<>();
        List<Script> scriptList = scriptService.getAllScripts();
        for (Script script : scriptList) {
            Response response = new Response();
            response.setContent(script);
            responseList.add(response);
            setLinks(response, script);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addScript(@RequestBody String script) throws Exception {
        Response response = new Response();
        Script jsScript = new Script();
        jsScript.setId(IdGeneratorUtils.generateScriptId());
        jsScript.setScript(script);
        Script obj = scriptService.addScript(jsScript);
        response.setContent(obj);
        setLinks(response, obj);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/script/{id}")
    public ResponseEntity<Response> getScriptExecutorById(@PathVariable String id) throws ScriptServiceException {
        Response response = new Response();
        Script script = scriptService.getScriptById(id);
        response.setContent(script);
        setLinks(response, script);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity stopAndRemoveScript(@PathVariable String id) throws ScriptServiceException {
        scriptService.deleteScript(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> exceptionHandler(Exception e) {
        Map<String, String> responseBody = new HashMap<>();
        if (e instanceof NoSuchScriptException) {
            responseBody.put("code", Constant.CUSTOMER_SIDE);
        } else {
            responseBody.put("code", Constant.SERVER_SIDE);
        }
        responseBody.put("message", e.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    private void setLinks(Response response, Script script) throws ScriptServiceException {
        response.add(linkTo(methodOn(ScriptController.class).getScriptExecutorById(script.getId())).withRel("getScriptExecutorById"));
        response.add(linkTo(methodOn(ScriptController.class).stopAndRemoveScript(script.getId())).withRel("stopAndRemoveScriptById"));
    }
}
