package com.example.API;

import Model.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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



    // ---------------------with time----------------------------

    @RequestMapping(value = "/time/{label}/time_get", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public HashMap<Object,Object> getCombodataBytime(@PathVariable("label") String label, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date st, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date et) {

        String pattern = "yyyy-MM-dd HH:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);
        String strSt = df.format(st);
        String strEt = df.format(et);

        StringBuilder sb_st=new StringBuilder(strSt);
        sb_st.replace(10, 11, "T");
        String stFin=sb_st.toString();

        StringBuilder sb_et=new StringBuilder(strEt);
        sb_et.replace(10, 11, "T");
        String etFin=sb_et.toString();

        HashMap<Object,Object> res = searchNodeComboWithTime(label,stFin,etFin);
        return res;
    }

    @PostMapping(value="/getBody")
    public String getBody(@RequestBody PathData pathData){
    	System.out.println(pathData.getPathEdges());
        return pathData.getPathNodes().get(0).getLabel();
    }

}
