package DAO;

import Model.*;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.units.qual.C;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.*;
import java.util.stream.Collectors;

import static DAO.outils.captureName;

/**
 * @author RRH
 */
public class MethodeNeo4J {

    private static String nb = "20";
    private static String nbId = "21";
    private static int nbDisplay = 21;


    //APIs for combo graph

    public static HashMap<Object, Object> searchNodeCombo(String label) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        HashMap<Object, Object> res = new HashMap<>();
        Boolean evolution = false;
        Result result = session.run("Match (n:" + captureName(label.toLowerCase()) + ")return properties(n) as attributes,id(n) as id," +
                "n." + label.toLowerCase() + "id as entityid  limit  " + nb); //order by n."+label.toLowerCase()+"id
        ArrayList<Combo> combos = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Combo combo = new Combo();
            combo.setId(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString());
            combo.setLabel(combo.getId());
            HashMap<Object, Object> styleCombo = new HashMap<>();
            String colorCombo = "#006699";
            styleCombo.put("fill", colorCombo);
            styleCombo.put("stroke", colorCombo);

            combo.setStyle(styleCombo);
            combos.add(combo);
            Node node = new Node();
            node.setId("" + record.get("id").asLong());
            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution = false;
                node.setLabel(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString()); //node.getId()
                node.setComboId("null");

                //  node.setType("diamond");
            } else {
                evolution = true;
                node.setComboId(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString());
                node.setLabel(node.getInstanceid());
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object, Object> attributes = new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());
            HashMap<Object, Object> style = new HashMap<>();
            String color;
            String stroke;
            switch (label.toLowerCase()) {
                case "user":
                    color = "#FF6666";
                    stroke = "#FF6666";
                    break;
                case "item":
                    color = "#FFFFCC";
                    stroke = "#FFFFCC";
                    break;
                default:
                    color = "#FFFF00";
                    stroke = "#FFFF00";

            }
            style.put("fill", color);
            style.put("stroke", stroke);

            node.setStyle(style);
            node.setAttributes(attributes);
            nodes.add(node);
        }

        LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);

        res.put("nodes", nodes);
        if (evolution) {
            res.put("combos", listWithoutDuplicateElements);
        }
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    public static HashMap<Object, Object> searchEdgeCombo(String label, String sourcelabel, String targetlabel) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Boolean sevolution = false;
        Boolean tevolution = false;
        String item = targetlabel.toLowerCase();
        if (targetlabel.toLowerCase() == "item") {
            item = targetlabel.toLowerCase();
        } else if (sourcelabel.toLowerCase() == "item") {
            item = sourcelabel;
        }
        Result result = session.run("match(t:" + captureName(targetlabel.toLowerCase()) + ")<-[r:" + captureName(label.toLowerCase()) + "]-(s:" + captureName(sourcelabel.toLowerCase()) + ") return r as relation,id(r) as relationid, id(s) as startid,properties(s) as sattributes,id(t) as endid,properties(t) as eattributes,type(r) as type, " +
                "properties(r) as rattributes  limit " + nb); //order by t."+item+"id
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Node source = new Node();
            Node target = new Node();
            Edge edge = new Edge();
            Combo scombo = new Combo();
            Combo tcombo = new Combo();

            // node source and node target
            source.setId(record.get("startid").asLong() + "");
            source.setInstanceid(record.get("sattributes").get("instanceid").asString());
            source.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            source.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution = false;
                source.setLabel(captureName(sourcelabel.toLowerCase()) + record.get("sattributes").get(sourcelabel.toLowerCase() + "id").asString());
                //  source.setType("diamond");
            } else {
                sevolution = true;
                source.setComboId(sourcelabel + record.get("sattributes").get(sourcelabel.toLowerCase() + "id").asString());
                source.setLabel(source.getInstanceid());
            }
            HashMap<Object, Object> styleSource = new HashMap<>();
            String colorSource;
            switch (sourcelabel.toLowerCase()) {
                case "user":
                    colorSource = "#FF6666";
                    break;
                case "item":
                    colorSource = "#FFFFCC";
                    break;
                default:
                    colorSource = "#FFFF00";
            }
            styleSource.put("fill", colorSource);
            styleSource.put("stroke", colorSource);
            source.setStyle(styleSource);

            HashMap<Object, Object> sattributes = new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            source.setAttributes(sattributes);
            nodes.add(source);

            target.setId(record.get("endid").asLong() + "");
            target.setInstanceid(record.get("eattributes").get("instanceid").asString());
            target.setStartvalidtime(record.get("eattributes").get("startvalidtime").asString());
            target.setEndvalidtime(record.get("eattributes").get("endvalidtime").asString());
            if ((record.get("eattributes").get("instanceid").asString() == "null")) {
                tevolution = false;
                target.setLabel(captureName(targetlabel.toLowerCase()) + record.get("eattributes").get(targetlabel.toLowerCase() + "id").asString());
                // target.setType("diamond");
            } else {
                tevolution = true;
                target.setLabel(record.get("eattributes").get("instanceid").asString());
                target.setComboId(targetlabel + record.get("eattributes").get(targetlabel.toLowerCase() + "id").asString());

            }
            HashMap<Object, Object> styleTarget = new HashMap<>();
            String colorTarget;
            switch (targetlabel.toLowerCase()) {
                case "user":
                    colorTarget = "#FF6666";
                    break;
                case "item":
                    colorTarget = "#FFFFCC";
                    break;
                default:
                    colorTarget = "#FFFF00";
            }
            styleTarget.put("fill", colorTarget);
            styleTarget.put("stroke", colorTarget);
            target.setStyle(styleTarget);

            HashMap<Object, Object> eattributes = new HashMap<>();
            eattributes.putAll(record.get("eattributes").asMap());
            target.setAttributes(eattributes);
            nodes.add(target);

            //edge
            edge.setId(record.get("relationid").asLong() + "");
            edge.setSource(source.getId());
            edge.setTarget(target.getId());
            edge.setTypeEdge(record.get("type").asString());
            HashMap<Object, Object> rattributes = new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);

            HashMap<Object, Object> styleEdge = new HashMap<>();
            String colorEdge;
            switch (label.toLowerCase()) {
                case "addtocart":
                    colorEdge = "#FFEEE4";
                    break;
                case "belongto":
                    colorEdge = "#CFCFCF";
                    break;
                case "subCategory":
                    colorEdge = "#AAABD3";
                    break;
                case "view":
                    colorEdge = "#EEB4B4";
                    break;
                default:
                    colorEdge = "#CDBE70";
            }
            styleEdge.put("stroke", colorEdge);
            edge.setStyle(styleEdge);
            edges.add(edge);
            //combo
            if (sevolution) {
                scombo.setId(source.getComboId());
                scombo.setLabel(source.getComboId());
                HashMap<Object, Object> styleSCombo = new HashMap<>();
                String colorSCombo = "#006699";
                styleSCombo.put("fill", colorSCombo);
                styleSCombo.put("stroke", colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);
            }
            if (tevolution) {
                tcombo.setId(target.getComboId());
                tcombo.setLabel(target.getComboId());
                HashMap<Object, Object> styleTCombo = new HashMap<>();
                String colorTCombo = "#006699";
                styleTCombo.put("fill", colorTCombo);
                styleTCombo.put("stroke", colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);
            }
        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);


        HashMap<Object, Object> res = new HashMap<>();


        res.put("nodes", nodeNoDupli);
        res.put("edges", edgeNoDupli);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    //APIs of nodes for list
    public static HashMap<Object, Object> searchNodeId(String label, int nbPage) {
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
        ArrayList<Object> nodes = new ArrayList<>();

        if (label.toLowerCase().equals("item")) {
            System.out.println("item");
            result = session.run("match(n:Item) RETURN collect(DISTINCT n.itemid)[" + (nbPage - 1) * nbDisplay + ".." + Integer.parseInt(nbId) * nbPage + "]  as entityid ");
            while (result.hasNext()) {
                Record record = result.next();
                // return an entity
                List<Object> list;
                list = record.get("entityid").asList();
                nodes.addAll(list);
                System.out.println("test" + nodes);
                //  ArrayList<Object> tests= new ArrayList<String>(record.get("entityid").asList());

            }
        } else {
            result = session.run("match(n:" + captureName(label.toLowerCase()) + ") return id(n) as id, n." + label.toLowerCase() + "id as entityid skip " + (nbPage - 1) * nbDisplay + " limit " + nbId);
            String nodeId;
            while (result.hasNext()) {
                Record record = result.next();
                // return an entity
                nodeId = record.get("entityid").asString();
                // nodeId.setEntityid(record.get("entityid").asString());
                nodes.add(nodeId);
            }
        }


        LinkedHashSet<Object> set = new LinkedHashSet<>(nodes);
        ArrayList<Object> listWithoutDuplicateElements = new ArrayList<>(set);
        HashMap<Object, Object> res = new HashMap<>();
        res.put("nodes", listWithoutDuplicateElements);
        res.put("label", captureName(label));
        //   res.put("changes",nbchanges);
        System.out.println("ok");
        session.close();
        driver.close();

        return res;
    }

    //APIs of relations for list
    public static HashMap<Object, Object> searchEdgeId(String label, int nbPage) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        String source;
        String tartget;
        switch (label.toLowerCase()) {
            case "belongto":
                source = "item";
                tartget = "category";
                break;
            case "subcategory":
                source = "category";
                tartget = "category";
                break;
            case "view":
            case "transaction":
            case "addtocart":
            default:
                source = "user";
                tartget = "item";
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
        String sourceLabel = null;
        String targetLabel = null;
        Result resLabel = session.run("match()-[r:" + captureName(label.toLowerCase()) + "]-() return  labels(startNode(r)) as source, labels(endNode(r)) as target limit 1");
        while (resLabel.hasNext()) {
            Record record = resLabel.next();
            sourceLabel = record.get("source").get(0).asString();
            targetLabel = record.get("target").get(0).asString();
        }
        Result result = session.run("match(s:" + sourceLabel + ")-[r:" + captureName(label.toLowerCase()) + "]->(t:" + targetLabel + ") return distinct s." + sourceLabel.toLowerCase() + "id as source, t." + targetLabel.toLowerCase() + "id as target skip " + (nbPage - 1) * nbDisplay + " limit " + nbId);

        ArrayList<EdgeId> edgeIds = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            EdgeId edgeId = new EdgeId();
            edgeId.setSource(record.get("source").asString());
            edgeId.setTarget(record.get("target").asString());

            edgeIds.add(edgeId);

        }
        LinkedHashSet<EdgeId> set = new LinkedHashSet<>(edgeIds);
        ArrayList<EdgeId> edgeIdsAfter = new ArrayList<EdgeId>(set);


        HashMap<Object, Object> res = new HashMap<>();
        res.put("edges", edgeIdsAfter);
        res.put("sourceLabel", sourceLabel);
        res.put("targetLabel", targetLabel);
        res.put("label", captureName(label.toLowerCase()));
        //res.put("changes",nbchanges);


        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    //APIs of list for combo graph
    public static HashMap<Object, Object> searchEntityById(String labelN, String id) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        String label = captureName(labelN.toLowerCase());
        Result result = session.run("match(n:" + label + "{" + label.toLowerCase() + "id : '" + id + "' })  return id(n) as id , properties(n) as attributes  ");
        ArrayList<Combo> combos = new ArrayList<Combo>();
        ArrayList<Node> nodes = new ArrayList<Node>();
        Boolean evolution = false;
        while (result.hasNext()) {
            Record record = result.next();
            Combo combo = new Combo();
            combo.setId(captureName(label.toLowerCase()) + id);
            combo.setLabel(combo.getId());
            HashMap<Object, Object> styleCombo = new HashMap<>();
            String colorCombo = "#006699";
            styleCombo.put("fill", colorCombo);
            styleCombo.put("stroke", colorCombo);
            combo.setStyle(styleCombo);
            combos.add(combo);

            Node node = new Node();
            node.setId(record.get("id").asLong() + "");

            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution = false;
                node.setLabel(captureName(label.toLowerCase()) + id + "");
                //node.setType("diamond");
            } else {
                evolution = true;
                node.setLabel(record.get("attributes").get("instanceid").asString());
                node.setComboId(captureName(label.toLowerCase()) + id);
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object, Object> attributes = new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());
            node.setAttributes(attributes);
            HashMap<Object, Object> style = new HashMap<>();
            String color;
            String stroke;
            switch (label.toLowerCase()) {
                case "user":
                    color = "#FF6666";
                    stroke = "#FF6666";
                    break;
                case "item":
                    color = "#FFFFCC";
                    stroke = "#FFFFCC";
                    break;
                default:
                    color = "#FFFF00";
                    stroke = "#FFFF00";

            }
            style.put("fill", color);
            style.put("stroke", stroke);

            node.setStyle(style);
            nodes.add(node);

        }


        HashMap<Object, Object> res = new HashMap<>();
        res.put("nodes", nodes);
        if (evolution) {
            HashSet<Combo> set = new HashSet<Combo>(combos);
            combos = new ArrayList<Combo>(set);
            res.put("combos", combos);
        }
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    //APIs of list for combo graph
    public static HashMap<Object, Object> searchEdgeById(String label, String sourceLabel, String targetLabel, String sourceId, String targetId) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Result result = session.run("match(source:" + captureName(sourceLabel.toLowerCase()) + ")-[r:" + captureName(label.toLowerCase()) + "]-(target:" + captureName(targetLabel.toLowerCase()) + ") where source." + sourceLabel.toLowerCase() + "id='" + sourceId + "' and target." + targetLabel.toLowerCase() + "id='" + targetId + "' return  id(r) as relationid, id(source) as sourceid, properties(source) as sattributes, id(target) as targetid, properties(target) as tattributes, properties(r) as rattributes ");

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        Boolean sevolution = false;
        Boolean tevolution = false;

        while (result.hasNext()) {
            Record record = result.next();
            Node snode = new Node();
            Node tnode = new Node();
            Edge edge = new Edge();
            Combo scombo = new Combo();
            Combo tcombo = new Combo();
            //source
            snode.setId(record.get("sourceid").asLong() + "");

            snode.setInstanceid(record.get("sattributes").get("instanceid").asString());
            snode.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            snode.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution = false;
                snode.setLabel(captureName(sourceLabel.toLowerCase()) + sourceId);
                //  snode.setType("diamond");
            } else {
                sevolution = true;
                snode.setLabel(record.get("sattributes").get("instanceid").asString());
                snode.setComboId(sourceLabel + sourceId);
            }
            HashMap<Object, Object> sattributes = new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            snode.setAttributes(sattributes);
            HashMap<Object, Object> styleSource = new HashMap<>();
            String colorSource;
            switch (sourceLabel.toLowerCase()) {
                case "user":
                    colorSource = "#FF6666";
                    break;
                case "item":
                    colorSource = "#FFFFCC";
                    break;
                default:
                    colorSource = "#FFFF00";
            }
            styleSource.put("fill", colorSource);
            styleSource.put("stroke", colorSource);
            snode.setStyle(styleSource);
            nodes.add(snode);

            //target
            tnode.setId(record.get("targetid").asLong() + "");

            tnode.setInstanceid(record.get("tattributes").get("instanceid").asString());
            tnode.setStartvalidtime(record.get("tattributes").get("startvalidtime").asString());
            tnode.setEndvalidtime(record.get("tattributes").get("endvalidtime").asString());
            if ((record.get("tattributes").get("instanceid").asString() == "null")) {
                tevolution = false;
                tnode.setLabel(captureName(targetLabel.toLowerCase()) + targetId);
                //  tnode.setType("diamond");
            } else {
                tevolution = true;
                tnode.setLabel(record.get("tattributes").get("instanceid").asString());
                tnode.setComboId(targetLabel + targetId);
            }
            HashMap<Object, Object> tattributes = new HashMap<>();
            tattributes.putAll(record.get("tattributes").asMap());
            tnode.setAttributes(tattributes);
            HashMap<Object, Object> styleTarget = new HashMap<>();
            String colorTarget;
            switch (targetLabel.toLowerCase()) {
                case "user":
                    colorTarget = "#FF6666";
                    break;
                case "item":
                    colorTarget = "#FFFFCC";
                    break;
                default:
                    colorTarget = "#FFFF00";
            }
            styleTarget.put("fill", colorTarget);
            styleTarget.put("stroke", colorTarget);
            tnode.setStyle(styleTarget);
            nodes.add(tnode);

            //edge
            edge.setId(record.get("relationid").asLong() + "");
            edge.setSource(snode.getId());
            edge.setTarget(tnode.getId());
            edge.setTypeEdge(captureName(label.toLowerCase()));
            HashMap<Object, Object> rattributes = new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);
            HashMap<Object, Object> styleEdge = new HashMap<>();
            String colorEdge;
            switch (label.toLowerCase()) {
                case "addtocart":
                    colorEdge = "#FFEEE4";
                    break;
                case "belongto":
                    colorEdge = "#CFCFCF";
                    break;
                case "subCategory":
                    colorEdge = "#AAABD3";
                    break;
                case "view":
                    colorEdge = "#EEB4B4";
                    break;
                default:
                    colorEdge = "#CDBE70";
            }
            styleEdge.put("stroke", colorEdge);
            edge.setStyle(styleEdge);

            edges.add(edge);


            //combo
            if (sevolution) {
                scombo.setId(snode.getComboId());
                scombo.setLabel(scombo.getId());
                HashMap<Object, Object> styleSCombo = new HashMap<>();
                String colorSCombo = "#006699";
                styleSCombo.put("fill", colorSCombo);
                styleSCombo.put("stroke", colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);
            }
            if (tevolution) {
                tcombo.setId(tnode.getComboId());
                tcombo.setLabel(tcombo.getId());
                HashMap<Object, Object> styleTCombo = new HashMap<>();
                String colorTCombo = "#006699";
                styleTCombo.put("fill", colorTCombo);
                styleTCombo.put("stroke", colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);
            }
        }
        LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);
        HashMap<Object, Object> res = new HashMap<>();
        res.put("nodes", nodes);
        res.put("edges", edges);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


    //-----------------------------with time----------------------------------------------
    //http://localhost:8080/kaggle/time/user/time_get?st=2015-07-09 06:00:00 &et=2016-07-10 08:00:00
    public static HashMap<Object, Object> searchNodeComboWithTime(String label, String st, String et) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        HashMap<Object, Object> res = new HashMap<>();
        Boolean evolution = false;
        Result result = session.run("MATCH (n:" + captureName(label.toLowerCase()) + ") WHERE datetime(n.startvalidtime)< datetime('" + et + "') AND (datetime(n.endvalidtime)>=datetime('" + st + "') OR datetime(n.endvalidtime) IS NULL)" +
                " return properties(n) as attributes,id(n) as id," +
                "n." + label.toLowerCase() + "id as entityid limit  " + nb);
        ArrayList<Combo> combos = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Combo combo = new Combo();
            combo.setId(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString());
            combo.setLabel(combo.getId());
            HashMap<Object, Object> styleCombo = new HashMap<>();
            String colorCombo = "#006699";
            styleCombo.put("fill", colorCombo);
            styleCombo.put("stroke", colorCombo);

            combo.setStyle(styleCombo);
            combos.add(combo);
            Node node = new Node();
            node.setId("" + record.get("id").asLong());
            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution = false;
                node.setLabel(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString()); //node.getId()
                node.setComboId("null");

                //  node.setType("diamond");
            } else {
                evolution = true;
                node.setComboId(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString());
                node.setLabel(node.getInstanceid());
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object, Object> attributes = new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());
            HashMap<Object, Object> style = new HashMap<>();
            String color;
            String stroke;
            switch (label.toLowerCase()) {
                case "user":
                    color = "#FF6666";
                    stroke = "#FF6666";
                    break;
                case "item":
                    color = "#FFFFCC";
                    stroke = "#FFFFCC";
                    break;
                default:
                    color = "#FFFF00";
                    stroke = "#FFFF00";

            }
            style.put("fill", color);
            style.put("stroke", stroke);

            node.setStyle(style);
            node.setAttributes(attributes);
            nodes.add(node);
        }

        LinkedHashSet<Combo> set = new LinkedHashSet<>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<>(set);

        res.put("nodes", nodes);
        if (evolution) {
            res.put("combos", listWithoutDuplicateElements);
        }
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    public static HashMap<Object, Object> searchEdgeComboWithTime(String label, String sourcelabel, String targetlabel, String st, String et) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        Boolean sevolution = false;
        Boolean tevolution = false;
        String item = targetlabel.toLowerCase();
        if (targetlabel.toLowerCase() == "item") {
            item = targetlabel.toLowerCase();
        } else if (sourcelabel.toLowerCase() == "item") {
            item = sourcelabel;
        }
        Result result = session.run("match(t:" + captureName(targetlabel.toLowerCase()) + ")<-[r:" + captureName(label.toLowerCase()) + "]-(s:" + captureName(sourcelabel.toLowerCase()) + ") return r as relation,id(r) as relationid, id(s) as startid,properties(s) as sattributes,id(t) as endid,properties(t) as eattributes,type(r) as type, " +
                "properties(r) as rattributes limit " + nb); // order by t."+item+"id
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Node source = new Node();
            Node target = new Node();
            Edge edge = new Edge();
            Combo scombo = new Combo();
            Combo tcombo = new Combo();

            // node source and node target
            source.setId(record.get("startid").asLong() + "");
            source.setInstanceid(record.get("sattributes").get("instanceid").asString());
            source.setStartvalidtime(record.get("sattributes").get("startvalidtime").asString());
            source.setEndvalidtime(record.get("sattributes").get("endvalidtime").asString());
            if ((record.get("sattributes").get("instanceid").asString() == "null")) {
                sevolution = false;
                source.setLabel(captureName(sourcelabel.toLowerCase()) + record.get("sattributes").get(sourcelabel.toLowerCase() + "id").asString());
                //  source.setType("diamond");
            } else {
                sevolution = true;
                source.setComboId(sourcelabel + record.get("sattributes").get(sourcelabel.toLowerCase() + "id").asString());
                source.setLabel(source.getInstanceid());
            }
            HashMap<Object, Object> styleSource = new HashMap<>();
            String colorSource;
            switch (sourcelabel.toLowerCase()) {
                case "user":
                    colorSource = "#FF6666";
                    break;
                case "item":
                    colorSource = "#FFFFCC";
                    break;
                default:
                    colorSource = "#FFFF00";
            }
            styleSource.put("fill", colorSource);
            styleSource.put("stroke", colorSource);
            source.setStyle(styleSource);

            HashMap<Object, Object> sattributes = new HashMap<>();
            sattributes.putAll(record.get("sattributes").asMap());
            source.setAttributes(sattributes);
            nodes.add(source);

            target.setId(record.get("endid").asLong() + "");
            target.setInstanceid(record.get("eattributes").get("instanceid").asString());
            target.setStartvalidtime(record.get("eattributes").get("startvalidtime").asString());
            target.setEndvalidtime(record.get("eattributes").get("endvalidtime").asString());
            if ((record.get("eattributes").get("instanceid").asString() == "null")) {
                tevolution = false;
                target.setLabel(captureName(targetlabel.toLowerCase()) + record.get("eattributes").get(targetlabel.toLowerCase() + "id").asString());
                // target.setType("diamond");
            } else {
                tevolution = true;
                target.setLabel(record.get("eattributes").get("instanceid").asString());
                target.setComboId(targetlabel + record.get("eattributes").get(targetlabel.toLowerCase() + "id").asString());

            }
            HashMap<Object, Object> styleTarget = new HashMap<>();
            String colorTarget;
            switch (targetlabel.toLowerCase()) {
                case "user":
                    colorTarget = "#FF6666";
                    break;
                case "item":
                    colorTarget = "#FFFFCC";
                    break;
                default:
                    colorTarget = "#FFFF00";
            }
            styleTarget.put("fill", colorTarget);
            styleTarget.put("stroke", colorTarget);
            target.setStyle(styleTarget);

            HashMap<Object, Object> eattributes = new HashMap<>();
            eattributes.putAll(record.get("eattributes").asMap());
            target.setAttributes(eattributes);
            nodes.add(target);

            //edge
            edge.setId(record.get("relationid").asLong() + "");
            edge.setSource(source.getId());
            edge.setTarget(target.getId());
            edge.setTypeEdge(record.get("type").asString());
            HashMap<Object, Object> rattributes = new HashMap<>();
            rattributes.putAll(record.get("rattributes").asMap());
            edge.setAttributes(rattributes);

            HashMap<Object, Object> styleEdge = new HashMap<>();
            String colorEdge;
            switch (label.toLowerCase()) {
                case "addtocart":
                    colorEdge = "#FFEEE4";
                    break;
                case "belongto":
                    colorEdge = "#CFCFCF";
                    break;
                case "subCategory":
                    colorEdge = "#AAABD3";
                    break;
                case "view":
                    colorEdge = "#EEB4B4";
                    break;
                default:
                    colorEdge = "#CDBE70";
            }
            styleEdge.put("stroke", colorEdge);
            edge.setStyle(styleEdge);
            edges.add(edge);
            //combo
            if (sevolution) {
                scombo.setId(source.getComboId());
                scombo.setLabel(source.getComboId());
                HashMap<Object, Object> styleSCombo = new HashMap<>();
                String colorSCombo = "#006699";
                styleSCombo.put("fill", colorSCombo);
                styleSCombo.put("stroke", colorSCombo);
                scombo.setStyle(styleSCombo);
                combos.add(scombo);
            }
            if (tevolution) {
                tcombo.setId(target.getComboId());
                tcombo.setLabel(target.getComboId());
                HashMap<Object, Object> styleTCombo = new HashMap<>();
                String colorTCombo = "#006699";
                styleTCombo.put("fill", colorTCombo);
                styleTCombo.put("stroke", colorTCombo);
                tcombo.setStyle(styleTCombo);
                combos.add(tcombo);
            }
        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);


        HashMap<Object, Object> res = new HashMap<>();


        res.put("nodes", nodeNoDupli);
        res.put("edges", edgeNoDupli);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    public static HashMap<Object, Object> searchSubgraphWithTime(PathData pathData, String st, String et) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();

        // Process received data
        ArrayList<PathNode> pathNodes = new ArrayList<>();
        ArrayList<PathEdge> pathEdges = new ArrayList<>();
        pathEdges = pathData.getEdges();
        pathNodes = pathData.getNodes();
        String path = "p=";
        boolean find = false;
        int index = 0;
        // requete
        //path
        while ((!find) & index < pathNodes.size()) {
            System.out.println(pathEdges.get(0).getSource());
            if (pathNodes.get(index).getId().equals(pathEdges.get(0).getSource())) {
                System.out.println("trouve");
                path = path + "(:" + captureName(pathNodes.get(index).getLabelForQuery().replaceAll("\\d+", "").toLowerCase()) + ")";
                find = true;
            } else {
                System.out.println(pathNodes.get(index).getId());
                index++;
            }
        }
        int r = 0;
        for (PathEdge pe : pathEdges
        ) {
            r++;
            path = path + "-[r" + r + ":" + captureName(pe.getLabel().toLowerCase()) + "]-()";
        }
        //where
        String where = " where ";
        for (int i = 1; i < r + 1; i++) {

            where = where + " datetime(r" + i + ".startvalidtime)< datetime('" + et + "') AND (datetime(r" + i + ".endvalidtime)>=datetime('" + st + "') OR datetime(r" + i + ".endvalidtime) IS NULL)";
            if (i != r) {
                where = where + " and ";
            }
        }

        Result result = session.run("match " + path + where + " return nodes(p) as nodes,relationships(p) as relationships  limit " + nb);
        System.out.println("match " + path + where + " return nodes(p) as nodes,relationships(p) as relations  limit " + nb);
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            int nodeSize = record.get("nodes").size();
            int relationshipSize = record.get("relationships").size();

            // node
            for (int i = 0; i < nodeSize; i++) {
                Node source = new Node();
                Combo scombo = new Combo();
                org.neo4j.driver.types.Node sourceNode = record.get("nodes").get(i).asNode();
                HashMap<Object, Object> attribute = new HashMap<>();
                attribute.putAll(sourceNode.asMap());
                source.setAttributes(attribute);
                source.setId(sourceNode.id() + "");
                source.setInstanceid(attribute.get("instanceid") + "");
                source.setStartvalidtime(attribute.get("startvalidtime").toString());
                source.setEndvalidtime(attribute.get("endvalidtime") + "");
                if (attribute.get("instanceid") == null) {
                    source.setLabel(captureName(sourceNode.labels().iterator().next().toLowerCase()) + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    //  source.setType("diamond");
                } else {
                    source.setComboId(sourceNode.labels().iterator().next() + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    source.setLabel(source.getInstanceid());
                    scombo.setId(source.getComboId());
                    scombo.setLabel(source.getComboId());
                    HashMap<Object, Object> styleSCombo = new HashMap<>();
                    String colorSCombo = "#006699";
                    styleSCombo.put("fill", colorSCombo);
                    styleSCombo.put("stroke", colorSCombo);
                    scombo.setStyle(styleSCombo);
                    combos.add(scombo);
                }
                HashMap<Object, Object> styleSource = new HashMap<>();
                String colorSource;
                switch (sourceNode.labels().iterator().next().toLowerCase()) {
                    case "user":
                        colorSource = "#FF6666";
                        break;
                    case "item":
                        colorSource = "#FFFFCC";
                        break;
                    default:
                        colorSource = "#FFFF00";
                }
                styleSource.put("fill", colorSource);
                styleSource.put("stroke", colorSource);
                source.setStyle(styleSource);
                nodes.add(source);

            }

            //edge
            for (int i = 0; i < relationshipSize; i++) {
                Edge edge = new Edge();
                org.neo4j.driver.types.Relationship relationship = record.get("relationships").get(i).asRelationship();
                edge.setId(relationship.id() + "");
                edge.setSource(relationship.startNodeId() + "");
                edge.setTarget(relationship.endNodeId() + "");
                edge.setTypeEdge(relationship.type());
                HashMap<Object, Object> rattributes = new HashMap<>();
                rattributes.putAll(relationship.asMap());
                edge.setAttributes(rattributes);

                HashMap<Object, Object> styleEdge = new HashMap<>();
                String colorEdge;
                switch (relationship.type().toLowerCase()) {
                    case "addtocart":
                        colorEdge = "#FFEEE4";
                        break;
                    case "belongto":
                        colorEdge = "#CFCFCF";
                        break;
                    case "subCategory":
                        colorEdge = "#AAABD3";
                        break;
                    case "view":
                        colorEdge = "#EEB4B4";
                        break;
                    default:
                        colorEdge = "#CDBE70";
                }
                styleEdge.put("stroke", colorEdge);
                edge.setStyle(styleEdge);
                edges.add(edge);
            }


        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);


        HashMap<Object, Object> res = new HashMap<>();


        res.put("nodes", nodeNoDupli);
        res.put("edges", edgeNoDupli);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


    //-----------------------------sub graph----------------------------------------------
    public static HashMap<Object, Object> searchSubgraph(PathData pathData) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();

        // Process received data
        ArrayList<PathNode> pathNodes = new ArrayList<>();
        ArrayList<PathEdge> pathEdges = new ArrayList<>();
        pathEdges = pathData.getEdges();
        pathNodes = pathData.getNodes();
        String path = "p=";
        boolean find = false;
        int index = 0;
// requete
        while ((!find) & index < pathNodes.size()) {
            System.out.println(pathEdges.get(0).getSource());
            if (pathNodes.get(index).getId().equals(pathEdges.get(0).getSource())) {
                System.out.println("trouve");
                path = path + "(" + pathNodes.get(index).getLabelForQuery() + " :" + captureName(pathNodes.get(index).getLabelForQuery().replaceAll("\\d+", "").toLowerCase()) + ")";
                find = true;
            } else {
                System.out.println(pathNodes.get(index).getId());
                index++;
            }
        }
        int r = 0;
        for (PathEdge pe : pathEdges
        ) {
            r++;
            path = path + "-[r" + r + ":" + captureName(pe.getLabel().toLowerCase()) + "]-(" + pathNodes.get(r).getLabelForQuery() + ":" + captureName(pathNodes.get(r).getLabelForQuery().replaceAll("\\d+", "")) + ")";

        }

        Result result = session.run("match " + path + " return nodes(p) as nodes,relationships(p) as relationships  limit " + nb);
        System.out.println("match " + path + " return nodes(p) as nodes,relationships(p) as relations  limit " + nb);
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            int nodeSize = record.get("nodes").size();
            int relationshipSize = record.get("relationships").size();

            // node
            for (int i = 0; i < nodeSize; i++) {
                Node source = new Node();
                Combo scombo = new Combo();
                org.neo4j.driver.types.Node sourceNode = record.get("nodes").get(i).asNode();
                HashMap<Object, Object> attribute = new HashMap<>();
                attribute.putAll(sourceNode.asMap());
                source.setAttributes(attribute);
                source.setId(sourceNode.id() + "");
                source.setInstanceid(attribute.get("instanceid") + "");
                source.setStartvalidtime(attribute.get("startvalidtime").toString());
                source.setEndvalidtime(attribute.get("endvalidtime") + "");
                if (attribute.get("instanceid") == null) {
                    source.setLabel(captureName(sourceNode.labels().iterator().next().toLowerCase()) + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    //  source.setType("diamond");
                } else {
                    source.setComboId(sourceNode.labels().iterator().next() + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    source.setLabel(source.getInstanceid());
                    scombo.setId(source.getComboId());
                    scombo.setLabel(source.getComboId());
                    HashMap<Object, Object> styleSCombo = new HashMap<>();
                    String colorSCombo = "#006699";
                    styleSCombo.put("fill", colorSCombo);
                    styleSCombo.put("stroke", colorSCombo);
                    scombo.setStyle(styleSCombo);
                    combos.add(scombo);
                }
                HashMap<Object, Object> styleSource = new HashMap<>();
                String colorSource;
                switch (sourceNode.labels().iterator().next().toLowerCase()) {
                    case "user":
                        colorSource = "#FF6666";
                        break;
                    case "item":
                        colorSource = "#FFFFCC";
                        break;
                    default:
                        colorSource = "#FFFF00";
                }
                styleSource.put("fill", colorSource);
                styleSource.put("stroke", colorSource);
                source.setStyle(styleSource);
                nodes.add(source);

            }

            //edge
            for (int i = 0; i < relationshipSize; i++) {
                Edge edge = new Edge();
                org.neo4j.driver.types.Relationship relationship = record.get("relationships").get(i).asRelationship();
                edge.setId(relationship.id() + "");
                edge.setSource(relationship.startNodeId() + "");
                edge.setTarget(relationship.endNodeId() + "");
                edge.setTypeEdge(relationship.type());
                HashMap<Object, Object> rattributes = new HashMap<>();
                rattributes.putAll(relationship.asMap());
                edge.setAttributes(rattributes);

                HashMap<Object, Object> styleEdge = new HashMap<>();
                String colorEdge;
                switch (relationship.type().toLowerCase()) {
                    case "addtocart":
                        colorEdge = "#FFEEE4";
                        break;
                    case "belongto":
                        colorEdge = "#CFCFCF";
                        break;
                    case "subCategory":
                        colorEdge = "#AAABD3";
                        break;
                    case "view":
                        colorEdge = "#EEB4B4";
                        break;
                    default:
                        colorEdge = "#CDBE70";
                }
                styleEdge.put("stroke", colorEdge);
                edge.setStyle(styleEdge);
                edges.add(edge);
            }


        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);


        HashMap<Object, Object> res = new HashMap<>();


        res.put("nodes", nodeNoDupli);
        res.put("edges", edgeNoDupli);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    //-----------------------------filter sub graph----------------------------------------------
    public static HashMap<Object, Object> filterSubgraph(FiltreData filtreData) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        System.out.println(filtreData);
        PathData pathData = filtreData.getPathData();
        ArrayList<Condition> conditions = filtreData.getConditions();

        // Process received data
        ArrayList<PathNode> pathNodes = new ArrayList<>();
        ArrayList<PathEdge> pathEdges = new ArrayList<>();
        pathEdges = pathData.getEdges();
        pathNodes = pathData.getNodes();
        String path = "p=";
        boolean find = false;
        int index = 0;
        // requete
        while ((!find) & index < pathNodes.size()) {
            System.out.println(pathEdges.get(0).getSource());
            if (pathNodes.get(index).getId().equals(pathEdges.get(0).getSource())) {
                System.out.println("trouve");
                path = path + "(" + pathNodes.get(index).getLabelForQuery() + " :" + captureName(pathNodes.get(index).getLabelForQuery().replaceAll("\\d+", "").toLowerCase()) + ")";
                find = true;
            } else {
                System.out.println(pathNodes.get(index).getId());
                index++;
            }
        }
        int r = 0;
        for (PathEdge pe : pathEdges
        ) {
            r++;
            path = path + "-[r" + r + ":" + captureName(pe.getLabel().toLowerCase()) + "]-(" + pathNodes.get(r).getLabelForQuery() +
                    ":" + captureName(pathNodes.get(r).getLabelForQuery().replaceAll("\\d+", "")) + ")";
        }

        //where
        String where = " where ";
        for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i).getRelation() == null) {
                conditions.get(i).setRelation("");
            }
            ;
            where = where + conditions.get(i).getLabelForQuery() + "." +
                    conditions.get(i).getAttribute() + conditions.get(i).getOperation() + "'" + conditions.get(i).getValue() + "'  " + conditions.get(i).getRelation() + " ";
//
//            if (i != conditions.size() - 1) {
//                where = where + " and ";
//            }
        }

        Result result = session.run("match " + path + where + " return nodes(p) as nodes,relationships(p) as relationships  limit " + nb);
        System.out.println("match " + path + where + " return nodes(p) as nodes,relationships(p) as relations  limit " + nb);
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Combo> combos = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            int nodeSize = record.get("nodes").size();
            int relationshipSize = record.get("relationships").size();

            // node
            for (int i = 0; i < nodeSize; i++) {
                Node source = new Node();
                Combo scombo = new Combo();
                org.neo4j.driver.types.Node sourceNode = record.get("nodes").get(i).asNode();
                HashMap<Object, Object> attribute = new HashMap<>();
                attribute.putAll(sourceNode.asMap());
                source.setAttributes(attribute);
                source.setId(sourceNode.id() + "");
                source.setInstanceid(attribute.get("instanceid") + "");
                source.setStartvalidtime(attribute.get("startvalidtime").toString());
                source.setEndvalidtime(attribute.get("endvalidtime") + "");
                if (attribute.get("instanceid") == null) {
                    source.setLabel(captureName(sourceNode.labels().iterator().next().toLowerCase()) + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    //  source.setType("diamond");
                } else {
                    source.setComboId(sourceNode.labels().iterator().next() + attribute.get(sourceNode.labels().iterator().next().toLowerCase() + "id").toString());
                    source.setLabel(source.getInstanceid());
                    scombo.setId(source.getComboId());
                    scombo.setLabel(source.getComboId());
                    HashMap<Object, Object> styleSCombo = new HashMap<>();
                    String colorSCombo = "#006699";
                    styleSCombo.put("fill", colorSCombo);
                    styleSCombo.put("stroke", colorSCombo);
                    scombo.setStyle(styleSCombo);
                    combos.add(scombo);
                }
                HashMap<Object, Object> styleSource = new HashMap<>();
                String colorSource;
                switch (sourceNode.labels().iterator().next().toLowerCase()) {
                    case "user":
                        colorSource = "#FF6666";
                        break;
                    case "item":
                        colorSource = "#FFFFCC";
                        break;
                    default:
                        colorSource = "#FFFF00";
                }
                styleSource.put("fill", colorSource);
                styleSource.put("stroke", colorSource);
                source.setStyle(styleSource);
                nodes.add(source);

            }

            //edge
            for (int i = 0; i < relationshipSize; i++) {
                Edge edge = new Edge();
                org.neo4j.driver.types.Relationship relationship = record.get("relationships").get(i).asRelationship();
                edge.setId(relationship.id() + "");
                edge.setSource(relationship.startNodeId() + "");
                edge.setTarget(relationship.endNodeId() + "");
                edge.setTypeEdge(relationship.type());
                HashMap<Object, Object> rattributes = new HashMap<>();
                rattributes.putAll(relationship.asMap());
                edge.setAttributes(rattributes);

                HashMap<Object, Object> styleEdge = new HashMap<>();
                String colorEdge;
                switch (relationship.type().toLowerCase()) {
                    case "addtocart":
                        colorEdge = "#FFEEE4";
                        break;
                    case "belongto":
                        colorEdge = "#CFCFCF";
                        break;
                    case "subCategory":
                        colorEdge = "#AAABD3";
                        break;
                    case "view":
                        colorEdge = "#EEB4B4";
                        break;
                    default:
                        colorEdge = "#CDBE70";
                }
                styleEdge.put("stroke", colorEdge);
                edge.setStyle(styleEdge);
                edges.add(edge);
            }


        }

        LinkedHashSet<Node> nodeset = new LinkedHashSet<Node>(nodes);
        ArrayList<Node> nodeNoDupli = new ArrayList<Node>(nodeset);
        LinkedHashSet<Edge> edgeset = new LinkedHashSet<Edge>(edges);
        ArrayList<Edge> edgeNoDupli = new ArrayList<Edge>(edgeset);
        LinkedHashSet<Combo> set = new LinkedHashSet<Combo>(combos);
        ArrayList<Combo> listWithoutDuplicateElements = new ArrayList<Combo>(set);


        HashMap<Object, Object> res = new HashMap<>();


        res.put("nodes", nodeNoDupli);
        res.put("edges", edgeNoDupli);
        res.put("combos", listWithoutDuplicateElements);
        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }


    //-----------------------------schema-------------------------------------------------
    public static HashMap<Object, Object> getSchema() {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        // install apoc in neo4j
        Result result = session.run("call db.schema.visualization() ");
        ArrayList<String> nodes = new ArrayList<>();
        ArrayList<String> edges = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        while (result.hasNext()) {
            Record record = result.next();
            int nodeSize = record.get("nodes").size();
            int edgeSize = record.get("relationships").size();
            for (int i = 0; i < nodeSize; i++) {
                org.neo4j.driver.types.Node node = record.get("nodes").get(i).asNode();
                System.out.println(node.labels().iterator().next());

                nodes.add(node.labels().iterator().next());
            }
            for (int i = 0; i < edgeSize; i++) {
                org.neo4j.driver.types.Relationship relationship = record.get("relationships").get(i).asRelationship();
                edges.add(relationship.type());
            }

        }

        HashMap<Object, Object> res = new HashMap<>();
        labels.addAll(nodes);
        labels.addAll(edges);
        res.put("nodes", nodes);
        res.put("edges", edges);
        res.put("labels", labels);
        return res;
    }

    //-----------------------------history-------------------------------------------------

    public static HashMap<Object, Object> searchHistoryNode(String id, String label) {
        Driver driver = ConnexionNeo4J.connectDB();
        Session session = driver.session();
        HashMap<Object, Object> res = new HashMap<>();
        Boolean evolution = false;
        //obtain all of instances of a node
        Result result = session.run("MATCH (n:" + captureName(label.toLowerCase()) + ") WHERE n." + label.toLowerCase() + "id='" + id + "' return properties(n) as attributes,id(n) as id"); //order by n."+label.toLowerCase()+"id
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<NodeHistory> nodeHistories = new ArrayList<>();
        ArrayList<Change> changes = new ArrayList<>();

        while (result.hasNext()) {
            Record record = result.next();
            Node node = new Node();
            node.setId("" + record.get("id").asLong());
            node.setInstanceid(record.get("attributes").get("instanceid").asString());
            if ((record.get("attributes").get("instanceid").asString() == "null")) {
                evolution = false;
                node.setLabel(captureName(label.toLowerCase()) + record.get("attributes").get(label.toLowerCase() + "id").asString()); //node.getId()
                //  node.setType("diamond");
            } else {
                evolution = true;
                node.setLabel(node.getInstanceid());
            }
            node.setStartvalidtime(record.get("attributes").get("startvalidtime").asString());
            node.setEndvalidtime(record.get("attributes").get("endvalidtime").asString());
            HashMap<Object, Object> attributes = new HashMap<>();
            attributes.putAll(record.get("attributes").asMap());

            node.setAttributes(attributes);
            nodes.add(node);
        }
        NodeHistory n1 = new NodeHistory();
        // sort the nodes by time
        // données deja bien tiées
        String fill1;
        switch (label.toLowerCase()) {
            case "user":
                fill1 = "#FF6666";
                break;
            case "item":
                fill1 = "#FFFFCC";
                break;
            default:
                fill1 = "#FFFF00";
        }

        n1.setId(nodes.get(0).getId());
        n1.setLabel(nodes.get(0).getLabel());
        n1.setSize("80");
        n1.setX("0");
        n1.setY("5");
        HashMap<Object, Object> style1 = new HashMap<>();
        style1.put("fill", fill1);
        // style.put("stroke", stroke);
        n1.setStyle(style1);
        nodeHistories.add(n1);
        Change c1 =new Change();
        //c1.setAppear(null);
        //c1.setDisappear(null);
        HashMap<Object,Object> validtime1 =new HashMap<>();
        validtime1.put("startvalidtime",nodes.get(0).getStartvalidtime());
        validtime1.put("endvalidtime",nodes.get(0).getEndvalidtime());
        c1.setValidtime(validtime1);
        //c1.setValuechange(null);
        changes.add(c1);
        //compare the nodes and generate nodeHistory
        for (int i = 0; i < nodes.size() - 1; i++) {
            int nbNew = 0;
            int nbDisappear = 0;
            int nbValue = 0;
            String typeChange = "";
            String color1 = "";
            String color2 = "";
            String fill = "#FFFFFF";


            //extract all of the key and value
            //i
            Set<Object> keySetP = nodes.get(i).getAttributes().keySet();
            ArrayList<String> keysP = new ArrayList<>(Arrays.asList(keySetP.toArray(new String[0])));
            ArrayList<String> keysP1 = new ArrayList<>();
            keysP1.addAll(keysP);

            //i+1
            Set<Object> keySetN = nodes.get(i + 1).getAttributes().keySet();
            ArrayList<String> keysN = new ArrayList<>(Arrays.asList(keySetN.toArray(new String[0])));
            ArrayList<String> keysN1 = new ArrayList<>();
            keysN1.addAll(keysN);

            System.out.println(keysP);
            System.out.println(keysN);


            // new
            // remove all elements of keysP list
            System.out.println("new");
            keysN1.removeAll(keysP1);
            System.out.println(keysN1);
            nbNew = keysN1.size();
            //disappear
            // remove all elements of keysN list
            System.out.println("disappear");
            keysP1.removeAll(keysN);
            System.out.println(keysP1);
            nbDisappear = keysP1.size();
            //value
            HashMap<Object,Object> valuechange=new HashMap<>();
            for (Map.Entry<Object, Object> entry1 : nodes.get(i).getAttributes().entrySet()) {
                Object m1value = entry1.getValue() == null ? "" : entry1.getValue();
                Object m2value = nodes.get(i + 1).getAttributes().get(entry1.getKey()) == null ? "" : nodes.get(i + 1).getAttributes().get(entry1.getKey());

                if (!m1value.equals(m2value)) {//若两个map中相同key对应的value不相等
                    if (entry1.getKey().equals("instanceid") == false && entry1.getKey().equals("endvalidtime") == false && entry1.getKey().equals("startvalidtime") == false) {
                        System.out.println(i + "+" + (i + 1));
                        System.out.println(entry1.getKey() + ":" + m1value.toString() + "+" + m2value.toString());
                        nbValue++;
                        // valuechange
                        valuechange.put("before:"+entry1.getKey() ,m1value);
                        valuechange.put("actuel:"+entry1.getKey() ,m2value);
                    }
                }
            }
            // instantier change pour cette instance
            Change change =new Change();
            //appear
            change.setAppear(keysN1);
            //disappear
            change.setDisappear(keysP1);
            //validtime
            HashMap<Object,Object> validtime =new HashMap<>();
            validtime.put("startvalidtime",nodes.get(i+1).getStartvalidtime());
            validtime.put("endvalidtime",nodes.get(i+1).getEndvalidtime());
            change.setValidtime(validtime);
            //valuechange
            change.setValuechange(valuechange);
            // add into changes by order
            changes.add(change);

            //比完了。总结
            //如果3个nb都是大于0  type = pie
            if (nbDisappear > 0 && nbValue > 0 && nbNew > 0) {
                typeChange = "pie-node";
            }
            //如果有2个  type= demi   根据nb的类型  设置color1 2
            if (nbNew > 0 && nbDisappear > 0 && nbValue == 0) {
                typeChange = "demi-node";
                color1 = "#7FFF00";
                color2 = "#FF0000";

            } else if (nbNew > 0 && nbDisappear == 0 && nbValue > 0) {
                typeChange = "demi-node";
                color1 = "#7FFF00";
                color2 = "#FFB90F";

            } else if (nbNew == 0 && nbDisappear > 0 && nbValue > 0) {
                typeChange = "demi-node";
                color1 = "#FFB90F";
                color2 = "#FF0000";
            }

            //如果有1个或者没有 type为空  根据nb 设置style中fill颜色
            if (nbNew > 0 && nbDisappear == 0 && nbValue == 0) {
                fill = "#7FFF00";

            } else if (nbNew == 0 && nbDisappear == 0 && nbValue > 0) {
                fill = "#FFB90F";

            } else if (nbNew == 0 && nbDisappear > 0 && nbValue == 0) {
                fill = "#FF0000";
            }
            NodeHistory nodeHistory = new NodeHistory();
            nodeHistory.setId(nodes.get(i + 1).getId());
            nodeHistory.setLabel(nodes.get(i + 1).getLabel());
            nodeHistory.setSize("80");
            nodeHistory.setColor1(color1);
            nodeHistory.setColor2(color2);
            nodeHistory.setDegree("360");
            nodeHistory.setType(typeChange);
            HashMap<Object, Object> style = new HashMap<>();
            style.put("fill", fill);
            // style.put("stroke", stroke);
            System.out.println("style" + style);
            nodeHistory.setStyle(style);
            nodeHistory.setX((90 + 90 * i) + "");
            nodeHistory.setY("5");
            nodeHistories.add(nodeHistory);
        }

        HashMap<Object,Object> resNodes=new HashMap<>();
        resNodes.put("nodes", nodeHistories);

        res.put("nodes", resNodes);
        res.put("changes", changes);

        System.out.println("ok");
        session.close();
        driver.close();
        return res;
    }

    public static void main(String[] args) throws Exception {
        // searchNodeComboWithTime("user", "2015-07-09 06:00:00", "2016-07-10 08:00:00");
        searchHistoryNode("1", "item");
        System.out.println("----------------End--------------");

    }


}
