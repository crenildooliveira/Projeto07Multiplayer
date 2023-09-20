package com.smb.projeto07multiplayer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/game")
    public String game(Model model) {
        // Adicione dados ao modelo, se necessário
        model.addAttribute("message", "Bem-vindo ao meu aplicativo!");

        // Retorne o nome da página Thymeleaf (página game) que deve ser exibida
        return "game";
    }
}
