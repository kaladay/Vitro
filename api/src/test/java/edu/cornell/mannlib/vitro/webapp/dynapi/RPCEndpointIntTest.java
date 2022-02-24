package edu.cornell.mannlib.vitro.webapp.dynapi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import stubs.javax.servlet.http.HttpServletRequestStub;
import stubs.javax.servlet.http.HttpServletResponseStub;

public class RPCEndpointIntTest extends ServletContextTest {

	private final static String URI_BASE = "http://localhost/api/rpc/";

	private RPCEndpoint rpcEndpoint;

	private ActionPool actionPool;

	private HttpServletRequestStub request;

	private HttpServletResponseStub response;

	@Before
	public void beforeEach() throws IOException {
		request = new HttpServletRequestStub();
		response = new HttpServletResponseStub();
		rpcEndpoint = new RPCEndpoint();

		loadDefaultModel();

		request.setServletContext(servletContext);

		actionPool = ActionPool.getInstance();
		actionPool.init(request.getServletContext());
		actionPool.reload();
	}

	@Test
	public void doGetTest() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));
		request.addParameter("limit", "1");

		rpcEndpoint.doGet(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doGetTestWhenNameIsUnknown() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + "unknown"));
		request.addParameter("limit", "1");

		rpcEndpoint.doGet(request, response);

		assertEquals(HttpServletResponse.SC_NOT_IMPLEMENTED, response.getStatus());
	}

	@Test
	public void doGetTestWhenWithoutParameters() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));

		rpcEndpoint.doGet(request, response);

		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
	}

	@Test
	public void doPostTest() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));
		request.addParameter("limit", "1");

		rpcEndpoint.doPost(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doPostTestWhenNameIsUnknown() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + "unknown"));
		request.addParameter("limit", "1");

		rpcEndpoint.doPost(request, response);

		assertEquals(HttpServletResponse.SC_NOT_IMPLEMENTED, response.getStatus());
	}

	@Test
	public void doPostTestWhenWithoutParameters() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));

		rpcEndpoint.doPost(request, response);

		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
	}

	@Test
	public void doDeleteTest() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));
		request.addParameter("limit", "1");

		rpcEndpoint.doDelete(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doDeleteTestWhenNameIsUnknown() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + "unknown"));
		request.addParameter("limit", "1");

		rpcEndpoint.doDelete(request, response);

		assertEquals(HttpServletResponse.SC_NOT_IMPLEMENTED, response.getStatus());
	}

	@Test
	public void doDeleteTestWhenWithoutParameters() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));

		rpcEndpoint.doDelete(request, response);

		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
	}

	@Test
	public void doPutTest() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));
		request.addParameter("limit", "1");

		rpcEndpoint.doPut(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

	@Test
	public void doPutTestWhenNameIsUnknown() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + "unknown"));
		request.addParameter("limit", "1");

		rpcEndpoint.doPut(request, response);

		assertEquals(HttpServletResponse.SC_NOT_IMPLEMENTED, response.getStatus());
	}

	@Test
	public void doPutTestWhenWithoutParameters() throws MalformedURLException {
		request.setRequestUrl(new URL(URI_BASE + TEST_ACTION_NAME));

		rpcEndpoint.doPut(request, response);

		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
	}
}
