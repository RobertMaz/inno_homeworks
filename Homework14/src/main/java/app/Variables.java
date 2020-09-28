package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * Rest service which return all object as JSON.
 * If want print all variables as JSON object,
 * then path=http://127.0.0.1:8080/Homework14/rest/var.
 * If want print only need variable,
 * then path=http://127.0.0.1:8080/Homework14/rest/var/byName?name={findName}.
 * If want print all variables as UI table,
 * then path=http://127.0.0.1:8080/Homework14/.
 */
@Path("/var")
public class Variables implements Serializable {

    @EJB
    private SystemReq systemReq;


    @GET
    public String getAllToString(){
        return systemReq.findAll();
    }

    @GET
    @Path("byName")
    public String getVariable(@QueryParam("name") String name) throws JsonProcessingException {
        return systemReq.find(name);
    }

}
