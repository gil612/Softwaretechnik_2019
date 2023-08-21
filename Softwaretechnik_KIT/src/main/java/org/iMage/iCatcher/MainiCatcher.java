package org.iMage.iCatcher;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Gil Baram
 *
 */
public class MainiCatcher extends JFrame implements ActionListener {
	/**
	 * Define serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    

    private String str = "src\\main\\resources\\apple.png";
	JFrame frame = new JFrame("iCatcher");
	JPanel panel = new JPanel();
	JLabel image1 = new JLabel(new ImageIcon(str));
	JButton image2 = new JButton(new ImageIcon(str));
	JButton showCurve = new JButton("SHOW CURVE");
	JButton saveCurve = new JButton("SAVE CURVE");
	JButton saveHDR = new JButton("SAVE HDR");
	JButton loadDir = new JButton("LOAD DIR");
	JButton loadCurve = new JButton("LOAD CURVE");
	JButton runHDrize = new JButton("RUN HDrize");
	
	
	/**
	 * Constructor Method
	 */
	public MainiCatcher() {

		super("");
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		gbc.insets = new Insets(20, 20, 20, 20);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		gbc.weightx = 0.5;
		gbc.ipady = 250;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(image1, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 250;
		gbc.gridwidth = 3;
		gbc.weightx = 0.5;
		gbc.gridx = 3;
		gbc.gridy = 0;
		panel.add(image2, gbc);
		image2.addActionListener(new java.awt.event.ActionListener() {
			
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JPanel pane = new JPanel() {
 
					private static final long serialVersionUID = 1L;

					@Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);                           
                        try {
                        	BufferedImage image = ImageIO.read(new File(str));
            				g.drawImage(image, image.getWidth(), image.getHeight(), null);
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
                    }
            		};
            		JFrame frame2 = new JFrame();
            		pane.setPreferredSize(new Dimension(1000, 1000));
            		frame2.add(pane);
            		frame2.pack();
            		frame2.setVisible(true);
            }
        });
		
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.ipady = 0;
		gbc.gridx = 3;
		gbc.gridy = 1;
		panel.add(showCurve, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.ipady = 0;
		gbc.gridx = 4;
		gbc.gridy = 1;
		panel.add(saveCurve, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.gridx = 5;
		gbc.gridy = 1;
		panel.add(saveHDR, gbc);

		String[] cbl1 = {"Standard Curve"};		
		JComboBox jcb = new JComboBox(cbl1);
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(jcb, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JSlider s = new JSlider(0, 1000);
		s.setPaintTrack(true); 
        s.setPaintTicks(true); 
        s.setPaintLabels(true); 
        
        s.setMajorTickSpacing(1000); 
        s.setMinorTickSpacing(50); 
  
        gbc.gridwidth = 2;
		gbc.gridx = 3;
		gbc.gridy = 2;
		panel.add(s, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JTextField tfName = new JTextField("", 15);
		gbc.gridwidth = 1;
		gbc.gridx = 5;
		gbc.gridy = 2;
		panel.add(tfName, gbc);
		
		String[] cBlist = {"Simple Map"};
		jcb = new JComboBox(cBlist);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(jcb, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.gridx = 3;
		gbc.gridy = 3;
		panel.add(loadDir, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.gridx = 4;
		gbc.gridy = 3;
		panel.add(loadCurve, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.1;
		gbc.gridx = 5;
		gbc.gridy = 3;
		panel.add(runHDrize, gbc);
		runHDrize.addActionListener(this);	
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setMinimumSize(new Dimension(800, 700));
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 *
	 * Action method after clicking a button 
	 * @param evt 
	 */
	public void actionPerformed(ActionEvent evt ){

		
		if (((JButton) evt.getSource()).getText().equals("image2")) {
			
		}

		}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainiCatcher();		
			}
		});
	}
}