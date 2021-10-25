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
public class TCP_socket_server {
   
    
     public static void EnviarMsg(Socket Cliente) throws IOException  {
    // Função responsável por enviar mensagens para o cliente conectado.
         
        Scanner str;
            System.out.printf("Digite a msg para o cliente: ");
            str = new Scanner(System.in);
            String msg;
         msg =str.nextLine();// Escrevo uma mensagem a partir do teclado e gravo na variavel 'msg'
       
        OutputStream Send;// metodo de saida para enviar a msg em forma de stream
          Send = Cliente.getOutputStream();
           Send.write(msg.getBytes());// envio a msg em gorma de bytes, sendo necessario que o cliente converta em 'char'
          Send.write(4);
        Send.flush();
           
       // infoma que a msg foi enviada
         System.out.println(" Mensagem enviada!");
         reciveMsg(Cliente);// chamo a função para receber a resposta do cliente
        
  }
    
     public static void reciveMsg(Socket Cliente) throws IOException{
         String msg = " ";
         InputStream Recive;
         Recive = Cliente.getInputStream();
         int recebido=0;
         char data = 0;
  
  // Recebo a mensagem de resposta do cliente em forma de strem
  // Essa msg chega em forma de byte, por isso é necessário converter em 'char'
     while ((data = (char)Recive.read()) != 4) {
          //System.out.print((char)data);
         recebido=data;
        msg += (char)data;
      }
     
         Cliente.close();// fecha a conexão
             
          // Msg 
          System.out.printf("Cliente mandou: (%s)",msg);
                 
      
     }
    
     
	 public static void main (String [] args ) throws IOException {
             
    // cria o nosso socket na porta 5555
  
             ServerSocket servsock = new ServerSocket(5555);
             System.out.println("SERVER On-Line!.  Ouvindo a porta(5555)\nAgandando conexão!\n>");
             
             while(true){// lop forever para estabelecer uma conexão por vez
                  Socket Server = servsock.accept();
                System.out.println(" Conected:");
              EnviarMsg(Server);// assim q algum cliente se conecta enviamos algo a ele
             }
              
    
    }
 
}
