package DistributedFileSystem;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */
public class FileDataServerA extends UnicastRemoteObject implements FileDataServer {
    private static FileDataServer replicadataserver1;
    public FileDataServerA(File directorio) throws RemoteException {
        super();
        this.directorio = directorio;
        
    }
     
    @Override
    public void msg(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }
    private static File directorio;
    private static Registry reg;
    
    public static void main(String args[]){

        try {
            directorio = new File("./Data/A");
            if (!directorio.exists()) {
                try {
                    if (directorio.mkdirs()) {
                    } else {}
                } catch (Exception e) {}
            } else {}
            reg = LocateRegistry.createRegistry(1100);
            reg.rebind("DataServer1", new FileDataServerA(directorio));
            System.out.println("Data Server A started.");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            Registry reg3 = LocateRegistry.getRegistry("192.168.1.8",1101);
            replicadataserver1 = (FileDataServer) reg3.lookup("ReplicaDataServer1");
            System.out.println("Connected to our backup server - Node A.");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    

    
}
