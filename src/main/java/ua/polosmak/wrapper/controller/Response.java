package ua.polosmak.wrapper.controller;

import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;
import java.util.StringJoiner;

public class Response extends ResourceSupport {
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Response response = (Response) o;
        return Objects.equals(content, response.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Response.class.getSimpleName() + "[", "]")
                .add("content=" + content)
                .toString();
    }

}
