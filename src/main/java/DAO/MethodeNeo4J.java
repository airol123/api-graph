package DAO;

import Model.*;
import org.neo4j.cypher.internal.compiler.v2_3.No;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Str;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.*;

import static DAO.outils.captureName;

/**
 *
 * @author RRH
 */
public class MethodeNeo4J {

    private static String nb="20";
    private static String nbId="21";
    private static int nbDisplay=21;


    //APIs for combo graph

    public static HashMap<Object,Object> searchNodeCombo(String label) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        HashMap<Object,Object> res = new HashMap<>();
        Boolean evolution=false;
        Result result = session.run("Match (n:"+captureName(label.toLowerCase())+")return properties(n) as attributes,id(n) as id," +
                "n."+label.toLowerCase()+"id as entityid  limit  "+nb); //order by n."+label.toLowerCase()+"id
        ArrayList<Combo> combos=new ArrayList<>();
        ArrayList<Node> nodes=new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Combo combo=new Combo();
            combo.setId(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString());
            combo.setLabel(combo.getId());
            HashMap<Object,Object> styleCombo =new HashMap<>();
            String colorCombo="#006699";
            styleCombo.put("fill",colorCombo);
            styleCombo.put("stroke",colorCombo);

            combo.setStyle(styleCombo);
            combos.add(combo);
            Node node=new Node();
            node.setId(""+record.get("id").asLong());
            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution=false;
                node.setLabel(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString()); //node.getId()
                node.setComboId("null");

                //  node.setType("diamond");
            } else {
                evolution=true;
                node.setComboId(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString());
                node.setLabel(node.getInstanceid());
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object,Object> attributes =new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());
            HashMap<Object,Object> style =new HashMap<>();
            String color;
            String stroke;
            switch(label.toLowerCase()){
                case "user" :
                    color="#FF6666";
                    stroke="#FF6666";
                    break;
                case "item" :
                    color="#FFFFCC";
                    stroke="#FFFFCC";
                    break;
                default :
                    color="#FFFF00";
                    stroke="#FFFF00";

            }
            style.put("fill",color);
            style.put("stroke",stroke);

            node.setStyle(style);
            node.setAttributes(attributes);
            nodes.add(node);
        }

        LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);

        res.put("nodes",nodes);
        if (evolution) {
            res.put("combos", listWithoutDuplicateElements);
        }
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }
    public static HashMap<Object,Object> searchEdgeCombo(String label,String sourcelabel, String targetlabel) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Boolean sevolution=false;
        Boolean tevolution=false;
        String item=targetlabel.toLowerCase();
        if(targetlabel.toLowerCase()=="item"){
            item=targetlabel.toLowerCase();
        }
        else if (sourcelabel.toLowerCase()=="item"){
            item=sourcelabel;
        }
        Result result = session.run("match(t:"+captureName(targetlabel.toLowerCase())+")<-[r:"+captureName(label.toLowerCase())+"]-(s:"+captureName(sourcelabel.toLowerCase())+") return r as relation,id(r) as relationid, id(s) as startid,properties(s) as sattributes,id(t) as endid,properties(t) as eattributes,type(r) as type, " +
                "properties(r) as rattributes order by t."+item+"id limit "+nb);
        ArrayList<Node> nodes=new ArrayList<>();
        ArrayList<Edge> edges=new ArrayList<>();
        ArrayList<Combo> combos=new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Node source =new Node();
            Node target =new Node();
            Edge edge= new Edge();
            Combo scombo=new Combo();
            Combo tcombo=new Combo();

            // node source and node target
            source.setId(record.get("startid").asLong()+"");
            source.setInstanceid(record.get("sattributes").get("instanceid").asString());
            source.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            source.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution=false;
                source.setLabel(captureName(sourcelabel.toLowerCase())+record.get("sattributes").get(sourcelabel.toLowerCase()+"id").asString());
              //  source.setType("diamond");
            } else {
                sevolution=true;
                source.setComboId(sourcelabel+record.get("sattributes").get(sourcelabel.toLowerCase()+"id").asString());
                source.setLabel(source.getInstanceid());
            }
            HashMap<Object,Object> styleSource =new HashMap<>();
            String colorSource;
            switch(sourcelabel.toLowerCase()){
                case "user" :
                    colorSource="#FF6666";
                    break;
                case "item" :
                    colorSource="#FFFFCC";
                    break;
                default :
                    colorSource="#FFFF00";
            }
            styleSource.put("fill",colorSource);
            styleSource.put("stroke",colorSource);
            source.setStyle(styleSource);

            HashMap<Object,Object> sattributes=new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            source.setAttributes(sattributes);
            nodes.add(source);

            target.setId(record.get("endid").asLong()+"");
             target.setInstanceid(record.get("eattributes").get("instanceid").asString());
            target.setStartvalidtime(record.get("eattributes").get("startvalidtime").asString());
            target.setEndvalidtime(record.get("eattributes").get("endvalidtime").asString());
            if ((record.get("eattributes").get("instanceid").asString() == "null")) {
                tevolution=false;
                target.setLabel(captureName(targetlabel.toLowerCase())+record.get("eattributes").get(targetlabel.toLowerCase()+"id").asString());
               // target.setType("diamond");
            } else {
                tevolution=true;
                target.setLabel(record.get("eattributes").get("instanceid").asString());
                target.setComboId(targetlabel+record.get("eattributes").get(targetlabel.toLowerCase()+"id").asString());

            }
            HashMap<Object,Object> styleTarget =new HashMap<>();
            String colorTarget;
            switch(targetlabel.toLowerCase()){
                case "user" :
                    colorTarget="#FF6666";
                    break;
                case "item" :
                    colorTarget="#FFFFCC";
                    break;
                default :
                    colorTarget="#FFFF00";
            }
            styleTarget.put("fill",colorTarget);
            styleTarget.put("stroke",colorTarget);
            target.setStyle(styleTarget);

            HashMap<Object,Object> eattributes=new HashMap<>();
            eattributes.putAll(record.get("eattributes").asMap());
            target.setAttributes(eattributes);
            nodes.add(target);

            //edge
            edge.setId(record.get("relationid").asLong()+"");
            edge.setSource(source.getId());
            edge.setTarget(target.getId());
            edge.setTypeEdge(record.get("type").asString());
            HashMap<Object,Object> rattributes=new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);

            HashMap<Object,Object> styleEdge =new HashMap<>();
            String colorEdge;
            switch(label.toLowerCase()){
                case "addtocart" :
                    colorEdge="#FFEEE4";
                    break;
                case "belongto" :
                    colorEdge="#CFCFCF";
                    break;
                case "subCategory" :
                    colorEdge="#AAABD3";
                    break;
                case "view" :
                    colorEdge="#EEB4B4";
                    break;
                default :
                    colorEdge="#CDBE70";
            }
            styleEdge.put("stroke",colorEdge);
            edge.setStyle(styleEdge);
            edges.add(edge);
            //combo
            if(sevolution) {
                scombo.setId(source.getComboId());
                scombo.setLabel(source.getComboId());
                HashMap<Object,Object> styleSCombo =new HashMap<>();
                String colorSCombo="#006699";
                styleSCombo.put("fill",colorSCombo);
                styleSCombo.put("stroke",colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);
            }
            if(tevolution){
                tcombo.setId(target.getComboId());
                tcombo.setLabel(target.getComboId());
                HashMap<Object,Object> styleTCombo =new HashMap<>();
                String colorTCombo="#006699";
                styleTCombo.put("fill",colorTCombo);
                styleTCombo.put("stroke",colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);}
        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);



        HashMap<Object,Object> res = new HashMap<>();


        res.put("nodes",nodeNoDupli);
        res.put("edges",edgeNoDupli);
        res.put("combos",listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }



    //APIs of nodes for list
    public static HashMap<Object,Object> searchNodeId(String label, int nbPage){
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
//        Result resNbChange=session.run("match(n:"+captureName(label.toLowerCase())+") return count(n."+label.toLowerCase()+"id) as nb, n."+label.toLowerCase()+"id ");
//        ArrayList<Integer> nbchanges=new ArrayList<>();
//        while (resNbChange.hasNext()){
//            Record record=resNbChange.next();
//            nbchanges.add(record.get("nb").asInt() );
//
//        }
        Result result = null;
        ArrayList<Object> nodes=new ArrayList<>();

        if(label.toLowerCase().equals("item")){
            System.out.println("item");
            result = session.run("match(n:Item) RETURN collect(DISTINCT n.itemid)["+(nbPage - 1) * nbDisplay +".."+Integer.parseInt(nbId)*nbPage+"]  as entityid ");
            while (result.hasNext()) {
                Record record = result.next();
                // return an entity
                List<Object> list;
                list=record.get("entityid").asList();
                nodes.addAll(list);
                System.out.println("test"+nodes);
              //  ArrayList<Object> tests= new ArrayList<String>(record.get("entityid").asList());

            }
        }else {
            result = session.run("match(n:" + captureName(label.toLowerCase()) + ") return id(n) as id, n." + label.toLowerCase() + "id as entityid skip " + (nbPage - 1) * nbDisplay + " limit " + nbId);
            String nodeId;
            while (result.hasNext()) {
                Record record = result.next();
                // return an entity
                nodeId=record.get("entityid").asString();
                // nodeId.setEntityid(record.get("entityid").asString());
                nodes.add(nodeId);
            }
        }


        LinkedHashSet<Object> set = new LinkedHashSet<>(nodes);
        ArrayList<Object> listWithoutDuplicateElements = new ArrayList<>(set);
        HashMap<Object,Object> res = new HashMap<>();
        res.put("nodes",listWithoutDuplicateElements);
        res.put("label",captureName(label));
     //   res.put("changes",nbchanges);
        System.out.println("ok");
        session.close();
        driver.close();

        return res;
    }
    //APIs of list for combo graph
    public static HashMap<Object,Object> searchEntityById(String labelN,String id) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        String label=captureName(labelN.toLowerCase());
        Result result = session.run("match(n:"+label+"{"+label.toLowerCase()+"id : '"+id+"' })  return id(n) as id , properties(n) as attributes  ");
        ArrayList<Combo> combos=new ArrayList<Combo>();
        ArrayList<Node> nodes=new ArrayList<Node>();
        Boolean evolution=false;
        while (result.hasNext()) {
            Record record = result.next();
            Combo combo=new Combo();
            combo.setId(captureName(label.toLowerCase())+id);
            combo.setLabel(combo.getId());
            HashMap<Object,Object> styleCombo =new HashMap<>();
            String colorCombo="#006699";
            styleCombo.put("fill",colorCombo);
            styleCombo.put("stroke",colorCombo);
            combo.setStyle(styleCombo);
            combos.add(combo);

            Node node=new Node();
            node.setId(record.get("id").asLong()+"");

            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution=false;
                node.setLabel(captureName(label.toLowerCase())+id+"");
                //node.setType("diamond");
            } else {
                evolution=true;
                node.setLabel(record.get("attributes").get("instanceid").asString());
                node.setComboId(captureName(label.toLowerCase())+id);
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object,Object> attributes =new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());
            node.setAttributes(attributes);
            HashMap<Object,Object> style =new HashMap<>();
            String color;
            String stroke;
            switch(label.toLowerCase()){
                case "user" :
                    color="#FF6666";
                    stroke="#FF6666";
                    break;
                case "item" :
                    color="#FFFFCC";
                    stroke="#FFFFCC";
                    break;
                default :
                    color="#FFFF00";
                    stroke="#FFFF00";

            }
            style.put("fill",color);
            style.put("stroke",stroke);

            node.setStyle(style);
            nodes.add(node);

        }


        HashMap<Object,Object> res = new HashMap<>();
        res.put("nodes",nodes);
        if (evolution){
            HashSet<Combo> set = new HashSet<Combo>(combos);
            combos = new ArrayList<Combo>(set);
            res.put("combos",combos);}
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


    //APIs of relations for list
    public static HashMap<Object,Object> searchEdgeId(String label, int nbPage) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        String source;
        String tartget;
        switch (label.toLowerCase()) {
            case "belongto":
                source = "item";
                tartget= "category";
                break;
            case "subcategory":
                source = "category";
                tartget= "category";
                break;
            case "view":
            case "transaction":
            case "addtocart":
            default:
                source = "user";
                tartget= "item";
                break;
        }
//        Result resNbChange=session.run("match(s:"+captureName(source)+")-[r:"+captureName(label.toLowerCase())+"]-(t:"+captureName(tartget)+") return count(r),s."+source+"id,t."+tartget+"id limit "+nbId);
//       // System.out.println("match(s:"+captureName(source)+")-[r:"+captureName(label.toLowerCase())+"]-(t:"+captureName(tartget)+") return count(r),s."+source+"id,t."+tartget+"id limit nbId");
//        ArrayList<Integer> nbchanges=new ArrayList<>();
//        while (resNbChange.hasNext()){
//            Record record=resNbChange.next();
//            nbchanges.add(record.get("nb").asInt() );
//
//        }
        String sourceLabel=null;
        String targetLabel=null;
        Result resLabel = session.run("match()-[r:"+captureName(label.toLowerCase())+"]-() return  labels(startNode(r)) as source, labels(endNode(r)) as target limit 1");
        while (resLabel.hasNext()){
            Record record =resLabel.next();
            sourceLabel=record.get("source").get(0).asString();
            targetLabel=record.get("target").get(0).asString();
        }
        Result result = session.run("match(s:"+sourceLabel+")-[r:"+captureName(label.toLowerCase())+"]->(t:"+targetLabel+") return s."+sourceLabel.toLowerCase()+"id as source, t."+targetLabel.toLowerCase()+"id as target skip "+(nbPage-1)*nbDisplay+" limit "+nbId);

        ArrayList<EdgeId> edgeIds =new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            EdgeId edgeId=new EdgeId();
            edgeId.setSource(record.get("source").asString());
            edgeId.setTarget(record.get("target").asString());

            edgeIds.add(edgeId);

        }
        LinkedHashSet<EdgeId> set = new LinkedHashSet<>(edgeIds);
        ArrayList<EdgeId> edgeIdsAfter = new ArrayList<EdgeId>(set);


        HashMap<Object,Object> res = new HashMap<>();
        res.put("edges",edgeIdsAfter);
        res.put("sourceLabel",sourceLabel);
        res.put("targetLabel",targetLabel);
        res.put("label",captureName(label.toLowerCase()));
        //res.put("changes",nbchanges);


        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }
    //APIs of list for combo graph
    public static HashMap<Object,Object> searchEdgeById(String label,String sourceLabel, String targetLabel,String sourceId,String targetId) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Result result = session.run("match(source:"+captureName(sourceLabel.toLowerCase())+")-[r:"+captureName(label.toLowerCase())+"]-(target:"+captureName(targetLabel.toLowerCase())+") where source."+sourceLabel.toLowerCase()+"id='"+sourceId+"' and target."+targetLabel.toLowerCase()+"id='"+targetId+"' return  id(r) as relationid, id(source) as sourceid, properties(source) as sattributes, id(target) as targetid, properties(target) as tattributes, properties(r) as rattributes ");

        ArrayList<Node> nodes=new ArrayList<>();
        ArrayList<Edge> edges =new ArrayList<>();
        ArrayList<Combo> combos=new ArrayList<>();
        Boolean sevolution=false;
        Boolean tevolution=false;

        while (result.hasNext()) {
            Record record = result.next();
            Node snode= new Node();
            Node tnode= new Node();
            Edge edge=new Edge();
            Combo scombo=new Combo();
            Combo tcombo=new Combo();
            //source
            snode.setId(record.get("sourceid").asLong()+"");

            snode.setInstanceid(record.get("sattributes").get("instanceid").asString());
            snode.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            snode.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution=false;
                snode.setLabel(captureName(sourceLabel.toLowerCase())+sourceId);
              //  snode.setType("diamond");
            } else {
                sevolution=true;
                snode.setLabel(record.get("sattributes").get("instanceid").asString());
                snode.setComboId(sourceLabel+sourceId);
            }
            HashMap<Object,Object> sattributes = new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            snode.setAttributes(sattributes);
            HashMap<Object,Object> styleSource =new HashMap<>();
            String colorSource;
            switch(sourceLabel.toLowerCase()){
                case "user" :
                    colorSource="#FF6666";
                    break;
                case "item" :
                    colorSource="#FFFFCC";
                    break;
                default :
                    colorSource="#FFFF00";
            }
            styleSource.put("fill",colorSource);
            styleSource.put("stroke",colorSource);
            snode.setStyle(styleSource);
            nodes.add(snode);

            //target
            tnode.setId(record.get("targetid").asLong()+"");

            tnode.setInstanceid(record.get("tattributes").get("instanceid").asString());
            tnode.setStartvalidtime(record.get("tattributes").get("startvalidtime").asString());
            tnode.setEndvalidtime(record.get("tattributes").get("endvalidtime").asString());
            if ((record.get("tattributes").get("instanceid").asString() == "null")) {
                tevolution=false;
                tnode.setLabel(captureName(targetLabel.toLowerCase())+targetId);
              //  tnode.setType("diamond");
            } else {
                tevolution=true;
                tnode.setLabel(record.get("tattributes").get("instanceid").asString());
                tnode.setComboId(targetLabel+targetId);
            }
            HashMap<Object,Object> tattributes = new HashMap<>();
            tattributes.putAll(record.get("tattributes").asMap());
            tnode.setAttributes(tattributes);
            HashMap<Object,Object> styleTarget =new HashMap<>();
            String colorTarget;
            switch(targetLabel.toLowerCase()){
                case "user" :
                    colorTarget="#FF6666";
                    break;
                case "item" :
                    colorTarget="#FFFFCC";
                    break;
                default :
                    colorTarget="#FFFF00";
            }
            styleTarget.put("fill",colorTarget);
            styleTarget.put("stroke",colorTarget);
            tnode.setStyle(styleTarget);
            nodes.add(tnode);

            //edge
            edge.setId(record.get("relationid").asLong()+"");
            edge.setSource(snode.getId());
            edge.setTarget(tnode.getId());
            edge.setTypeEdge(captureName(label.toLowerCase()));
            HashMap<Object,Object> rattributes=new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);
            HashMap<Object,Object> styleEdge =new HashMap<>();
            String colorEdge;
            switch(label.toLowerCase()){
                case "addtocart" :
                    colorEdge="#FFEEE4";
                    break;
                case "belongto" :
                    colorEdge="#CFCFCF";
                    break;
                case "subCategory" :
                    colorEdge="#AAABD3";
                    break;
                case "view" :
                    colorEdge="#EEB4B4";
                    break;
                default :
                    colorEdge="#CDBE70";
            }
            styleEdge.put("stroke",colorEdge);
            edge.setStyle(styleEdge);

            edges.add(edge);


            //combo
            if (sevolution){
                scombo.setId(snode.getComboId());
                scombo.setLabel(scombo.getId());
                HashMap<Object,Object> styleSCombo =new HashMap<>();
                String colorSCombo="#006699";
                styleSCombo.put("fill",colorSCombo);
                styleSCombo.put("stroke",colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);}
            if (tevolution){
                tcombo.setId(tnode.getComboId());
                tcombo.setLabel(tcombo.getId());
                HashMap<Object,Object> styleTCombo =new HashMap<>();
                String colorTCombo="#006699";
                styleTCombo.put("fill",colorTCombo);
                styleTCombo.put("stroke",colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);}
        }
        LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);
        HashMap<Object,Object> res = new HashMap<>();
        res.put("nodes",nodes);
        res.put("edges",edges);
        res.put("combos",listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


//-----------------------------with time----------------------------------------------
    //http://localhost:8080/kaggle/time/user/time_get?st=2015-07-09 06:00:00 &et=2016-07-10 08:00:00
    public static HashMap<Object,Object> searchNodeComboWithTime(String label,String st, String et) {
    Driver driver = ConnexionNeo4J.connectDB();
    Session session = driver.session();
    HashMap<Object,Object> res = new HashMap<>();
    Boolean evolution=false;
    Result result = session.run("MATCH (n:"+captureName(label.toLowerCase())+") WHERE datetime(n.startvalidtime)< datetime('"+et+"') AND (datetime(n.endvalidtime)>=datetime('"+st+"') OR datetime(n.endvalidtime) IS NULL)" +
            " return properties(n) as attributes,id(n) as id," +
            "n."+label.toLowerCase()+"id as entityid limit  "+nb);
    ArrayList<Combo> combos=new ArrayList<>();
    ArrayList<Node> nodes=new ArrayList<>();
    while (result.hasNext()) {
        Record record = result.next();
        Combo combo=new Combo();
        combo.setId(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString());
        combo.setLabel(combo.getId());
        HashMap<Object,Object> styleCombo =new HashMap<>();
        String colorCombo="#006699";
        styleCombo.put("fill",colorCombo);
        styleCombo.put("stroke",colorCombo);

        combo.setStyle(styleCombo);
        combos.add(combo);
        Node node=new Node();
        node.setId(""+record.get("id").asLong());
        node.setInstanceid(record.get("attributes").get("instanceid").asString());
        if ((record.get("attributes").get("instanceid").asString() == "null")) {
            evolution=false;
            node.setLabel(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString()); //node.getId()
            node.setComboId("null");

            //  node.setType("diamond");
        } else {
            evolution=true;
            node.setComboId(captureName(label.toLowerCase())+record.get("attributes").get(label.toLowerCase()+"id").asString());
            node.setLabel(node.getInstanceid());
        }
        node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
        node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
        HashMap<Object,Object> attributes =new HashMap<>();
        attributes.putAll(record.get("attributes").asMap());
        HashMap<Object,Object> style =new HashMap<>();
        String color;
        String stroke;
        switch(label.toLowerCase()){
            case "user" :
                color="#FF6666";
                stroke="#FF6666";
                break;
            case "item" :
                color="#FFFFCC";
                stroke="#FFFFCC";
                break;
            default :
                color="#FFFF00";
                stroke="#FFFF00";

        }
        style.put("fill",color);
        style.put("stroke",stroke);

        node.setStyle(style);
        node.setAttributes(attributes);
        nodes.add(node);
    }

    LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
    ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);

    res.put("nodes",nodes);
    if (evolution) {
        res.put("combos", listWithoutDuplicateElements);
    }
    System.out.println("ok");
    session.close();
    driver.close();
    return res;
}

    public static HashMap<Object,Object> searchEdgeComboWithTime(String label,String sourcelabel, String targetlabel,String st, String et) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Boolean sevolution=false;
        Boolean tevolution=false;
        String item=targetlabel.toLowerCase();
        if(targetlabel.toLowerCase()=="item"){
            item=targetlabel.toLowerCase();
        }
        else if (sourcelabel.toLowerCase()=="item"){
            item=sourcelabel;
        }
        Result result = session.run("match(t:"+captureName(targetlabel.toLowerCase())+")<-[r:"+captureName(label.toLowerCase())+"]-(s:"+captureName(sourcelabel.toLowerCase())+") return r as relation,id(r) as relationid, id(s) as startid,properties(s) as sattributes,id(t) as endid,properties(t) as eattributes,type(r) as type, " +
                "properties(r) as rattributes order by t."+item+"id limit "+nb);
        ArrayList<Node> nodes=new ArrayList<>();
        ArrayList<Edge> edges=new ArrayList<>();
        ArrayList<Combo> combos=new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Node source =new Node();
            Node target =new Node();
            Edge edge= new Edge();
            Combo scombo=new Combo();
            Combo tcombo=new Combo();

            // node source and node target
            source.setId(record.get("startid").asLong()+"");
            source.setInstanceid(record.get("sattributes").get("instanceid").asString());
            source.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            source.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution=false;
                source.setLabel(captureName(sourcelabel.toLowerCase())+record.get("sattributes").get(sourcelabel.toLowerCase()+"id").asString());
                //  source.setType("diamond");
            } else {
                sevolution=true;
                source.setComboId(sourcelabel+record.get("sattributes").get(sourcelabel.toLowerCase()+"id").asString());
                source.setLabel(source.getInstanceid());
            }
            HashMap<Object,Object> styleSource =new HashMap<>();
            String colorSource;
            switch(sourcelabel.toLowerCase()){
                case "user" :
                    colorSource="#FF6666";
                    break;
                case "item" :
                    colorSource="#FFFFCC";
                    break;
                default :
                    colorSource="#FFFF00";
            }
            styleSource.put("fill",colorSource);
            styleSource.put("stroke",colorSource);
            source.setStyle(styleSource);

            HashMap<Object,Object> sattributes=new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            source.setAttributes(sattributes);
            nodes.add(source);

            target.setId(record.get("endid").asLong()+"");
            target.setInstanceid(record.get("eattributes").get("instanceid").asString());
            target.setStartvalidtime(record.get("eattributes").get("startvalidtime").asString());
            target.setEndvalidtime(record.get("eattributes").get("endvalidtime").asString());
            if ((record.get("eattributes").get("instanceid").asString() == "null")) {
                tevolution=false;
                target.setLabel(captureName(targetlabel.toLowerCase())+record.get("eattributes").get(targetlabel.toLowerCase()+"id").asString());
                // target.setType("diamond");
            } else {
                tevolution=true;
                target.setLabel(record.get("eattributes").get("instanceid").asString());
                target.setComboId(targetlabel+record.get("eattributes").get(targetlabel.toLowerCase()+"id").asString());

            }
            HashMap<Object,Object> styleTarget =new HashMap<>();
            String colorTarget;
            switch(targetlabel.toLowerCase()){
                case "user" :
                    colorTarget="#FF6666";
                    break;
                case "item" :
                    colorTarget="#FFFFCC";
                    break;
                default :
                    colorTarget="#FFFF00";
            }
            styleTarget.put("fill",colorTarget);
            styleTarget.put("stroke",colorTarget);
            target.setStyle(styleTarget);

            HashMap<Object,Object> eattributes=new HashMap<>();
            eattributes.putAll(record.get("eattributes").asMap());
            target.setAttributes(eattributes);
            nodes.add(target);

            //edge
            edge.setId(record.get("relationid").asLong()+"");
            edge.setSource(source.getId());
            edge.setTarget(target.getId());
            edge.setTypeEdge(record.get("type").asString());
            HashMap<Object,Object> rattributes=new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);

            HashMap<Object,Object> styleEdge =new HashMap<>();
            String colorEdge;
            switch(label.toLowerCase()){
                case "addtocart" :
                    colorEdge="#FFEEE4";
                    break;
                case "belongto" :
                    colorEdge="#CFCFCF";
                    break;
                case "subCategory" :
                    colorEdge="#AAABD3";
                    break;
                case "view" :
                    colorEdge="#EEB4B4";
                    break;
                default :
                    colorEdge="#CDBE70";
            }
            styleEdge.put("stroke",colorEdge);
            edge.setStyle(styleEdge);
            edges.add(edge);
            //combo
            if(sevolution) {
                scombo.setId(source.getComboId());
                scombo.setLabel(source.getComboId());
                HashMap<Object,Object> styleSCombo =new HashMap<>();
                String colorSCombo="#006699";
                styleSCombo.put("fill",colorSCombo);
                styleSCombo.put("stroke",colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);
            }
            if(tevolution){
                tcombo.setId(target.getComboId());
                tcombo.setLabel(target.getComboId());
                HashMap<Object,Object> styleTCombo =new HashMap<>();
                String colorTCombo="#006699";
                styleTCombo.put("fill",colorTCombo);
                styleTCombo.put("stroke",colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);}
        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);



        HashMap<Object,Object> res = new HashMap<>();


        res.put("nodes",nodeNoDupli);
        res.put("edges",edgeNoDupli);
        res.put("combos",listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


    public static void main(String[] args) throws Exception {
        searchNodeComboWithTime("user","2015-07-09 06:00:00","2016-07-10 08:00:00");

        System.out.println("----------------End--------------");

    }


}
