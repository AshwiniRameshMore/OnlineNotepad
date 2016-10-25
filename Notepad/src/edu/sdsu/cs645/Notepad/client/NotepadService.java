package edu.sdsu.cs645.Notepad.client;

import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import java.util.*;
import java.io.*;

@RemoteServiceRelativePath("greet")
public interface NotepadService extends RemoteService {

	String validateLogin(String password) throws IllegalArgumentException;

	String load() throws IllegalArgumentException;

	String save(String data) throws IllegalArgumentException;
	
}
