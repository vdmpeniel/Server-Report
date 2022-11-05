package com.victor.server_report.service;

import com.victor.server_report.enumeration.Status;
import com.victor.server_report.model.Server;
import com.victor.server_report.repository.ServerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.victor.server_report.enumeration.Status.SERVER_DOWN;
import static com.victor.server_report.enumeration.Status.SERVER_UP;

@Service
//@RequiredArgsConstructor // lombok creates a constructor and puts serverRepository in the constructor as a dependency (no need for @Autowired)
@AllArgsConstructor
@Transactional // https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/transaction.html
@Slf4j
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server {}", server.getName());
        server.setImageUrl(setServerImageUrl(server.getIpAddress()));
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? SERVER_UP : SERVER_DOWN);
        return serverRepository.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
        //return serverRepository.findAll().stream().toList();

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server save(Server server) {
        log.info("Saving server {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server {}", server.getName());
        return save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Delete server by id: {}", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl(String ipAddress) {
        String[] imageNames = {"server_1.png", "server_2.png", "server_3.png", "server_4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
