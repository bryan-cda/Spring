package com.celtic.banking.controller;


import com.celtic.banking.request.ClientRequest;
import com.celtic.banking.request.ClientResponse;
import com.celtic.banking.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("celtic-banking/clients")
@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> listClients(){
        return ResponseEntity.ok(clientService.listClients());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ClientResponse>> listClients(Pageable page){
        return ResponseEntity.ok(clientService.listClients(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @PostMapping
    public  ResponseEntity<ClientResponse> createClient(@RequestBody @Valid ClientRequest clientRequest){
        return ResponseEntity.ok(clientService.createClient(clientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ClientResponse> updateClientData(@RequestBody ClientRequest clientRequest){
        return ResponseEntity.ok(clientService.updateClientData(clientRequest));
    }
}
