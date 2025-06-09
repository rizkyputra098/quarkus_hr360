package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.repository.LoginEnt;
import org.acme.repository.LoginReposotory;

import java.util.Map;
import java.util.Optional;

@Path("api/v1/login")
public class RegisterController {

    @Inject
    LoginReposotory loginReposotory;

    @POST
    @Path("/SignUp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newLoginEnt (@Valid LoginEnt Ent) {
        Optional<LoginEnt> found = loginReposotory.getAuthByEmail(Ent.email());


        if (found.isPresent()) {
            return Response.status(409)
                    .entity(Map.of("error", "Email already exists"))
                    .build();
        }

        LoginEnt created = loginReposotory.createLoginEnt(
                Ent.password(), Ent.email(), Ent.fullName(), Ent.employeeId(), Ent.adminStatus()
        );
        return Response.status(201).entity(created).build();
    }
}
