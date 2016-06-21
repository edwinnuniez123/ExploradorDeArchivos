package DistributedFileSystem;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Andres
 */
public class ServerNodeImpl extends UnicastRemoteObject implements ServerNode {

    private static HashMap<String, Record> recordmap = new HashMap();
    private static FileDataServer dataA;
    private static FileDataServer backupdataA;
    private static FileDataServer dataB;
    private static FileDataServer backupdataB;
    private static DefaultTreeModel myfilestructure;

    @Override
    public void messageS(String mensaje) throws RemoteException {

    }

    private TreePath find(DefaultMutableTreeNode root, DefaultMutableTreeNode search) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (((TreeNode) node.getUserObject()).getNodename().equals(((TreeNode) search.getUserObject()).getNodename())) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }

    @Override
    public boolean createFolder(String nombre, DefaultMutableTreeNode padre) throws RemoteException {

        TreePath camino = find((DefaultMutableTreeNode) myfilestructure.getRoot(), padre);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) camino.getLastPathComponent());
        int servidor = 1;
        selectedNode.add(new DefaultMutableTreeNode(new TreeNode(nombre, servidor, 'd')));
        System.out.println(nombre);
        new File("DFS.bin").delete();
        File archivo = null;
        try {
            archivo = new File("DFS.bin");
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(myfilestructure);
            objeto.flush();
            objeto.close();
            salida.close();

        } catch (Exception ex3) {

        }
        //codigo para guardar en el data node
        return true;
    }

    public ServerNodeImpl() throws RemoteException {
        super();
        myfilestructure = new DefaultTreeModel(new DefaultMutableTreeNode(new TreeNode("root", -1, 'd')));
        File archivo = null;
        try {
            archivo = new File("DFS.bin");
            if (!archivo.exists()) {
                FileOutputStream salida = new FileOutputStream(archivo);
                ObjectOutputStream objeto = new ObjectOutputStream(salida);
                objeto.writeObject(myfilestructure);
                objeto.flush();
                objeto.close();
                salida.close();
            } else {
                FileInputStream entrada = new FileInputStream(archivo);
                ObjectInputStream objeto = new ObjectInputStream(entrada);
                try {
                    myfilestructure = (DefaultTreeModel) objeto.readObject();
                } catch (ClassNotFoundException e) {
                    //encontro el final del binario
                } catch (EOFException e) {
                } finally {
                    objeto.close();
                    entrada.close();
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean createFile(String nombre, String Text, DefaultMutableTreeNode padre) throws RemoteException {
        
        TreePath camino = find((DefaultMutableTreeNode) myfilestructure.getRoot(), padre);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) camino.getLastPathComponent());
        int servidor = 1;
        selectedNode.add(new DefaultMutableTreeNode(new TreeNode(nombre, servidor, 'a')));
        new File("DFS.bin").delete();
        File archivo = null;
        try {
            archivo = new File("DFS.bin");
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(myfilestructure);
            objeto.flush();
            objeto.close();
            salida.close();

        } catch (Exception ex3) {

        }
        

        return true;
    }

    @Override
    public boolean deleteFile(String nombre, DefaultMutableTreeNode nodo) throws RemoteException {
        TreePath camino = find((DefaultMutableTreeNode) myfilestructure.getRoot(), nodo);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) camino.getLastPathComponent());
        ((DefaultMutableTreeNode) selectedNode.getParent()).remove(selectedNode);
        new File("DFS.bin").delete();
        File archivo = null;
        try {
            archivo = new File("DFS.bin");
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(myfilestructure);
            objeto.flush();
            objeto.close();
            salida.close();

        } catch (Exception ex3) {

        }
        
        return true;
    }

    @Override
    public boolean deleteFolder(String nombre, DefaultMutableTreeNode nodo) throws RemoteException {
        TreePath camino = find((DefaultMutableTreeNode) myfilestructure.getRoot(), nodo);
        DefaultMutableTreeNode selectedNode = ((DefaultMutableTreeNode) camino.getLastPathComponent());
        ((DefaultMutableTreeNode) selectedNode.getParent()).remove(selectedNode);
        new File("DFS.bin").delete();
        File archivo = null;
        try {
            archivo = new File("DFS.bin");
            FileOutputStream salida = new FileOutputStream(archivo);
            ObjectOutputStream objeto = new ObjectOutputStream(salida);
            objeto.writeObject(myfilestructure);
            objeto.flush();
            objeto.close();
            salida.close();

        } catch (Exception ex3) {

        }
        //flush data server

        return true;
    }

    @Override
    public String getFile(String nombre) throws RemoteException {
        return "";
    }

    @Override
    public DefaultTreeModel getFileSystem() throws RemoteException {
        return myfilestructure;
    }

    private static void start() {

        String host = "192.168.1.8";//localhost IP
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServerNode", new ServerNodeImpl());
            System.out.println("Server started successfully.");

            Registry reg2 = LocateRegistry.getRegistry(host, 1100); // agregar host
            dataA = (FileDataServer) reg2.lookup("DataServer1");
            System.out.println("File Data Server A connected successfully.");

            Registry reg3 = LocateRegistry.getRegistry(host, 1101);
            backupdataA = (FileDataServer) reg3.lookup("ReplicaDataServer1");
            System.out.println("Backup File Data Server A connected successfully.");

            Registry reg4 = LocateRegistry.getRegistry(host, 1102);
            dataB = (FileDataServer) reg4.lookup("DataServer2");
            System.out.println("File Data Server B connected successfully.");

            Registry reg5 = LocateRegistry.getRegistry(host, 1103);
            backupdataB = (FileDataServer) reg5.lookup("ReplicaDataServer2");
            System.out.println("Backup File Data Server B connected successfully.");

            dataA.msg("ping " + recordmap.get("DataServer1").getId());
            backupdataA.msg("ping " + recordmap.get("ReplicaDataServer1").getId());
            dataB.msg("ping " + recordmap.get("DataServer2").getId());
            backupdataB.msg("ping " + recordmap.get("ReplicaDataServer2").getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        recordmap.put("ServerNode", new Record(1));
        recordmap.put("DataServer1", new Record(2));
        recordmap.put("ReplicaDataServer1", new Record(3));
        recordmap.put("DataServer2", new Record(4));
        recordmap.put("ReplicaDataServer2", new Record(5));

        start();
    }

}
