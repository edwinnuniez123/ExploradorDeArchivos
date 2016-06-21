package DistributedFileSystem;

import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 *
 * @author Sndres
 */
public class BackupFileDataServerB extends UnicastRemoteObject implements FileDataServer {

    public BackupFileDataServerB(File directorio) throws RemoteException {
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
            directorio = new File("./Data/BackupB");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1103);
            reg.rebind("ReplicaDataServer2", new FileDataServerA(directorio));
            System.out.println("Backup File Data Server B connected successfully.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}