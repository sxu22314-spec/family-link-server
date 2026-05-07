package com.example.linkfamily.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkfamily.Model.Dto.UpdateLockedRequest;
import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.PhotoService;


@RestController
@RequestMapping("/puzzle")
public class PuzzleController {

    @Autowired
    private PhotoService photoService;

    @GetMapping({"/getAllpuzzles"})
    public Response getAllpuzzles() {
        return Response.success(photoService.getAllPuzzles());
    }

    @PostMapping("/updateIsLocked")
    public Response updateIsLocked(@RequestBody UpdateLockedRequest request){
        if (request == null || request.getId() == null) {
        return Response.error("Puzzle ID is missing");
        }   

            Long id = Long.valueOf(request.getId());
            Integer isLocked = request.getIsLocked();

            if(photoService.updateIsLocked(id, isLocked)){
                return Response.success();
            }
            return Response.error();
    }
}
