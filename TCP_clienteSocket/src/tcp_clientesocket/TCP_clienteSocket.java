/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp_clientesocket;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Lucas Alves da Costa
 */
public class TCP_clienteSocket {

 public static void main(String[] args) throws IOException {

    Scanner teclado = new Scanner(System.in);
    System.out.println("Host address:");
    String host = teclado.nextLine();
    System.out.println("Host port: ");
    int port= teclado.nextInt();
    
        Socket cliente = new Socket(host, port);
        System.out.println("Connection Established: "); 
            
        PrintStream saida = new PrintStream(cliente.getOutputStream()); 
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
            
    }
}
