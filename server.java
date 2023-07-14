package server.site;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class yash {
    public static void main(String[] args) {
        System.out.println("Starting Server");
        try (ServerSocket serverSocket = new ServerSocket(1919))
        {
            while (true)
            {
                try (Socket client = serverSocket.accept()){

                    InputStreamReader isr = new InputStreamReader(client.getInputStream()) ;
                    BufferedReader br = new BufferedReader(isr) ;
                    StringBuilder request = new StringBuilder() ;

                    String Line ;

                    Line = br.readLine() ;
                    while (!Line.isBlank())
                    {
                        request.append(Line+"\r\n") ;
                        Line = br.readLine() ;
                    }

                    System.out.println("Request Recived\n");
                    String route =  request.toString().split("\n")[0].split(" ")[1];
                    System.out.println(route);
                    if (route=="/")
                    {
                        FileInputStream html = new FileInputStream("./server/site/index.html") ;
                        BufferedReader fileReader = new BufferedReader(new FileReader("./server/site/index.html")) ;

                        String Line1 ;

                        Line1 = fileReader.readLine() ;
                        while (!Line.isBlank())
                        {
                            request.append(Line1+"\r\n") ;
                            Line1 = br.readLine() ;
                        }


                        OutputStream clientOutputStream = client.getOutputStream();
                        clientOutputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        clientOutputStream.write("\r\n".getBytes());
                        clientOutputStream.write(html.readAllBytes());
                        clientOutputStream.flush();
                        client.close();
                    }
                    else {
                        FileInputStream html = new FileInputStream("./server/site"+route) ;
                        OutputStream clientOutputStream = client.getOutputStream();
                        clientOutputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                        clientOutputStream.write("\r\n".getBytes());
                        clientOutputStream.write(html.readAllBytes());
                        clientOutputStream.flush();
                        client.close();
                    }

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
