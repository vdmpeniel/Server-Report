package com.victor.server_report.service;

import com.victor.server_report.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> list(int limit);
    Server get(Long id);
    Server save(Server server);
    Server update(Server server);
    Boolean delete(Long id);

}
