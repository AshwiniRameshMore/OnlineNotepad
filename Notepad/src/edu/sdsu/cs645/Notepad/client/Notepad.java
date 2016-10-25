package edu.sdsu.cs645.Notepad.client;

import edu.sdsu.cs645.Notepad.shared.FieldVerifier;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.*;
import java.io.*;

public class Notepad implements EntryPoint {
	final Label passwordLabel = new Label("Enter your Password");
	final Label statusLabel = new Label("");
	final PasswordTextBox password = new PasswordTextBox();
	final Button loginBtn = new Button("Login");
	final Button clearBtn = new Button("Clear");
    private final NotepadServiceAsync notepadService = GWT.create(NotepadService.class);
	private RichTextArea notepad;
	
	public void onModuleLoad(){
		Label titleLabel = new Label();
	    FlowPanel loginPanel = addLoginPanel();
	    RootPanel.get().add(new HTML("<h1>Online Notepad</h1>"));
	    RootPanel.get().add(loginPanel);
	    RootPanel.get().add(statusLabel);
	    statusLabel.getElement().setId("status");
		password.setFocus(true);
	}
	private FlowPanel addLoginPanel() {
		
		FlowPanel mainPanel = new FlowPanel();
		mainPanel.getElement().setId("main");
		mainPanel.add(passwordLabel);
		mainPanel.add(password);
		
		FlowPanel btnPanel = new FlowPanel();
		btnPanel.getElement().setId("btnPanel");
		btnPanel.add(clearBtn);
		btnPanel.add(loginBtn);
		
		mainPanel.add(btnPanel);
		
		clearBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			  password.setText("");	
			}
		});
		loginBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			 validateLogin(password.getText());	
			}
		});
		
		return mainPanel;
	}
	
	private void bulidNotepad() {
	   FlowPanel main = new FlowPanel();
	   notepad = new RichTextArea();
	   notepad.getElement().setId("note");
	   main.add(notepad);
	   main.add(makeButtonPanel());
	   
	   statusLabel.getElement().setId("status");
	
	   RootPanel.get().clear();
	   RootPanel.get().add(new HTML("<h1>Online Notepad</h1>"));
	   RootPanel.get().add(main);
	   RootPanel.get().add(statusLabel);
	}
	private FlowPanel makeButtonPanel() {
	   final Button load = new Button("ReLoad");
	   final Button save = new Button("Save");
	   final Button clear = new Button("Clear");
		    
	   FlowPanel btnPanel = new FlowPanel();
	   btnPanel.getElement().setId("btnPanel2");
	   btnPanel.add(clear);
	   btnPanel.add(save);
	   btnPanel.add(load);
	   
	   clear.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			  notepad.setHTML("");	
			  notepad.setFocus(true);
			}
		});
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			 save(notepad.getHTML());
			}
		});
		load.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			 load();
			}
		});
	   
	 return btnPanel;
	}
	
	private void validateLogin(String password) {
		AsyncCallback callback = new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace(); 
			}

			@Override
			public void onSuccess(Object result) {
				String answer = (String) result;
				if(answer.equals("Success"))
				{	bulidNotepad();
				    load();
				    notepad.setFocus(true);
				    notepad.getFormatter();
				    notepad.getFormatter().setBackColor("#AAA");
				    notepad.getFormatter().setForeColor("#AAA");
				}
				else
					statusLabel.setText("Invalid Password");
			}
		};
		notepadService.validateLogin(password, callback);
	}
	
	private void load() {
		AsyncCallback callback = new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				statusLabel.setText("Failed"+caught);
				caught.printStackTrace(); 
			}

			@Override
			public void onSuccess(Object result) {
				notepad.setHTML((String)result);
				statusLabel.setText("Data Loaded from file");
			}
		};
		notepadService.load(callback);
	}
	private void save(String data) {
		AsyncCallback callback = new AsyncCallback() {

			@Override
			public void onFailure(Throwable caught) {
				statusLabel.setText("Failed"+caught);
				caught.printStackTrace(); 
			}

			@Override
			public void onSuccess(Object result) {
				String answer = (String) result;
				if(answer.equals("Success"))
					statusLabel.setText("Data Saved");
			  	else
					statusLabel.setText(answer);
			}
		};
		notepadService.save(data, callback);
	}
}
