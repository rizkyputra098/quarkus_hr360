package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UpdateEmployeeDTO;
import org.acme.model.Employee;
import org.acme.repository.EmployeeEnt;
import org.acme.repository.EmployeeReposotory;
import org.acme.until.JwtUntil;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Optional;

@Path("api/v1/employess")
public class EmployeeController {
    @Inject
    EmployeeReposotory employeeReposotory;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/fullname")
    public List<String> findAllNameEmployees (){
        return employeeReposotory.findAllNameEmployees();
    }

    @GET
    @Path("/alldataemployee")
    public List<EmployeeEnt> findAllEmployees (){
        return employeeReposotory.findAllEmployees();
    }

    @GET
    @Path("/profile")
    public Response findEmployeeByToken(){
        Long employeeId = JwtUntil.getEmployeeIdFromJwt(jwt);

        Optional<EmployeeEnt> employee = employeeReposotory.findById(employeeId);
        if(employee.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }
        return Response.ok(employee.get()).build();
    }
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOwnEmployee(UpdateEmployeeDTO request) {
        Long employeeId;
        try {
            employeeId = JwtUntil.getEmployeeIdFromJwt(jwt);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        Optional<EmployeeEnt> existing = employeeReposotory.findById(employeeId);
        if (existing.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }



        employeeReposotory.updateEmployee(employeeId, request);
        return Response.ok("Employee updated successfully").build();
    }


    @POST
    @Path("/addemployess")
    public EmployeeEnt addEmployee(@Valid EmployeeEnt employeeEnt) {
        return employeeReposotory.createEmployeeEnt(
                employeeEnt.position_level(),
                employeeEnt.fullname(),
                employeeEnt.employee_num(),
                employeeEnt.address(),
                employeeEnt.address_permanent(),
                employeeEnt.phone(),
                employeeEnt.phone_emergency(),
                employeeEnt.handphone(),
                employeeEnt.sex(),
                employeeEnt.birth_place(),
                employeeEnt.birth_date(),
                employeeEnt.blood_type(),
                employeeEnt.email_address(),
                employeeEnt.race()
        );
    }
}
