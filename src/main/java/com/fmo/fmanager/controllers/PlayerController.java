package com.fmo.fmanager.controllers;

import com.fmo.fmanager.domain.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PlayerController {
    @GetMapping("addPlayerPage")
    public String addPlayerPage(Model model) {
        Player player = new Player();
        model.addAttribute("player", player);

        return "add-player-page";
    }
}
