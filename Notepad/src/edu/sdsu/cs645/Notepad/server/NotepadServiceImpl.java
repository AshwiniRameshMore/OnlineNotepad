package edu.sdsu.cs645.Notepad.server;

import edu.sdsu.cs645.Notepad.client.NotepadService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements NotepadService {

	@Override
	public String validateLogin(String password) throws IllegalArgumentException{
		if(password.trim().equals("sp2016")) 
			return "Success";
		else
			return "Fail";
	}
	
	public String load() throws IllegalArgumentException{
		String path = getServletContext().getRealPath("/");
		String filename = path + "/data.txt";
		String result = "";
		try{
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			while((line = in.readLine()) != null)
				result += line;
			in.close();
		}
		catch(Exception e){
			return "Error: " +e;
		}
		return result;
	} 
	
	public synchronized String save(String result) throws IllegalArgumentException{
		String path = getServletContext().getRealPath("/");
		String filename = path + "/data.txt";
		try{
			PrintWriter out = new PrintWriter(new FileWriter(filename));
			result = result.replaceAll("\r\n|\n", "<br />");
			out.print(result);
			out.close();
		}
		catch(Exception e){
			return "Error: " +e;
		}
		return "Success";
	}

}
