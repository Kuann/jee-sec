package me.kuann.jee.authorization;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationHandler implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (isAccessibleWithoutRoleResource()) {
			return;
		}

		if (!requestContext.getHeaderString("role").equals("kuann")) {
			Map<String, Object> map = new HashMap<>();
			map.put("Time", LocalDateTime.now().toString());
			map.put("Detail", "Not allowed");

			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(map).build());
		}
	}

	private boolean isAccessibleWithoutRoleResource() {
		return resourceInfo.getResourceClass().isAnnotationPresent(AccessibleWithoutRole.class)
				|| resourceInfo.getResourceMethod().isAnnotationPresent(AccessibleWithoutRole.class);
	}

}
