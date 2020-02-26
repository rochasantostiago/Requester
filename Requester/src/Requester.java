import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.net.URISyntaxException;
import javax.xml.bind.JAXBException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class Requester 
{
    public static byte[] readFileData(File file, int fileLength) throws IOException 
    {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try 
        {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } 
        
        finally 
        {
            if (fileIn != null) 
            fileIn.close();
        }

        return fileData;
    }
        
    public static void main(String[] args) throws IOException 
    {                     
        try 
        {           
            Socket socket = new Socket("websrv.cs.fsu.edu", 80);
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);         
            BufferedOutputStream dataOut = null;
            File file = new File(".","text.xml"); 
            int fileLength = (int) file.length(); 
            byte[] fileData = readFileData(file, fileLength);
                                                    
            out.println("POST /~engelen/calcserver.cgi HTTP/1.1");
            out.println("Host: websrv.cs.fsu.edu");
            out.println("Connection: Keep-Alive");
            out.println("Content-Type: text/xml; charset=utf-8");
            out.println("SOAPAction: \"\"");
            out.println("Content-Length: " + fileLength);
            out.println();
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();

            System.out.println(in.nextLine());
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Requester.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }  
}
