package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public MessageData data;
	
	public MessagePanel(MessageData data) {
		this.data = data;
		Dimension thisMaxSize = this.getMaximumSize();
		
		JLabel whoSendLabel = new JLabel(
				(data.whoSend.equals(Main.socketController.userName) ? "You" : data.whoSend) + ": ");
		
		whoSendLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		contentPanel.setBackground(Color.white);
		
		if(data.type.equals("notify")) {
			JTextArea textContent = new JTextArea(data.content);
			textContent.setFont(new Font("Dialog", Font.ITALIC, 15));
			textContent.setForeground(Color.red);
			textContent.setEditable(false);
			
			contentPanel.add(textContent);
			this.setMaximumSize(new Dimension(thisMaxSize.width, 25));
	
		} else if(data.type.equals("text")) {
			JTextArea textContent = new JTextArea(data.content);
			textContent.setFont(new Font("Dialog", Font.PLAIN, 15));
			textContent.setEditable(false);
			contentPanel.add(textContent);
			
			int lineCount = data.content.split("\r\n|\r\n").length;
			if(lineCount > 1) {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 19 * lineCount));
			} else {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 25));		
			}
		} else if(data.type.equals("file")) {
			contentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			contentPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						String fileName = data.content;
						String extension = fileName.split("\\.")[1];
						
						JFileChooser jfc = new JFileChooser();
						jfc.setDialogTitle("Choose path the download");
						jfc.setFileFilter(new FileNameExtensionFilter(extension.toUpperCase() + " files", extension));
						jfc.setSelectedFile(new File(fileName));
						int result = jfc.showSaveDialog(contentPanel);
						jfc.setVisible(true);
						
						if(result == JFileChooser.APPROVE_OPTION) {
							String filePath = jfc.getSelectedFile().getAbsolutePath();
							if(!filePath.endsWith("." + extension))
								filePath += "." + extension;
							
							Room room = Room.findRoom(Main.socketController.allRooms, MainScreen.chattingRoom);
							int messageIndex = -1;
							for(int i = 0; i < room.messages.size(); i++) {
								if(room.messages.get(i) == data) {
									messageIndex = i;
									break;
								}
							}
							Main.socketController.downloadFile(room.id, messageIndex, fileName, filePath);
						}
					}
			});
			
			JLabel fileIcon = new JLabel();
			try {
				String extension = data.content.split("\\.")[1];
				Random r = new Random();
				File tempFile = new File("temp" + r.nextInt() + r.nextInt() + r.nextInt() + "." + extension);
				tempFile.createNewFile();
				fileIcon.setIcon(FileSystemView.getFileSystemView().getSystemIcon(tempFile));
				tempFile.delete();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			JLabel fileNameLabel = new JLabel("<HTML><U>" + data.content + "</U></HTML>");
			fileNameLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
			
			contentPanel.add(fileIcon, new GBCBuilder(1, 1).setWeight(0, 0).setAnchor(GridBagConstraints.LINE_START).setFill(GridBagConstraints.NONE).setInsets(0, 0, 0, 5));
			contentPanel.add(fileNameLabel,
					new GBCBuilder(2, 1).setWeight(1, 0).setAnchor(GridBagConstraints.LINE_START));
			this.setMaximumSize(new Dimension(thisMaxSize.width, 30));
			
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		whoSendLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(whoSendLabel);
		contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(contentPanel);
		this.setBackground(null);
	}
}
