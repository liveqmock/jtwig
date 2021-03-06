package org.jtwig;

import org.jtwig.configuration.JtwigConfiguration;
import org.jtwig.configuration.JtwigConfigurationBuilder;
import org.jtwig.exception.JtwigException;
import org.jtwig.loader.Loader;
import org.jtwig.loader.impl.ClasspathLoader;
import org.jtwig.loader.impl.StringLoader;
import org.jtwig.render.RenderContext;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class JtwigTemplate {
    public static JtwigTemplate inlineTemplate(String template) {
        return new JtwigTemplate(new StringLoader.StringResource(template));
    }
    public static JtwigTemplate inlineTemplate(String template, JtwigConfiguration configuration) {
        return new JtwigTemplate(new StringLoader.StringResource(template), configuration);
    }

    public static JtwigTemplate classpathTemplate (String location) {
        return new JtwigTemplate(new ClasspathLoader.ClasspathResource(location));
    }
    public static JtwigTemplate classpathTemplate (String location, JtwigConfiguration configuration) {
        return new JtwigTemplate(new ClasspathLoader.ClasspathResource(location), configuration);
    }

    private final Environment environment;
    private final Loader.Resource resource;

    public JtwigTemplate(Loader.Resource resource) {
        this(resource, JtwigConfigurationBuilder.newConfiguration().build());
    }

    public JtwigTemplate(Loader.Resource resource, JtwigConfiguration configuration) {
        this.environment = new Environment(configuration);
        this.resource = resource;
    }

    public void render (JtwigModelMap modelMap, OutputStream outputStream) throws JtwigException {
        environment.compile(resource).render(RenderContext.create(environment, modelMap, outputStream));
    }

    public String render (JtwigModelMap modelMap) throws JtwigException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        environment.compile(resource).render(RenderContext.create(environment, modelMap, output));
        return output.toString();
    }

    public Environment environment() {
        return environment;
    }
}
