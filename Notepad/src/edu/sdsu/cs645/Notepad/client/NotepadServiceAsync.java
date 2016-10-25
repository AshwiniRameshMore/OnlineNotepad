package edu.sdsu.cs645.Notepad.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.*;
import java.io.*;

public interface NotepadServiceAsync {

	void validateLogin(String password, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void load(AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void save(String data, AsyncCallback<String> callback) throws IllegalArgumentException;
	
}
