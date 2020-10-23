package example.annotating.boundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import example.annotating.controller.DeleteAnnotationController;
import example.annotating.controller.LoadImageController;
import example.annotating.controller.SaveAnnotationController;
import example.annotating.model.Model;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class AnnotationApp extends JFrame {

	private JPanel				contentPane;
	Model 						model;
	ImagePanel 					panel;				
	JButton 					btnLoad, btnDelete, btnSave, btnCancel;
	
	JTextArea 					displayAnnotationTextArea;
	JTextArea 					enterAnnotationTextArea;
	JTextArea 					textAreaOverlapAlert;
	
	JLabel 						annotationDisplayLabel;
	JLabel 						annotationEnterLabel;
	
	ImageFileChooser			loadFileChooser;

	private JSeparator 			separator;
	private JSeparator 			separator_1;
	
	private JScrollPane 		scrollPaneEnterAnnotationText;
	private JScrollPane 		scrollPaneDisplayAnnotationText;

	// access all the actionable elements
	public ImagePanel getImagePanel() { return panel; }
	
	public JButton getDeleteButton() { return btnDelete; }
	public JButton getSaveButton() { return btnSave; }
	public JButton getCancelButton() { return btnCancel; }
	
	public JTextArea getDisplayAnnotationTextArea() { return displayAnnotationTextArea; }
	public JTextArea  getEnterAnnotationTextArea() { return enterAnnotationTextArea; }
	
	public ImageFileChooser	getLoadFileChooser() { return loadFileChooser; }
	
	public void setOverlapAlert() { textAreaOverlapAlert.setText(model.getOverlapAlert()); }
	public void clearOverlapAlert() { textAreaOverlapAlert.setText(""); };
	public JTextArea getOverlapAlert() { return textAreaOverlapAlert; }
	
	/**
	 * Create the frame.
	 */
	public AnnotationApp(Model m) {
		super();
		setResizable(false);
		this.model = m;
		setTitle("Annotation Application");
		// tell the app window that we will be responsible for closing application 
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 928, 582);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel = new ImagePanel(this.model, this);
		panel.setPreferredSize(new Dimension(640, 480));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		panel.setBorder(blackline);
		
		annotationDisplayLabel = new JLabel("Annotation");
		annotationDisplayLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		loadFileChooser = new ImageFileChooser(this.model);
		
		btnLoad = new JButton("Load Image");
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadImageController(model, AnnotationApp.this).load();
				File f = new File(model.getImagePath());
				try {
					@SuppressWarnings("deprecation")
					Image image = new ImageIcon(f.toURL()).getImage();
					panel.setImage(image);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DeleteAnnotationController(model, AnnotationApp.this).delete();	
				repaint();
			}			
		});		
		
		btnSave = new JButton("Save");		
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SaveAnnotationController(model, AnnotationApp.this).save();
				repaint();
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.setEnabled(false);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SaveAnnotationController(model, AnnotationApp.this).cancelAnnotation();
				repaint();
			}
		});
		
		annotationEnterLabel = new JLabel("Enter Annotation Here");
		annotationEnterLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		
		JLabel lblNewLabel = new JLabel("Image ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		scrollPaneEnterAnnotationText = new JScrollPane();
		
		scrollPaneDisplayAnnotationText = new JScrollPane();
		
		textAreaOverlapAlert = new JTextArea();
		textAreaOverlapAlert.setFont(new Font("Monospaced", Font.BOLD, 15));
		textAreaOverlapAlert.setForeground(Color.RED);
		textAreaOverlapAlert.setBackground(SystemColor.menu);
		textAreaOverlapAlert.setEditable(false);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(separator_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
										.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPaneEnterAnnotationText)
										.addComponent(annotationDisplayLabel, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPaneDisplayAnnotationText, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
										.addComponent(annotationEnterLabel, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(btnLoad, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textAreaOverlapAlert)
							.addGap(264))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(annotationDisplayLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPaneDisplayAnnotationText, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete)
							.addGap(10)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(annotationEnterLabel)
							.addGap(8)
							.addComponent(scrollPaneEnterAnnotationText, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSave)
								.addComponent(btnCancel))
							.addGap(12)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnLoad))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textAreaOverlapAlert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		
		displayAnnotationTextArea = new JTextArea(); 
		scrollPaneDisplayAnnotationText.setViewportView(displayAnnotationTextArea);
		displayAnnotationTextArea.setLineWrap(true);
		displayAnnotationTextArea.setWrapStyleWord(true);
		displayAnnotationTextArea.setEditable(false);
		displayAnnotationTextArea.setText("");
		
		enterAnnotationTextArea = new JTextArea();
		scrollPaneEnterAnnotationText.setViewportView(enterAnnotationTextArea);
		enterAnnotationTextArea.setEnabled(false);
		enterAnnotationTextArea.setLineWrap(true);
		enterAnnotationTextArea.setWrapStyleWord(true);
		
		contentPane.setLayout(gl_contentPane);
	}
}
