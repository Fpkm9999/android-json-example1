package org.example.javaspringbootjpa1.controller;

import org.example.javaspringbootjpa1.service.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OneController {

    @Autowired
    private OneService oneService;

    @GetMapping("/test")
    public String test() {
        return "Hello, world";
    }

//    @GetMapping("/test1")
//    public List<String> getAllMembers() {
//        List<String> members = oneService.getAllMembers();
//        return members;
//    }
//    }
}