package DAO;

public class outils {

    public static String captureName(String name) {
        //char[] cs=name.toCharArray();
        //cs[0]-=32;
        //return String.valueOf(cs);

        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }

}
