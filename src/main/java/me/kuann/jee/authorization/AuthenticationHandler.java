package me.kuann.jee.authorization;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationHandler implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (!requestContext.getHeaderString("token").equals("kuann")) {
			Map<String, Object> map = new HashMap<>();
			map.put("Time", LocalDateTime.now().toString());
			map.put("Detail", "Not allowed");

			requestContext.abortWith(Response.status(Status.FORBIDDEN).entity(map).build());
		}
	}

}
