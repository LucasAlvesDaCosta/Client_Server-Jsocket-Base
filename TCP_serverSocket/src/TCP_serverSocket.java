import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas Alves
 */
public class TCP_serverSocket {
   static Socket cliente;
static ServerSocket servidor;
public static void main(String[] args) throws IOException {

    Scanner teclado = new Scanner(System.in);
    System.out.println("Host port: ");
    int port= teclado.nextInt();
    
    servidor = new ServerSocket(port);
    System.out.println("Server listening on " + port);      
    cliente = servidor.accept();
    System.out.println("Client " + cliente.getInetAddress().getHostAddress() + " connected on " + port); 
Thread t = new Thread(() -> {
    try (Scanner entrada = new Scanner(cliente.getInputStream())) {
        while(entrada.hasNextLine()){
            String text = entrada.nextLine();
            if(text.equals("[q]")) {
                break;
            }
            System.out.println(text);
        }
    } catch(Exception e) {
        
    }
    try {
        cliente.close();
    } catch (IOException ex) {
        Logger.getLogger(TCP_serverSocket.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
        servidor.close();
    } catch (IOException ex) {
        Logger.getLogger(TCP_serverSocket.class.getName()).log(Level.SEVERE, null, ex);
    }
    });
t.start();
Thread t1 = new Thread(() -> {
    try {
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        while (teclado.hasNextLine()) {
            String palavra = teclado.nextLine();
            if(palavra.equals("[q]")) {
//saida.println(“Client disconnected”);
saida.println(palavra);
break;
            } else {
                saida.println(teclado.nextLine());
            }
        }
        servidor.close();
        cliente.close();
    } catch(Exception e) {
        
    }
    });
t1.start();
// servidor.close();
}
}
