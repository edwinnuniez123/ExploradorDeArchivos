package DistributedFileSystem;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Andres
 */
public class BackupFileDataServerA extends UnicastRemoteObject implements FileDataServer {

    public BackupFileDataServerA(File directorio) throws RemoteException {
        super();
        this.directorio = directorio;
        
    }
     
    @Override
    public void msg(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }
    private static File directorio;
    private static Registry reg;
    
    public static void main(String args[]) {
        try {
            directorio = new File("./Data/BackupA");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1101);
            reg.rebind("ReplicaDataServer1", new FileDataServerA(directorio));
            System.out.println("Backup File Data Server B connected successfully.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}