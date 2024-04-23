package com.yeva.dauletova.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QRController {
    @GetMapping("/qr")
    public String getQR(Model model){
        model.addAttribute("qrlink", "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=example");
        return "qr";
    }
    @PostMapping("/qr")
    public String getQR(@RequestParam String qrText, Model model){
        model.addAttribute("qrlink", "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+qrText);
        return "qr";
    }
}
