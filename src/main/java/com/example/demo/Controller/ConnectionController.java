package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Connect.NetworkTest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Connection API", description = "連線測試")
public class ConnectionController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conneciton Redis success")})
    @Operation(summary = "Redis連線", description = "connection Redis")
    @GetMapping("/redis")
    public void redisConnection(String host, Integer port){
        System.out.println("#### Redis connect start ");
        NetworkTest.testConnection(host, port);
        System.out.println("#### Redis connect end ");
    }
}

