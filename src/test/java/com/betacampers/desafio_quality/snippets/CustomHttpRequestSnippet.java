package com.betacampers.desafio_quality.snippets;

import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.http.HttpRequestSnippet;
import org.springframework.restdocs.operation.Operation;
import org.springframework.util.Assert;

import java.util.Map;

public class CustomHttpRequestSnippet extends HttpRequestSnippet {
    @Override
    protected Map<String, Object> createModel(Operation operation) {
        var model = super.createModel(operation);
        model.put("path", removeQueryStringIfPresent(extractUrlTemplate(operation)));
        return model;
    }

    private String removeQueryStringIfPresent(String urlTemplate) {
        int index = urlTemplate.indexOf('?');
        if (index == -1) {
            return urlTemplate;
        }
        return urlTemplate.substring(0, index);
    }

    private String extractUrlTemplate(Operation operation) {
        String urlTemplate = (String) operation.getAttributes()
                .get(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE);
        Assert.notNull(urlTemplate, "urlTemplate not found. If you are using MockMvc did "
                + "you use RestDocumentationRequestBuilders to build the request?");
        return urlTemplate;
    }
}
