package com.example.API;

import Model.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

import static DAO.MethodeNeo4J.*;

@RestController
@RequestMapping("/kaggle")
public class Hellocontroller {


    @GetMapping("/combo/{label}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getNodecombo(@PathVariable("label") String label){
        HashMap<Object,Object> nodeCombo= searchNodeCombo(label);
        return nodeCombo;
    }

    @GetMapping("/combo/{label}/{sourcelabel}/{targetlabel}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getEdegcombo(@PathVariable("label") String label,@PathVariable("sourcelabel") String sourcelabel,@PathVariable("targetlabel") String targetlabel){
        HashMap<Object,Object> edgeCombo= searchEdgeCombo(label,sourcelabel,targetlabel);
        return edgeCombo;
    }


    @GetMapping("/node/{label}/{nbPage}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getNode(@PathVariable("label") String label,@PathVariable("nbPage") int nbPage) {
        HashMap<Object,Object> nodeId = searchNodeId(label,nbPage);
        return nodeId;
    }

    @GetMapping("/{label}/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getEntityById(@PathVariable("label") String label,@PathVariable("id") String id){
        HashMap<Object,Object> cu = searchEntityById( label,id);
        return cu;
    }

    @GetMapping("/edge/{label}/{nbPage}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getEntityById(@PathVariable("label") String label,@PathVariable("nbPage") int nbPage){
        HashMap<Object,Object> cu = searchEdgeId(label,nbPage);
        return cu;
    }

    @GetMapping("/edge/{label}/{sourceLabel}/{sourceId}/{targetLabel}/{targetId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public HashMap<Object,Object> getEntityById(@PathVariable("label") String label,@PathVariable("sourceLabel") String sourceLabel,@PathVariable("sourceId") String sourceId,@PathVariable("targetLabel") String targetLabel,@PathVariable("targetId") String targetId){
        HashMap<Object,Object> cu = searchEdgeById(label,sourceLabel,targetLabel,sourceId,targetId);
        return cu;
    }
}
