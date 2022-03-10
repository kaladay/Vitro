package edu.cornell.mannlib.vitro.webapp.dynapi;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.cornell.mannlib.vitro.webapp.dynapi.matcher.APIResponseMatcher;

@RunWith(Parameterized.class)
public class RESTDocumentationEndpointIntegrationTest extends ServletContextITest {

    private RESTDocumentationEndpoint restEndpoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter responsePrintWriter;

    @Parameter(0)
    public String testVersion;

    @Parameter(1)
    public String testResource;

    @Parameter(2)
    public Boolean testJson;

    @Parameter(3)
    public String testExpectedResponse;

    @Parameter(4)
    public String testMessage;

    @Before
    public void beforeEach() throws IOException {
        restEndpoint = new RESTDocumentationEndpoint();

        loadDefaultModel();
        loadTestModel();

        loadModels("n3", "src/test/resources/rdf/abox/filegraph/dynamic-api-individuals-api.n3");

        ActionPool actionPool = ActionPool.getInstance();
        ResourceAPIPool resourceAPIPool = ResourceAPIPool.getInstance();

        actionPool.init(servletContext);
        actionPool.reload();

        resourceAPIPool.init(servletContext);
        resourceAPIPool.reload();

        DynamicAPIDocumentation dynamicAPIDocumentation = DynamicAPIDocumentation.getInstance();

        dynamicAPIDocumentation.init(servletContext);

        MockitoAnnotations.openMocks(this);
    }

    @After
    public void afterEach() {

    }

    @Test
    public void doTest() throws IOException {
        String pathInfo = "/" + testVersion;
        String mimeType = "application/yaml";

        if (testResource != null) {
            pathInfo += "/" + testResource;
        }

        if (testJson == true) {
            mimeType = "application/json";
        }

        when(request.getServletPath()).thenReturn("/docs/rest");
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getHeader("Accept")).thenReturn(mimeType);
        when(request.getContentType()).thenReturn(mimeType);
        when(response.getWriter()).thenReturn(responsePrintWriter);

        System.out.println("\n\nRunning Test against: '/docs/rest" + pathInfo + "'.\n");

        restEndpoint.doGet(request, response);

        verify(response, times(1)).setContentType(mimeType);
        verify(responsePrintWriter, times(1)).print(argThat(new APIResponseMatcher(testJson, testExpectedResponse)));
        verify(responsePrintWriter, times(1)).flush();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> requests() {
        final String collection = "test_collection_resource";
        final String concept = "test_concept_resource";
        final String document = "test_document_resource";
        final String organization = "test_organization_resource";
        final String person = "test_person_resource";
        final String process = "test_process_resource";
        final String relationship = "test_relationship_resource";
        final String resource = "test_resource";

        return Arrays.asList(new Object[][] {
            // version resource,     json,  expected response,         message
            { "1",     null,         false, "rest/1/all",              "All, Version 1 with yaml" },
          //{ "2.1.0", null,         false, "rest/2.1.0/all",          "All, Version 2.1.0 with yaml" },
            { "1",     collection,   false, "rest/1/collection",       collection + ", Version 1 with yaml" },
          //{ "2.1.0", collection,   false, "rest/2.1.0/collection",   collection + ", Version 2.1.0 with yaml" },
            { "1",     concept,      false, "rest/1/concept",          concept + ", Version 1 with yaml" },
          //{ "2.1.0", concept,      false, "rest/2.1.0/concept",      concept + ", Version 2.1.0 with yaml" },
            { "1",     document,     false, "rest/1/document",         document + ", Version 1 with yaml" },
          //{ "2.1.0", document,     false, "rest/2.1.0/document",     document + ", Version 2.1.0 with yaml" },
            { "1",     organization, false, "rest/1/organization",     organization + ", Version 1 with yaml" },
          //{ "2.1.0", organization, false, "rest/2.1.0/organization", organization + ", Version 2.1.0 with yaml" },
            { "1",     person,       false, "rest/1/person",           person + ", Version 1 with yaml" },
          //{ "2.1.0", person,       false, "rest/2.1.0/person",       person + ", Version 2.1.0 with yaml" },
            { "1",     process,      false, "rest/1/process",          process + ", Version 1 with yaml" },
          //{ "2.1.0", process,      false, "rest/2.1.0/process",      process + ", Version 2.1.0 with yaml" },
            { "1",     relationship, false, "rest/1/relationship",     relationship + ", Version 1 with yaml" },
          //{ "2.1.0", relationship, false, "rest/2.1.0/relationship", relationship + ", Version 2.1.0 with yaml" },
            { "1",     resource,     false, "rest/1/resource",         resource + ", Version 1 with yaml" },
          //{ "2.1.0", resource,     false, "rest/2.1.0/resource",     resource + ", Version 2.1.0 with yaml" },

            // version resource,     json,  expected response,        message
            { "1",     null,         true, "rest/1/all",              "All, Version 1 with json" },
          //{ "2.1.0", null,         true, "rest/2.1.0/all",          "All, Version 2.1.0 with json" },
            { "1",     collection,   true, "rest/1/collection",       collection + ", Version 1 with json" },
          //{ "2.1.0", collection,   true, "rest/2.1.0/collection",   collection + ", Version 2.1.0 with json" },
            { "1",     concept,      true, "rest/1/concept",          concept + ", Version 1 with json" },
          //{ "2.1.0", concept,      true, "rest/2.1.0/concept",      concept + ", Version 2.1.0 with json" },
            { "1",     document,     true, "rest/1/document",         document + ", Version 1 with json" },
          //{ "2.1.0", document,     true, "rest/2.1.0/document",     document + ", Version 2.1.0 with json" },
            { "1",     organization, true, "rest/1/organization",     organization + ", Version 1 with json" },
          //{ "2.1.0", organization, true, "rest/2.1.0/organization", organization + ", Version 2.1.0 with json" },
            { "1",     person,       true, "rest/1/person",           person + ", Version 1 with json" },
          //{ "2.1.0", person,       true, "rest/2.1.0/person",       person + ", Version 2.1.0 with json" },
            { "1",     process,      true, "rest/1/process",          process + ", Version 1 with json" },
          //{ "2.1.0", process,      true, "rest/2.1.0/process",      process + ", Version 2.1.0 with json" },
            { "1",     relationship, true, "rest/1/relationship",     relationship + ", Version 1 with json" },
          //{ "2.1.0", relationship, true, "rest/2.1.0/relationship", relationship + ", Version 2.1.0 with json" },
            { "1",     resource,     true, "rest/1/resource",         resource + ", Version 1 with json" },
          //{ "2.1.0", resource,     true, "rest/2.1.0/resource",       resource + ", Version 2.1.0 with json" },
        });
    }

}
