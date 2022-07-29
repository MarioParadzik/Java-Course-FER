package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * This class is a representation of Notepad++
 */
public class JNotepadPP extends JFrame implements MultipleDocumentListener {
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel tabs;
    private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
    private String control = "";
    LJLabel len = new LJLabel("lenght",flp);
    private JLabel l2 = new JLabel("",SwingConstants.CENTER);
    private JLabel l3 = new JLabel("",SwingConstants.RIGHT);
    private int lenght;
    
    /**
     * Basic Constructor.
     */
	public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(20, 20);
        setSize(1000, 1000);
        setTitle("JNotepad++");
        initGUI();
    }
	
	/**
	 * This method is used to initialize the GUI.
	 */
	private void initGUI() {
		initialization();	
		createActions();
		createMenus();
		createToolbars();
		statusBar();
		createBottom();
      
	}


	ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            l3.setText(formattedDate);
        }
    };
	
	private Action exitAction = new LocalizableAction("exit", flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
        public void actionPerformed(ActionEvent e) {
            exit();
        }
	};
	
	private Action makeNewTabAction = new LocalizableAction("new", flp) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            tabs.createNewDocument();
            statusBar();
        }
    };
    
	private Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
        public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = jfc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka "+fileName.getAbsolutePath()+" does not exist!", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			tabs.loadDocument(filePath);
			tabs.setSelectedComponent(tabs.getPanel(tabs.getCurrentDocument()));
			statusBar();
        }
	};
	
	private Action saveDocumentAction = new LocalizableAction("save", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabs.getCurrentDocument().getFilePath() == null || tabs.getCurrentDocument().getFilePath().equals(new File("").toPath())){
	            JFileChooser jfc = new JFileChooser();
	            jfc.setDialogTitle("Save as");
	            jfc.setApproveButtonText("Save");

	            if (jfc.showOpenDialog(JNotepadPP.this) == JFileChooser.APPROVE_OPTION) {
	                File fileToSave = jfc.getSelectedFile();
	                tabs.saveDocument(tabs.getCurrentDocument(),fileToSave.toPath());
	            }
	        }
	        else {
	            tabs.saveDocument(tabs.getCurrentDocument(), tabs.getCurrentDocument().getFilePath());
	        }

		}
	};
	
	private Action saveAsAction = new LocalizableAction("saveAs", flp) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            tabs.setDoc(tabs.getSelectedComponent());
            Path oldPath = tabs.getCurrentDocument().getFilePath();
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save as");
            jfc.setApproveButtonText("Save");

            if (jfc.showOpenDialog(JNotepadPP.this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                tabs.saveDocument(tabs.getCurrentDocument(),file.toPath());
                if(!oldPath.equals(file.toPath()) && !oldPath.toFile().getName().equals("")){
                    oldPath.toFile().delete();
                }
            }
        }
    };
	
    private Action closeAction = new LocalizableAction("closeTab", flp) {
    	
    	private static final long serialVersionUID = 1L;
    	
    	@Override
        public void actionPerformed(ActionEvent e) {
    		if(tabs.getCurrentDocument().isModified()) {
                int rezultat = JOptionPane.showConfirmDialog(JNotepadPP.this,
                		"File not saved." + "\n" +"Do you want to save it?",
                		"Warning",
                		JOptionPane.YES_NO_CANCEL_OPTION,
                		JOptionPane.WARNING_MESSAGE);
                switch (rezultat) {
                    case JOptionPane.CANCEL_OPTION:
                        return;
                    case JOptionPane.YES_OPTION:
                    	if(tabs.getCurrentDocument().getFilePath() == null ||
    					tabs.getCurrentDocument().getFilePath().equals(new File("").toPath())) {
	    				JFileChooser jfc = new JFileChooser();
	    				jfc.setDialogTitle("Save as");
	    	            jfc.setApproveButtonText("Save");
	    	            if (jfc.showOpenDialog(JNotepadPP.this) == JFileChooser.APPROVE_OPTION) {
	    	                File file = jfc.getSelectedFile();
	    	                tabs.saveDocument(tabs.getCurrentDocument(), file.toPath());
	    	            } else {
		    				tabs.saveDocument(tabs.getCurrentDocument(), tabs.getCurrentDocument().getFilePath());
		    			}
		    			JOptionPane.showMessageDialog(
		    					JNotepadPP.this, 
		    					"File saved.", 
		    					"Info", 
		    					JOptionPane.INFORMATION_MESSAGE);
    		
                }
            }
            tabs.closeDocument(tabs.getCurrentDocument());
            tabs.setDoc(tabs.getSelectedComponent());
            statusBar();
    		}
    	}
   };
   
   private Action deleteSelectedPartAction = new LocalizableAction("delete", flp) {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
			JTextArea editor = tabs.getCurrentDocument().getTextComponent();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
            if(len == 0) return;
            int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
            try {
                doc.remove(offset, len);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
		}
   };
   
   private Action cutAction = new LocalizableAction("cut", flp) {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
            JTextArea editor = tabs.getCurrentDocument().getTextComponent();
            int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
            if(len == 0) return;
            int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
            try {
                control = doc.getText(offset, len);
                doc.remove(offset, len);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
		}
   };
   
    private Action copyAction = new LocalizableAction("copy", flp) {
		
		  private static final long serialVersionUID = 1L;
		
		  public void actionPerformed(ActionEvent e) {
			 Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
             JTextArea editor = tabs.getCurrentDocument().getTextComponent();
             int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
             if(len == 0) return;
             int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
             try {
                 control = doc.getText(offset, len);
             } catch (BadLocationException e1) {
                 e1.printStackTrace();
             }
		  }
    };
  
    
    private Action pasteAction = new LocalizableAction("paste", flp) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
            JTextArea editor = tabs.getCurrentDocument().getTextComponent();
            int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
            try {
                doc.insertString(offset, control,null);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    };
    
    private Action statsAction = new LocalizableAction("statsAction", flp) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = tabs.getCurrentDocument().getTextComponent();
            int numberOfNonEmptyCharacters = 0;
            for(char c : editor.getText().toCharArray()){
                if(!Character.isWhitespace(c)) numberOfNonEmptyCharacters++;
            }
            String text = "Your document has "+ editor.getText().toCharArray().length
            		+" characters, " + numberOfNonEmptyCharacters + 
            		" non-blank characters and " +editor.getLineCount()+ " lines.";
            JOptionPane.showMessageDialog(
                    null,
                    text,
                    "Stats",
                    JOptionPane.INFORMATION_MESSAGE,
                    null);
        }
    };
    Action hr = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
            statusBar();
        }
    };
    
    Action en = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
            statusBar();
        }
    };
    
    Action de = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
            statusBar();
        }
    };
    
    /**
     * This method is used to create the bottom of the program.
     */
    private void createBottom() {
		JPanel jp = new JPanel(new GridLayout(1,0));
		jp.add(len);
        jp.add(l2);
        jp.add(l3);
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        l3.setText(formattedDate);
        Timer t = new Timer(1000, taskPerformer);
        t.start();
        this.getContentPane().add(jp,BorderLayout.PAGE_END);
		
	}
    
    /**
     * This method is used to create tool bars elements.
     */
	private void createToolbars() {
	    JToolBar toolBar = new JToolBar("Alati");
	    toolBar.setFloatable(true);

        toolBar.add(new JButton(makeNewTabAction));
        toolBar.add(new JButton(openDocumentAction));
        toolBar.add(new JButton(saveDocumentAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(deleteSelectedPartAction));
        toolBar.add(new JButton(copyAction));
        toolBar.add(new JButton(cutAction));
        toolBar.add(new JButton(pasteAction));
        for (int i = 0; i < 20; i++) {
        	toolBar.addSeparator();
		}
        toolBar.add(new JButton(statsAction));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(new JButton(closeAction));
        toolBar.add(new JButton(exitAction));

        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * This method is used to create menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		LocalizedJMenu fileMenu = new LocalizedJMenu("file",flp);
		menuBar.add(fileMenu);
		
        fileMenu.add(new JMenuItem(makeNewTabAction));
        fileMenu.add(new JMenuItem(openDocumentAction));
        fileMenu.add(new JMenuItem(saveDocumentAction));
        fileMenu.add(new JMenuItem(saveAsAction));
        fileMenu.add(new JMenuItem(closeAction));
        fileMenu.add(new JMenuItem(exitAction));
        
        LocalizedJMenu editMenu = new LocalizedJMenu("edit",flp);
        menuBar.add(editMenu);
        
        editMenu.add(new JMenuItem(copyAction));
        editMenu.add(new JMenuItem(cutAction));
        editMenu.add(new JMenuItem(pasteAction));
        editMenu.add(new JMenuItem(deleteSelectedPartAction));
        
        LocalizedJMenu languageMenu = new LocalizedJMenu("language",flp);
        menuBar.add(languageMenu);
        
        languageMenu.add(hr);
        languageMenu.add(en);
        languageMenu.add(de);

        this.setJMenuBar(menuBar);
	}

	/**
	 * This method is used to create Action.
	 */
	private void createActions() {
        exitAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control E"));
        exitAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_E);
        exitAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Exit aplication.");

        openDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_O);
        openDocumentAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Used to open existing file from disk.");

        saveDocumentAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_S);
        saveDocumentAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Used to save current file to disk.");


        makeNewTabAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control N"));
        makeNewTabAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_N);
        makeNewTabAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Creates a new blank tab.");


        saveAsAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control Q"));
        saveAsAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_P);
        saveAsAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Save file as choosed.");

        closeAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control R"));
        closeAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_R);
        closeAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Close current tab.");

        cutAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_X);
        cutAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Cut selected text.");

        pasteAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_M);
        pasteAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Paste.");

        copyAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_B);
        copyAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Copy selected text.");

        deleteSelectedPartAction.putValue(
                Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("control D"));
        deleteSelectedPartAction.putValue(
                Action.MNEMONIC_KEY,
                KeyEvent.VK_D);
        deleteSelectedPartAction.putValue(
                Action.SHORT_DESCRIPTION,
                "Erases selected text.");

        hr.putValue(
                Action.NAME,
                "HRV"
        );
        en.putValue(
                Action.NAME,
                "ENG"
        );
        de.putValue(
                Action.NAME,
                "DE"
        );

        setActions(false);
	}
	
	/**
	 * This method is used to initialize the program.
	 */
	private void initialization() {
		tabs = new DefaultMultipleDocumentModel(this);
		tabs.addMultipleDocumentListener(this);
		tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(tabs.getSelectedComponent()!=null) {
                	tabs.setDoc(tabs.getSelectedComponent());
                }
                else{
                	tabs.removeAll();
                }
            }
        });
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabs, BorderLayout.CENTER);
		WindowListener windowListener = new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        };
        this.addWindowListener(windowListener);
	}


	/**
	 * This method is used to update the status bar.
	 */
	protected void statusBar() {
		int lenght;
        int ln = 1;
        int col = 1;
        int sel;
        if(tabs.getNumberOfDocuments() == 0){
            lenght = 0;
            ln = 0;
            col = 0;
            sel = 0;
        }
        else {
            JTextArea jTextArea = tabs.getCurrentDocument().getTextComponent();
            int pos = jTextArea.getCaretPosition();
            Document doc = jTextArea.getDocument();
            Element root = doc.getDefaultRootElement();
            int row = root.getElementIndex(pos);
            lenght = jTextArea.getText().length();
            try {
                if(jTextArea.getLineOfOffset(jTextArea.getCaretPosition()) > 0) {
                	ln = jTextArea.getLineOfOffset(jTextArea.getCaretPosition()) + 1;
                }
                col = Math.max(pos - root.getElement(row).getStartOffset(), 1);
            }
            catch (Exception e){}
            try{
                sel = 0;
                if(jTextArea.getSelectedText() != null) sel = jTextArea.getSelectedText().toCharArray().length;
            }
            catch (Exception e){ sel = 0; }
          
        }
        setLenght(lenght);
		len.setText(len.getKey() + ": " + lenght);
        l2.setText("ln: " + ln + " Col: " + col + " Sel: " + sel);
	}
	
	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		statusBar();
        updateTitle();
		
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
        if(tabs.getNumberOfDocuments()>0) {
            setActions(true);
            statusBar();
            updateTitle();
        }
        updateTitle();
		
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
        if(tabs.getNumberOfDocuments()==0){
            setActions(false);
            statusBar();
        }
        updateTitle();
		
	}
	
	/**
	 * This method is used to freeze and activate actions.
	 * @param b True if we need to freeze, false otherwise.
	 */
    private void setActions(boolean b) {
        deleteSelectedPartAction.setEnabled(b);
        copyAction.setEnabled(b);
        cutAction.setEnabled(b);
        pasteAction.setEnabled(b);
        saveAsAction.setEnabled(b);
        saveDocumentAction.setEnabled(b);
        closeAction.setEnabled(b);
        statsAction.setEnabled(b);
		
	}

    /**
     * This method is used to update the title of the program.
     */
	private void updateTitle(){
        if(tabs.getNumberOfDocuments() == 0){
            this.setTitle("JNotepad++");
        }
        else {
            if(tabs.getCurrentDocument().getFilePath() == null){
            	if(tabs.getTitleAt(tabs.getSelectedIndex()).strip().equals("unnamed"+"")) {
            		this.setTitle("unnamed");
            	} else {
            		String num = tabs.getTitleAt(tabs.getSelectedIndex()).split(" ")[1];
            		this.setTitle("unnamed " + num);		
            	}
            } else {
                this.setTitle(tabs.getCurrentDocument().getFilePath().toAbsolutePath().getFileName().toString());
            }
        }
    }

	/**
	 * This method is used to exit the program.
	 */
	private void exit() {
		if (!ready()) {
            int rezultat = JOptionPane.showConfirmDialog(JNotepadPP.this, "Postoji nesnimljeni sadržaj, želite li ga spremiti?", "Upozorenje", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (rezultat) {
                case JOptionPane.CANCEL_OPTION:
                    return;
                case JOptionPane.NO_OPTION:
                    dispose();
                    return;
                case JOptionPane.YES_OPTION:
                    Iterator<SingleDocumentModel> iterator = tabs.iterator();
                    while (iterator.hasNext()) {
                        SingleDocumentModel next = iterator.next();
                        if (next.isModified()) {
                            tabs.setSelectedComponent(tabs.getPanel(next));
                            tabs.setDoc(next.getTextComponent());
                            if (next.getFilePath() == null || next.getFilePath().equals(new File("").toPath())) {
                                JFileChooser jfc = new JFileChooser();
                                jfc.setDialogTitle("Save as");
                                jfc.setApproveButtonText("Save");
                                if (jfc.showOpenDialog(JNotepadPP.this) == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = jfc.getSelectedFile();
                                    tabs.getCurrentDocument().setFilePath(fileToSave.toPath());
                                    tabs.saveDocument(next, fileToSave.toPath());
                                }
                            } else {
                                tabs.saveDocument(next, next.getFilePath());
                            }
                        }
                    }
                    dispose();
                    return;
            }
        } else {
            dispose();
            return;
        }
		
	}
	
	
	
	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	/**
	 * This method is used to tell if documents are saved.
	 * @return
	 */
	private boolean ready(){
        Iterator<SingleDocumentModel> iterator = tabs.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isModified()) return false;
        }
        return true;
    }
	
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JNotepadPP().setVisible(true);
            }
        });
    }
}

