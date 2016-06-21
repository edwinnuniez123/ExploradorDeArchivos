package DistributedFileSystem;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Andres
 */
public interface FileDataServer extends Remote {
    public void msg(String mensaje)throws RemoteException;
}
