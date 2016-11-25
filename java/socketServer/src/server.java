import java.io.*;
import java.net.*;
class Server
{
    ServerSocket server = null;
    Socket client = null;
    static final int port = 3000; //상수값으로 port 번호 설정

    //입력용 Stream
    InputStream is;
    ObjectInputStream ois;

    //출력용 Stream
    OutputStream os;
    ObjectOutputStream oos;

    String receiveData;

    Server()
    {
        try
        {
            server = new ServerSocket(port); //port 번호: 3000으로 ServerSocket 생성

            System.out.println("*****  Server Program이 Clinet 접속을 기다립니다. *****");

            //*** Clinet 접속이 있을 때까지 대기: 접속하는 순간 Socket을 반환 ***//
            client = server.accept();

            //*** 접속이 되면 Clinet로부터 IP 정보를 얻어 출력 ***//
            System.out.println(client.getInetAddress()+"로부터 연결요청");

            //*** Clinet로 부터 수신받은 message를 읽기 위한 입력 Stream ***//
            is = client.getInputStream();
            ois = new ObjectInputStream(is);

            //Clinet로 부터 수신받은 message를 다시 보내기 위한 출력 Stream ***//
            os = client.getOutputStream();
            oos = new ObjectOutputStream(os);

            //*** Clinet가 보내온 message를 Server가 읽은 후 다시 Clinet에게 전송함 ***//
            while ((receiveData = (String)ois.readObject()) != null) //ObjectInputStream.readObject() 호출
            {
                System.out.println("received data = " + receiveData);
                if (receiveData.equals("quit")) //"quit" 입력시 종료
                {
                    break;
                }
                oos.writeObject("→ "+receiveData); //ObjectOutputStream.writeObject() 호출
                oos.flush(); //버퍼의 데이터를 효율적으로 전송하기 위한 method
            }
            is.close();
            ois.close();
            os.close();
            oos.close();
        }
        catch (Exception e)
        {
            System.out.println("통신 Error !!");
            System.exit(0);
        }
    }
    public static void main(String[] args)
    {
        new Server();
    }
}
