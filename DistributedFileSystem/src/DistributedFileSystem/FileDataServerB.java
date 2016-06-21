package DistributedFileSystem;

import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 *
 * @author Andres
 */
public class FileDataServerB extends UnicastRemoteObject implements FileDataServer {
    private static FileDataServer replicadataserver2;
    public FileDataServerB(File directorio) throws RemoteException {
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
            directorio = new File("./Data/B");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1102);
            reg.rebind("DataServer2", new FileDataServerA(directorio));
            System.out.println("File Data Server 2 started.");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            Registry reg5 = LocateRegistry.getRegistry("192.168.1.8",1103);
            replicadataserver2 = (FileDataServer) reg5.lookup("ReplicaDataServer2");
            System.out.println("Connected to our backup copy - Node B.");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    
}