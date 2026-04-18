package com.example.linkfamily.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.PhotoService;

@RestController
@RequestMapping("/puzzle")
@CrossOrigin(origins = "*")
public class PuzzleController {

    @Autowired
    private PhotoService photoService;

    @GetMapping({"/getAllpuzzles"})
    public Response getAllpuzzles() {
        return Response.success(photoService.getAllPuzzles());
    }
}
