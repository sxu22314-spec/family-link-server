package com.example.linkfamily.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/selectUser/{id}")
    public Response selectUser(@PathVariable Long id ){
        return Response.success(userService.getById(id));
    }
}
