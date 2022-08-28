package com.fmo.fmanager.controllers;

import com.fmo.fmanager.domain.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlayerController {
    @GetMapping("addPlayerPage")
    public String addPlayerPage(Model model) {
        Player player = new Player();
        model.addAttribute("player", player);

        List<String> listPosition = Arrays.asList("GK", "DC", "DR", "DL", "DMC", "MC", "FC");
        model.addAttribute("listPosition", listPosition);

        return "add-player-page";
    }
}
