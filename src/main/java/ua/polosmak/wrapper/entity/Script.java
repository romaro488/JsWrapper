package ua.polosmak.wrapper.entity;

import ua.polosmak.wrapper.utils.Constant;

import java.util.Objects;
import java.util.StringJoiner;

public class Script {
    private String id;
    private String script;
    private String status = Constant.STATUS_NEW;
    private Object result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Script script1 = (Script) o;
        return Objects.equals(id, script1.id) &&
                Objects.equals(script, script1.script) &&
                Objects.equals(status, script1.status) &&
                Objects.equals(result, script1.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, script, status, result);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Script.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("script='" + script + "'")
                .add("status='" + status + "'")
                .add("result=" + result)
                .toString();
    }
}
