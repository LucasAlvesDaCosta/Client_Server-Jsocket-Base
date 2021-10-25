package Server_prova;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
	public Socket cliente;

	public Servidor(Socket cliente) {
		this.cliente = cliente;
	}

	public static void main(String[] args) throws IOException {

		// Cria um socket na porta 12345
		ServerSocket servidor = new ServerSocket(12345);
		System.out.println("Porta 12345 aberta!");

		// Aguarda alguém se conectar. A execução do servidor
		// fica bloqueada na chamada do método accept da classe
		// ServerSocket. Quando alguém se conectar ao servidor, o
		// método desbloqueia e retorna com um objeto da classe
		// Socket, que é uma porta da comunicação.
		System.out.println("Aguardando conexão do cliente...");

		while (true) {

			Socket cliente = servidor.accept();

			// Cria uma thread do servidor para tratar a conexão
			Servidor tratamento = new Servidor(cliente);

			Thread t = new Thread(tratamento);

			// Inicia a thread para o cliente conectado
			t.start();
		}
	}

	/*
	 * A classe Thread, que foi instancia no servidor, implementa Runnable. Então
	 * você terá que implementar sua lógica de troca de mensagens dentro deste
	 * método 'run'.
	 */
	public void run() {
		System.out.println("Nova conexao com o cliente " + this.cliente.getInetAddress().getHostAddress());
                 
                    int arq =0;
                    System.out.println("\n[Arquivo de texto envia1do para o servidor!]\n");
                    //leio o arquivo e gravo em uma str para enviar ao cliente
                    
       
		try {   
                    File entrada = new File ("gabarito.txt");// arquivo aberto para leitura em stream
                    BufferedInputStream ler2 = new BufferedInputStream(new FileInputStream(entrada));
                   
                    InputStream Recive;
                    Recive = this.cliente.getInputStream();// objeto para receber a mensagem do cliente
                    
                    int recebido=0,cont =1, score =0, erros =0, acertos =0, porcentagem =0, totalErros =0;
                    char data = 0, Lista =0;
                    String Resposta = "";

             // Recebo a msg do cliente em forma de bytes e transformo em 'char'
             // Ao mesmo tempo leio o arquivo de gabarito, desta forma posso comparar
             // resposta por resposta e verificar os erros e acertos.
                        while ((data = (char)Recive.read()) != 4 &&(arq = ler2.read()) != -1){
                            recebido=(char)data;
                         // Resposta refere-se a resposta da alternativa que o cliente mandou
                         // imprimir na tela somente se for; V ou F
                           if(recebido == 'F' || recebido == 'V')
                            System.out.printf("Resposta:(%c)\n",(char)recebido);

                                    Lista =(char)arq; 
                                    // sempre que uma alternativa presente no gabarito de texto for; V ou F
                                    // é impresso na tela a resposta correspondente, desta forma irá aparecer
                                    // uma alternativa (V ou F) do cliente e outra alternativa (V ou F) do gabarito
                                    // do servidor impressas na tela. podendo compara-las visualmente no servidor
                                    if(Lista == 'F' || Lista == 'V')
                                     System.out.printf("Gabarito:[%c]\n",Lista);
                                    
                                     //- Sempre que uma resposta enviada pelo cliente for diferente
                                     // da resposta contida no gabarito eu aumento o indice de erros.
                                     if(Lista != recebido){
                                         System.out.printf("Gabarito:(%s) < -- > Resposta do Cliente:(%s)\n\n",(char)Lista,(char)recebido);
                                         erros ++;                            
                                     } 
                                     else{
                                         // Caso a reposta do cliente seja igual a resposta da alternativa do 
                                         // servidor, o indice de acertos e acrescido
                                          if(Lista == 'F' || Lista== 'V' && Lista == recebido){
                                              System.out.println("Correto!\n");
                                              acertos ++; 
                                          }

                                     } 
                                     // Cada vez que for encontrado no arquivo de gabarito uma quebra de linha,
                                     // significa que cheguei no fim de uma questão, então incremento um contador
                                     // para usa-lo como referencia ao numero das questão finalizada.
                                      if(Lista=='\n'){
                                         System.out.println("Questão:("+cont+")-finalizada.\n"
                                         + "________________________________________________"); 
                                         // Pulo do gato, gravo uma string com a resposta de cada questão
                                         // com a quantidade de acertos e erros e também a porcentagem total de acertos.
                                         Resposta +="Questão:("+cont+")\nAcertos = "+acertos+"\nErros = "+erros+"\n\n";
                                         
                                         cont++; // o contador das alternativas continua
                                          // caso haja alguma resposta esteja incorreta eu mando a sequencia
                                          // de respostas esperadas.     
                                          
                                         score += acertos;// salvar o total de acertos do cliente
                                         totalErros += erros;// total de erros
                                         acertos =0;
                                         erros =0;// os indices de erros e acertos são reiniciados para começar
                                                  // novamente a contar em uma nova questão!!
                                       }

                        }// calcular a porcentagem de acertos!
                        porcentagem =(score*100)/200;
                        // concatenando o restante da reposta na string a ser enviada.
                        Resposta +="\n porcentagem de acertos: "+porcentagem+"%\n";
                        Resposta += "<total de acertos; "+score+"> - <total de erros; "+totalErros+">\n";
                        
			System.out.printf("\nResposta final:\n %s \n",Resposta);
                        OutputStream Saida;
			// Envia mensagem ao servidor                          
                          Saida = this.cliente.getOutputStream();
                           Saida.write(Resposta.getBytes("UTF-8"));
                           Saida.write(4);
                          Saida.flush();
                         
			// Finaliza objetos
                        Saida.close();
			Recive.close();
			this.cliente.close();
		} catch (IOException e) {
		        System.out.println("Não foi possivel completar a ação devido a exeção: "+e.getMessage());
		}
	}
}
