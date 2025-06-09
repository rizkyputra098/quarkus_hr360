package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.repository.EmployeeEnt;
import org.acme.repository.ScheduleEnt;
import org.acme.repository.ScheduleReposotory;

import java.util.List;

@Path("api/v1/schedule")
public class ScheduleController {

 @Inject
 ScheduleReposotory scheduleReposotory;

    @GET
    @Path("/alldataschedule")

    public List<ScheduleEnt> findAllScheduleEnt () {
        return scheduleReposotory.findAllSchedules();
    }
}
