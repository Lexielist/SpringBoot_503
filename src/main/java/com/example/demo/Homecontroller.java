package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.Map;

@Controller
public class Homecontroller {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    CloudinaryCongig cloudc;
    @RequestMapping("/")
    public String listActors(Model model){
        model.addAttribute("actors", actorRepository.findAll());
        return "list";

    }
    @GetMapping("/add")
    public String processActor(@ModelAttribute Actor actor,
        @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/add";

        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype","auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepository.save(actor);

        } catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";

    }
}
