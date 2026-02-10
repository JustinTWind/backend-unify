package com.UnifyApp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "¡Bienvenido a Unify! La aplicación está funcionando correctamente.";
    }

    @GetMapping("/api/health")
    public String health() {
        return "API Health: OK";
    }
}

