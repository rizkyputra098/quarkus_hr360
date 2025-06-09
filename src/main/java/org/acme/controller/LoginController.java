package org.acme.controller;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.hash.PasswordUtil;
import org.acme.model.Auth;
import org.acme.repository.LoginEnt;
import org.acme.repository.LoginReposotory;
import org.eclipse.microprofile.jwt.JsonWebToken;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("api/v1/login")
public class LoginController {

    @Inject
    LoginReposotory loginReposotory;

    @Inject
    JsonWebToken jwt;


    @GET
    @Path("/testing")
    public List<Auth> getAuth() {
        return loginReposotory.getAuths();
    }


    @POST
    @Path("/SignIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(LoginEnt loginEnt) {
        Optional<LoginEnt> found = loginReposotory.getAuthByEmail(loginEnt.email());

        if (found.isEmpty() || !PasswordUtil.verify(loginEnt.password(), found.get().password())) {
            return Response.status(401).entity("Invalid email or password").build();
        }



        String token = Jwt.upn(found.get().email())
                .claim("userId", found.get().userId())
                .claim("employeeId", found.get().employeeId())
                .claim("roles", "user")
                .sign();

        return Response.ok(Map.of("email", loginEnt.email(), "token", token)).build();
    }
}
