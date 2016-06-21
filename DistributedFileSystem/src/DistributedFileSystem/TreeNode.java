package DistributedFileSystem;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class TreeNode implements Serializable{
    private String nodename;
    private int dataServer;
    private char type;

    public TreeNode(String nodename, int dataServer, char type) {
        this.nodename = nodename;
        this.dataServer = dataServer;
        this.type = type;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public int getDataServer() {
        return dataServer;
    }

    public void setDataServer(int dataServer) {
        this.dataServer = dataServer;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
    
    @Override
    public String toString(){
        if(nodename.equals("root")){
            return nodename;
        }else{
            String temporal[] = nodename.split("/");
            return temporal[temporal.length-1];
        }
    }
    
}
