/*
 Autor; Lucas Alves da costa
projeto, Cliente socket
 */
package clientesocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Lucas Alves
 */
public class ClienteSocket {


 
    public static void main(String[] args)throws Exception{
         Menu();// ja começo chamando o menu
    }
    
       //***************** menu básico ***********************//                           
    public static void Menu() throws IOException {
       // inicia a conexão com o servidor raiz   
          System.out.println("Escolha uma opção:\n [1]. Conectar com servidor.\n [2]. Sair!\n");
         Scanner Ler;
         Ler = new Scanner(System.in);
         int Opc;
         Socket Cliente;
           Opc = Ler.nextInt();
           if(Opc==2){
               System.exit(0);
           }
           else{
               // se a opção for diferente de sair é criada uma conexão local
               // com o localhost e a porta xxxx
            Cliente = new Socket("127.0.0.1",5555);//conect na mesma porta e ip do servidor
             System.out.println("Conectado com o servidor. . .");
              receberMsg(Cliente);// se a porta estiver desocupada é iniciada a conexão
           }  
    }
    public static void Download(String Lista,Socket Cliente) throws IOException {
        // Função para receber a string e reverte a mesma.
        //É necessario que a conexão com o cliente seja passada para a função para 
        //não perdemos a mesma!
        
           int size = Lista.length();
           int i,j;
           String palindrome = Lista;
           int len = palindrome.length();
           
           char[] tempCharArray = new char[len];
           char[] charArray = new char[len];
    // lendo  a string e gravando em um array
    for (i = 0; i < len; i++) {
      tempCharArray[i] = palindrome.charAt(i);
    }
    // revertendo o array anterior colocando cada posição em ordem decrescente em cada posição do outro vetor em ordem 
    // crescente
    for (j = 0; j < len; j++) {
      charArray[j] = tempCharArray[len - 1 - j];
    }
        String novaMSG  = new String(charArray);
    System.out.println("Mensagem reversa: "+novaMSG);
  
        // Aqui envia  a mensagem revertida em forma de stream
           OutputStream Saida;
              Saida = Cliente.getOutputStream();
              Saida.write(novaMSG.getBytes());
              Saida.write(4);
           Saida.flush();
  /*************************************************************************************/ 
              Menu();//retorno ao menu para uma nova conexão!!
            
    }
      
    

    /**
     *
     * @param Cliente
    
     * @throws java.io.IOException
     
     */
   // @SuppressWarnings("empty-statement")
   
   @SuppressWarnings("empty-statement")
    public static void receberMsg(Socket Cliente) throws IOException {
        // Essa função é responsavel por receber os dados enviados pelo servidor
        InputStream Recive;
        Recive = Cliente.getInputStream();
      int recebido = 0; 
      char DATE = 0; 
      String Lista="";
     // Recebe o nome do arquivo, faço um cast para converter o dado recebido em "char"
      while ((DATE = (char)Recive.read()) != 4) {
        recebido =DATE; 
        Lista +=(char)DATE;// Gravo tudo que for recebido pelo servidor na string lista!!
     };
 
           // Passo a string recebida pelo servidor e a conexão para a função que irá tratar a string
           Download(Lista,Cliente);
        
 }
  
}
    
