package org.jeannyil.fuse.cxfrs.ipservice.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value="/")
public interface FreeGeoIp {
 
    @GET
    @Path(value="/{type}/{ip}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String getGeoIp(@PathParam("type") String type, @PathParam("ip") String ip);
 
}

