package com.fmo.fmanager.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmo.fmanager.domain.Player;
import com.fmo.fmanager.domain.Position;
import com.fmo.fmanager.services.PositionsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PositionsService positionsService;

    @GetMapping("addPlayerPage")
    public String addPlayerPage(Model model) {
        Player player = new Player();
        model.addAttribute("player", player);

        String json = positionsService.getPositionsPlainJSON();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Position>> typeReference = new TypeReference<>() {};
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode json2 = root.at("/_embedded/positions");
            List<Position> positions = mapper.readValue(json2.toString(), typeReference);
            model.addAttribute("positions", positions);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "add-player-page";
    }

    @PostMapping("player/save")
    public String submitPlayer(Model model, @ModelAttribute("player") Player player) {
        model.addAttribute("player", player);

        rabbitTemplate.convertAndSend("MyTopicExchange", "topic", player);

        return "add-player-success";
    }
}
