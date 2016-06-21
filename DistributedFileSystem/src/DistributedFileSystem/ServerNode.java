
package DistributedFileSystem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Andres
 */
public interface ServerNode extends Remote{
    public void messageS(String mensaje)throws RemoteException;
    public boolean createFolder(String nombre, DefaultMutableTreeNode padre)throws RemoteException;
    public boolean createFile(String nombre, String Text, DefaultMutableTreeNode padre)throws RemoteException;
    public boolean deleteFile(String nombre, DefaultMutableTreeNode nodo) throws RemoteException;
    public boolean deleteFolder(String nombre, DefaultMutableTreeNode nodo) throws RemoteException;
    public String getFile(String nombre)throws RemoteException; 
    public DefaultTreeModel getFileSystem()throws RemoteException; 
    
}
