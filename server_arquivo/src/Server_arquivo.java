import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Lucas Alves
 */
public class Server_arquivo {
   
  
     public static void reciveMsg(Socket Cliente) throws IOException{
         
           File entrada = new File ("arquivoLadoServidor.txt");// arquivo criado
       
           
       
         String msg = " ";
         InputStream Recive;
         Recive = Cliente.getInputStream();
         int recebido=0;
         char data = 0;
  
  // Recebo a msg do cliente em forma de bytes e transformo em 'char'
     while ((data = (char)Recive.read()) != 4) {
         recebido=data;
        msg += (char)data;//gravo nessa str
      }
            try (FileWriter gravar1 = new FileWriter(entrada)) {
            gravar1.write(msg);
            gravar1.flush();// gravo a msg recebida em um arquivo de texto
        }
         Cliente.close();// fecha a conexão
             
          // Msg q informa a str recebida
          System.out.printf("Cliente mandou: (%s)",msg);
                 
      
     }
    
     
	 public static void main (String [] args ) throws IOException {
             
    // cria o nosso socket na porta 5555
  
             ServerSocket servsock = new ServerSocket(5555);
             System.out.println("SERVER On-Line!.  Ouvindo a porta(5555)\nAgandando conexão!\n>");
             
             while(true){
                  Socket Server = servsock.accept();
                System.out.println(" Conected:");
              reciveMsg(Server);// assim que alguém se conecta o server ja esta pronto a receber uma msg
             }
              
    
    }
 
}
