package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.repository.LoginEnt;
import org.acme.repository.LoginReposotory;

@Path("api/v1/login")
public class RegisterController {

    @Inject
    LoginReposotory loginReposotory;

    @POST
    @Path("/SignUp")
    public LoginEnt newLoginEnt (@Valid LoginEnt Ent) {
        return loginReposotory.createLoginEnt(Ent.password(), Ent.email(), Ent.fullName(), Ent.employeeId(), Ent.adminStatus());
    }
}
