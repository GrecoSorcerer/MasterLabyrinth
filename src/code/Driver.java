package code;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Driver {
	/**
	 * @author Sal
	 * @param args
	 */
	File path = new File("rsc");
	
	private static final String FRAME_NAME = "Master Labyrinth";
	
	private JFrame map, extraTile, player, selection;
	
	private ImageIcon scale = null;
	
	private static final int N = 7;
    private final List<JButton> list = new ArrayList<JButton>();
    
    
    MasterLabyrinthBoard<Tile> game = new MasterLabyrinthBoard<Tile>();
    
    
	public Driver() throws IOException {
		map = new JFrame(FRAME_NAME + " Map");
		extraTile = new JFrame(FRAME_NAME + " Free Tile");
		selection = new JFrame(FRAME_NAME + " Selections");
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		extraTile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reloadVisuals();
	}
	
	private void reloadVisuals() throws IOException {
		buildMap();
		buildExtra();
		JButton RESET = new JButton("Reset");
		RESET.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					map.dispose();
					extraTile.dispose();
					selection.dispose();
					reloadVisuals();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		selection.add(RESET);
		
		selection.pack();
		selection.setLocationByPlatform(true);
		selection.setVisible(true);
	}
	
	private void buildMap() throws IOException {
		
		Image bg = ImageIO.read(new File(path, "MapBackground.png")); //Will use BufferedImage for true implementation
		BufferedImage _bg_ = ImageIO.read(new File(path, "MapBackground.png")); //Will use BufferedImage for true implementation
		ImageIcon _bg = new ImageIcon(_bg_);
		
		JPanel contentPanelMap = new JPanel(new GridLayout(7, 7));
		JLabel mapBGLayer = new JLabel();
		
		contentPanelMap.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		//mapBGLayer.setPreferredSize(new Dimension(512, 512));
		mapBGLayer.setLayout(new FlowLayout(FlowLayout.CENTER, 64, 94 ));
		mapBGLayer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		mapBGLayer.setIcon(_bg);
		
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.setResizable(false);
		
			JButton tileButton[] = new JButton[N * N];
	        for (int i = 0; i < N * N; i++) {
	            int row = i / N;
	            int col = i % N;
	            
	            tileButton[i] = createGridButton(row, col);
	            tileButton[i].setIcon(combinedTileImage(game._grid[row][col]));
	            tileButton[i].setPreferredSize(new Dimension(scale.getIconWidth(), scale.getIconHeight()));
	            list.add(tileButton[i]);
	            contentPanelMap.add(tileButton[i]);
	        }
		
		map.add(mapBGLayer);
	    mapBGLayer.add(contentPanelMap);
		
		map.pack();
		map.setVisible(true);
		
	}
	

	private void buildExtra() throws IOException {
		
		BufferedImage _rotateLeft_ = ImageIO.read(new File(path, "RotateLeft.png"));
		ImageIcon _rotateLeft = new ImageIcon(_rotateLeft_);
		
		BufferedImage _rotateRight_ = ImageIO.read(new File(path, "RotateRight.png"));
		ImageIcon _rotateRight = new ImageIcon(_rotateRight_);
		
		
		JPanel contentpanelExtra = new JPanel(new FlowLayout(FlowLayout.CENTER, 64,64));
		JPanel contentpanelRotate = new JPanel(new FlowLayout(FlowLayout.CENTER, 2,2));
		JPanel contentpanelCombined = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		JLabel contentframeExtra = new JLabel();
		
		JButton rotateRight = new JButton(_rotateRight);
		JButton rotateLeft = new JButton(_rotateLeft);
		
		BufferedImage etileIMG = ImageIO.read(new File(path, game._extraTile.getTexture()));
		ImageIcon _etileIMG = new ImageIcon(etileIMG);
		System.out.println(game._extraTile.id);
		//Tile _extraTile;
		
		contentframeExtra.setIcon(_etileIMG);		
		
		contentpanelCombined.setPreferredSize(new Dimension(100, 100));
		
		contentpanelCombined.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		contentframeExtra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		contentpanelRotate.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		
		rotateRight.setPreferredSize(new Dimension(_rotateRight.getIconWidth(), _rotateRight.getIconHeight()));
		rotateLeft.setPreferredSize(new Dimension(_rotateLeft.getIconWidth(), _rotateLeft.getIconHeight()));
		rotateRight.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		rotateLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		rotateLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game._extraTile.rotateLeft();
				BufferedImage etileIMG = null;
				try {
					etileIMG = ImageIO.read(new File(path, game._extraTile.getTexture()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ImageIcon _etileIMG = new ImageIcon(etileIMG);
				contentframeExtra.setIcon(_etileIMG);
			}
		});
		
		rotateRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game._extraTile.rotateRight();
				BufferedImage etileIMG = null;
				try {
					etileIMG = ImageIO.read(new File(path, game._extraTile.getTexture()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ImageIcon _etileIMG = new ImageIcon(etileIMG);
				contentframeExtra.setIcon(_etileIMG);
			}
		});
		
		
		contentpanelExtra.add(contentframeExtra);
		extraTile.add(contentpanelRotate);
		contentpanelRotate.add(rotateLeft);	
		contentpanelRotate.add(rotateRight);	
		contentpanelCombined.add(contentframeExtra);
		contentpanelCombined.add(contentpanelRotate);
		
		extraTile.add(contentpanelCombined);
		
		extraTile.pack();
		extraTile.setLocationByPlatform(true);
		extraTile.setVisible(true);
		
		
	}
	
	private void buildPlayerCard() {
		
	}
	
	
	int combCount = 0;
	private ImageIcon combinedTileImage(Tile tile) throws IOException {
		
		BufferedImage tileTest = ImageIO.read(new File(path, tile.id + tile.rotation + ".png"));
		
		// create the new image1, canvas size is the max. of both image sizes
		int w = Math.max(tileTest.getWidth(), tileTest.getWidth());
		int h = Math.max(tileTest.getHeight(), tileTest.getHeight());
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		System.out.println(tile.hasPlayerOne);
		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(tileTest, 0, 0, null);
		if (tile.hasToken) {
			int ref = tile.storage.refrenceNumber;
			if (tile.storage.refrenceNumber == 21) {
				ref = 25;
			}
			BufferedImage overlay = ImageIO.read(new File(path, "token" + ref + ".png"));
			g.drawImage(overlay, 0, 0, null);
		}
		
		if (tile.hasPlayerOne) {
			System.out.println(tile.hasPlayerOne);
			BufferedImage playerone = ImageIO.read(new File(path, "PlayerOne.png"));
			g.drawImage(playerone, 0, 0, null);
		}
		if (tile.hasPlayerTwo) {
			BufferedImage playertwo = ImageIO.read(new File(path, "PlayerTwo.png"));
			g.drawImage(playertwo, 0, 0, null);
		}
		if (tile.hasPlayerTwo) {
			BufferedImage playerthree = ImageIO.read(new File(path, "PlayerThree.png"));
			g.drawImage(playerthree, 0, 0, null);
		}
		if (tile.hasPlayerFour) {
			BufferedImage playerfour = ImageIO.read(new File(path, "PlayerFour.png"));
			g.drawImage(playerfour, 0, 0, null);
		}
		g.dispose(); //Release Paint resources
		
		// Save as new image
		String comb = "combined"+ combCount++ +".png";
		ImageIO.write(combined, "PNG", new File(path, comb));
		
		//Save the image to rsc folder
		BufferedImage image = ImageIO.read(new File(path, comb));
		ImageIcon _image = new ImageIcon(image);
		scale = _image;//USed to set scale of buttons and such
		return _image; //Returns the image used for button it's needed on
		
	}
	
	
	/*
	private void hightLightValid() {
		for (int i = 0; i < N * N; i++) {
			int row = i / N;
			int col = i % N;
			
		}
	}
	*/
	
    private JButton getGridButton(int r, int c) {
        int index = r * N + c;
        return list.get(index);
    }
    
    private JButton createGridButton(final int row, final int col) {
        JButton b = new JButton();
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton tileButton = Driver.this.getGridButton(row, col);
                game.makeMove(row, col);
                try {
					buildMap();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                System.out.println("r" + row + ",c" + col
                    + " " + (b == tileButton));
            }
        });
        return b;
    }
 
	public static void main(String[] args) {
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					new Driver();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
	}

}
