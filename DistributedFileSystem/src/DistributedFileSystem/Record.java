
package DistributedFileSystem;

/**
 *
 * @author Andres
 */
public class Record {
    private int id;
    public Record(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
}

