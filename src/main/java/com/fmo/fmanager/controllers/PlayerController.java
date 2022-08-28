package com.fmo.fmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PlayerController {
    @GetMapping("addPlayerPage")
    public String addPlayerPage(Model model) {
        return "add-player-page";
    }
}
