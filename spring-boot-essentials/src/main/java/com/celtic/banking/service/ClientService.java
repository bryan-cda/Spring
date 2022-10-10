package com.celtic.banking.service;

import com.celtic.banking.domain.Client;
import com.celtic.banking.mapping.ClientMapper;
import com.celtic.banking.mapping.ClientResponseMapper;
import com.celtic.banking.repository.ClientRepository;
import com.celtic.banking.request.ClientRequest;
import com.celtic.banking.request.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientResponse> listClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientResponse> allClients = new ArrayList<>();

        clients.forEach(client -> {
            allClients.add(ClientResponseMapper.INSTANCE.mapToClientResponse(client));
        });
        return allClients;
    }

    public Page<Client> listClients(Pageable page) {
        Page<Client> clients = clientRepository.findAll(page);
        return clients;
    }

    public ClientResponse findClientById(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found for id."));
        return ClientResponseMapper.INSTANCE.mapToClientResponse(client);
    }

    public ClientResponse createClient(Client client) {
        return ClientResponseMapper.INSTANCE.mapToClientResponse(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found for id."));
        clientRepository.deleteById(id);
    }

    public ClientResponse updateClientData(ClientRequest clientRequest) {
        Client client = clientRepository.findById(clientRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found."));

        clientRepository.deleteById(client.getId());

        return createClient(ClientMapper.INSTANCE.mapToClient(clientRequest));
    }


}
