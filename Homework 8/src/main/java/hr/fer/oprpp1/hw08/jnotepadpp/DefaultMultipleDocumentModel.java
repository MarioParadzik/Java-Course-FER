package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


/**
 * This class represents an implementation of MultipleDocumentModel
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel, SingleDocumentListener {

	private static final long serialVersionUID = 1L;
    private ImageIcon unsaved = getImg("icons/unsaved.png");
    private ImageIcon saved = getImg("icons/saved.png");
	private static final String EMPTY_STRING = "";
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners;
	private Map<SingleDocumentModel, JPanel> mp = new HashMap<>();
	private JNotepadPP jNotepadPP;
	private static int lastUnnamedindex = 0;
	private List<Integer> numbers = new LinkedList<>();
	public DefaultMultipleDocumentModel(JNotepadPP jNotepadPP) {
		this.jNotepadPP = jNotepadPP;
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		addChangeListener(e -> {
			SingleDocumentModel oldDoc = current;
			int newTabIndex = this.getSelectedIndex();
			current = newTabIndex != -1 ? documents.get(newTabIndex) : null;
			notifyRegisteredListeners(listener -> listener.currentDocumentChanged(oldDoc, current));
		});
	}
	
	/**
	 * This method is used for converting a path into ImageIcon.
	 * @param string Path.
	 * @return ImageIcon of the path.
	 */
	private ImageIcon getImg(String string) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(DefaultMultipleDocumentModel.class.getResource(string));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image scaled = image.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaled);
		return icon;
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel sdm = new DefaultSingleDocumentModel(null, "");
		sdm.setModified(true);
		notifyRegisteredListeners(listener -> listener.currentDocumentChanged(current, sdm));
		current = sdm;
        documents.add(current);
        current.addSingleDocumentListener(this);
        JScrollPane jsp = new JScrollPane(current.getTextComponent());
        JPanel jp = new JPanel(new BorderLayout());
        jp.add(jsp, BorderLayout.CENTER);
        mp.put(current, jp);
        addTab("unnamed " + getIndex(), unsaved, jp);
        setSelectedComponent(jp);
        notifyRegisteredListeners(listener -> listener.documentAdded(current));
        return current;
	}

	/**
	 * This method is used to get the index of new unnamed documents.
	 * @return First available index.
	 */
	private String getIndex() {
		if(lastUnnamedindex == 0) {
			numbers.add(0);
			lastUnnamedindex++;
			return EMPTY_STRING;
		}
		
		if(lastUnnamedindex > 0 && !numbers.contains(0)) {
			numbers.add(0);
			return EMPTY_STRING;
		}
		
		for(int i = 0; i < lastUnnamedindex; i++) {
			if(!numbers.contains(i)) {
				numbers.add(i);
				return String.valueOf(i);
			}
		}
		
		numbers.add(lastUnnamedindex);
		return String.valueOf(lastUnnamedindex++);
	}

	/**
	 * This method is used to notify all listeners.
	 * @param action Notify listeners about given action.
	 */
	private void notifyRegisteredListeners(Consumer<MultipleDocumentListener> action) {
		listeners.forEach(action);
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path can't be null!");
		if (!Files.exists(path)) throw new IllegalArgumentException(path + " does not exist!");
		
		for(SingleDocumentModel sdm : documents) {
			if(sdm.getFilePath() != null && sdm.getFilePath().equals(path)) {
				notifyRegisteredListeners(listener -> listener.currentDocumentChanged(current, sdm));
				current = sdm;
				return sdm;
			}
		}
		
		if(findForPath(path) == null) {
			try {
				SingleDocumentModel sdm = new DefaultSingleDocumentModel(path,new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
				current = sdm;
				documents.add(current);
				notifyRegisteredListeners(listener -> listener.currentDocumentChanged(current, sdm));
				JScrollPane jsp = new JScrollPane(current.getTextComponent());
		        JPanel jp = new JPanel(new BorderLayout());
		        jp.add(jsp, BorderLayout.CENTER);
		        mp.put(current, jp);
		        addTab(current.getFilePath().toFile().getName(), saved, jp);
		        notifyRegisteredListeners(listener -> listener.documentAdded(current));
		        current.addSingleDocumentListener(this);
                setIconAt(documents.indexOf(sdm), saved);
                return sdm;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		String num = null;
		if(getTitleAt(getSelectedIndex()).strip().equals("unnamed"+"")) {
			if(getTitleAt(getSelectedIndex()).split(" ").length == 1) {
				num = "0";
			} else {
				 num = getTitleAt(getSelectedIndex()).split(" ")[1];
			}
		}
		String a = getCurrentDocument().getTextArea().getText();
        getCurrentDocument().setFilePath(newPath);
		Objects.requireNonNull(model, "model can't be null.");
		if(newPath.toFile().exists() && !newPath.equals(model.getFilePath())){
            int rezultat = JOptionPane.showConfirmDialog(jNotepadPP,
            		newPath.toFile().getName() + " allready exists." + "\n" + "Do you want to replace it?",
            		"Upozorenje",
            		JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            switch (rezultat){
                case JOptionPane.NO_OPTION:
                    return;
            }
        }      
		model.getTextArea().setText(a);
        byte[] okteti = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		if(!(model.getFilePath() == null || newPath.equals(new File("").toPath()))) {
			try {
				Files.write(newPath, okteti);
				current.setFilePath(newPath);
				current.setModified(false);
				int index = getSelectedIndex();
				if (getTabCount() < index) {
                    setIconAt(getSelectedIndex(), saved);
                    setTitleAt(getSelectedIndex(), current.getFilePath().toFile().getName());
                }
				int i = -1;
				try {
					i = Integer.parseInt(num);
				} catch (Exception e) {
					return;
				}
				if(num != null && i > 0) {
					numbers.remove(i);
				} 
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
                        jNotepadPP,
                        newPath.toFile().getAbsolutePath() + ".\n" + "Warning: Can't write file when not saved.",
                        "Pogreška",
                        JOptionPane.ERROR_MESSAGE);
                return;
			}
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		String num = null;
		if(!getTitleAt(getSelectedIndex()).strip().equals("unnamed"+"")) {
			 num = getTitleAt(getSelectedIndex()).split(" ")[1];
		}
		int i = -1;
		try {
			i = Integer.parseInt(num);
		} catch (Exception e) {
			return;
		}
		
		if(num != null && i > 0) {
			numbers.remove(i);
		}
		
		int oldIndex = getIndexOfDocument(model);
        int newIndex = oldIndex >= this.getNumberOfDocuments() - 1 ? oldIndex - 1 : oldIndex;
        if (!documents.remove(model)) return;
		notifyRegisteredListeners(listener -> listener.documentRemoved(model));
		removeTabAt(getSelectedIndex());
		current = !documents.isEmpty() ? current = documents.get(newIndex) : null;
        notifyRegisteredListeners(listener -> listener.currentDocumentChanged(model, current));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		Objects.requireNonNull(path, "path can't be null.");
		for(SingleDocumentModel sdm : documents) {
			if(sdm.getFilePath() == null) continue;
			if(sdm.getFilePath().equals(path)) return sdm;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
			return documents.indexOf(doc);
	}
	
	/**
	 * This method is used to return the panel where other components are combined.
	 * @param sdm Model.
	 * @return Panel for the given model.
	 */
	public JPanel getPanel(SingleDocumentModel sdm){
        return mp.get(sdm);
    }
	
	/**
	 * This method is used to set the current document.
	 * @param selectedComponent Selected document.
	 */
	public void setDoc(Component selectedComponent) {
		if(selectedComponent != null) {
            if (selectedComponent.getClass().equals(JTextArea.class)) {
                for (SingleDocumentModel sdm : documents) {
                    if (sdm.getTextComponent() == selectedComponent) {
                        current = sdm;
                        break;
                    }
                }
            } else if (selectedComponent.getClass().equals(JPanel.class)) {
                for (SingleDocumentModel s : mp.keySet()) {
                    if (mp.get(s).equals(selectedComponent)) {
                        current = s;
                        break;
                    }
                }
            }
            notifyRegisteredListeners(listener -> listener.currentDocumentChanged(null, current));
		}
	}

	/**
	 * This method is a getter for opened documents.
	 * @return Opened documents.
	 */
	public List<SingleDocumentModel> getDocuments() {
		return documents;
	}
	
	/**
	 * This method is used to set the titles of Documents.
	 */
	private void setTitles(){
	       int tabCount = getTabCount();
	       for(SingleDocumentModel sdm : documents){
	           int index = getIndexOfDocument(sdm);
	           if(tabCount > index)
	               if(sdm.getFilePath() == null){
	            	   if(getTitleAt(index).equals("unnamed ")) {
	            		   setTitleAt(index,"unnamed ");
	            	   } else {
		            	   String num = getTitleAt(index).split(" ")[1];
		                   setTitleAt(index,"unnamed " + num);
	            	   }
	               }
	               else{
	                   setTitleAt(index,sdm.getFilePath().toFile().getName());
	               }
	       }
	}
	
	/**
	 * This method is used to set the Icons of the tabs.
	 */
    private void setIcons(){
        int tabCount = getTabCount();
        for(SingleDocumentModel sdm : documents){
            int index = getIndexOfDocument(sdm);
            if(tabCount > index){
                if (sdm.isModified()) {
                    setIconAt(index, unsaved);
                } else {
                    setIconAt(index, saved);
                }
            }
        }
    }
    
	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel model) {
		jNotepadPP.statusBar();
        setIcons();
		
	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel model) {
		setTitles();
	}

	
}
