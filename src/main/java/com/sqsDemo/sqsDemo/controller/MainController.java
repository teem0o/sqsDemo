package com.sqsDemo.sqsDemo.controller;


import com.sqsDemo.sqsDemo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MessageService messageService;

//    @RequestMapping(value = "/write",method = RequestMethod.POST)
//    public void write(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
//        InputStream inputStream = servletRequest.getInputStream();
//        String message = IOUtils.toString(inputStream);
//        messageService.sendMessage(message);
//    }

    @GetMapping(value = "/write/{message}")
    public String write(@PathVariable String message) throws IOException {
        messageService.sendMessage(message);
        return message;
    }

}

