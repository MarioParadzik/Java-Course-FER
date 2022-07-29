package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class is an implementation of SingelDocumentModel.
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	private Path path;
	private JTextArea textArea;
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	private boolean flag;
	
	/**
	 * Basic Constructor
	 * @param filePath Document path.
	 * @param text Document text.
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.path = filePath != null ? filePath : null;
		this.textArea = new JTextArea(text);
		
		textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                for(SingleDocumentListener l : listeners){
                    l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
                }
            }
        });
		
		this.flag = false;
		this.textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				flag = true;
				notifyListeners();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				flag = true;
				notifyListeners();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				flag = true;
				notifyListeners();
			}

		});
	}
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path, "Path cannot be null!");
		byte[] okteti;
        try {
            okteti = Files.readAllBytes(path);
        } catch(Exception ex) {
            return;
        }
        String tekst = new String(okteti, StandardCharsets.UTF_8);
        this.textArea.setText(tekst);
        notiftPathListeners();
	}
	
	/**
	 * This method is used to notify all path listeners.
	 */
	private void notiftPathListeners() {
		for(SingleDocumentListener singleDocumentListener : listeners)
            singleDocumentListener.documentFilePathUpdated(this);
	}

	
	@Override
	public boolean isModified() {
		return flag;
	}

	@Override
	public void setModified(boolean modified) {
		this.flag = modified;
		notifyListeners();	
	}

	/**
	 * This method is used to notify all document listeners.
	 */
	private void notifyListeners() {
		for(SingleDocumentListener singleDocumentListener : listeners)
            singleDocumentListener.documentModifyStatusUpdated(this);
    }
	
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

	
}
