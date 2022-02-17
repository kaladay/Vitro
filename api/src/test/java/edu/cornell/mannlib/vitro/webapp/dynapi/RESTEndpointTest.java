package edu.cornell.mannlib.vitro.webapp.dynapi;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResourcePool.class)
public class RESTEndpointTest {

	final static String URI_TEST = "/api/rest/test";

	private static RESTEndpoint restEndpoint;

	@Mock
	private static ResourcePool resourcePool;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@BeforeClass
	public static void beforeAll()  throws IOException, ServletException {
		mockStatic(ResourcePool.class);
		expect(ResourcePool.getInstance()).andReturn(resourcePool).anyTimes();
		replay(ResourcePool.class);

		restEndpoint = new RESTEndpoint();
	}

	@Before
	public void beforeEach() {
		request = createMock(HttpServletRequest.class);
		response = createMock(HttpServletResponse.class);

		expect(request.getRequestURI()).andReturn(URI_TEST);
	}

	@Test
	public void doPostTest() {
		expect(request.getMethod()).andReturn("POST").atLeastOnce();
		replay(request);

		restEndpoint.doPost(request, response);
		verify(request);
	}

	@Test
	public void doGetTest() {
		expect(request.getMethod()).andReturn("GET").atLeastOnce();
		replay(request);

		restEndpoint.doGet(request, response);
		verify(request);
	}

	@Test
	public void doDeleteTest() {
		expect(request.getMethod()).andReturn("DELETE").atLeastOnce();
		replay(request);

		restEndpoint.doDelete(request, response);
		verify(request);
	}

	@Test
	public void doPutTest() {
		expect(request.getMethod()).andReturn("PUT").atLeastOnce();
		replay(request);

		restEndpoint.doPut(request, response);
		verify(request);
	}

	@Test
	public void doPatchTest() {
		expect(request.getMethod()).andReturn("PATCH").atLeastOnce();
		replay(request);

		restEndpoint.doPatch(request, response);
		verify(request);
	}
}
