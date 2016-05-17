import java.net.*;
import java.io.*;
import java.util.*;

public class Mail
{
  private String mail_to;
  BufferedReader br;
  OutputStream os;
  public Mail(String mail_to)
  {
    this.mail_to = mail_to;
    System.out.println(mail_to);
  }

  public boolean sendMail(String message)
  {
    try
    {
      Socket  socket= new Socket("kendy.up.ac.za", 25);
      br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      os = socket.getOutputStream();
    }
    catch (Exception ex){}

    writeMessage("helo me");
    writeMessage("mail from: donotreply@cs.up.ac.za");
    writeMessage("rcpt to: " + mail_to);
    writeMessage("data");
    try
    {
      os.write((message + "\n").getBytes());
      os.flush();
      os.write((".\n").getBytes());
      os.flush();
    }
    catch (Exception ex){}
    return true;
  }

  private void writeMessage(String message)
  {
    try
    {
      System.out.println(br.readLine());
      System.out.println(message);
      os.write((message + "\n").getBytes());
      os.flush();
    }
    catch (Exception ex){}
  }
}
