package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//繪製畫面，繼承JFrame監聽滑鼠功能

public class GameWin extends JFrame {
	private static final long serialVersionUID = 1L;
	int wigth = 2 * GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH;
	int height = 4 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH;

	Image offScreenImage = null;
	MapBottom mapBottom = new MapBottom();
	MapTop mapTop = new MapTop();
	GameSelect gameSelect = new GameSelect();
	// 是否開始遊戲，false:未開始，true:開始
	boolean begin = false;

	void launch() { // 創建畫面
		GameUtil.START_TIME = System.currentTimeMillis();
		this.setVisible(true); // 視窗是否
		if (GameUtil.state == 3) {
			this.setSize(500, 500
					);
		} else {
			this.setSize(wigth, height); // 視窗大小}
		}

		this.setLocationRelativeTo(null); // 視窗位置
		this.setTitle("踩地雷"); // 標題
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 關閉視窗
		// 滑鼠事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				switch (GameUtil.state) {
				case 0:
					if (e.getButton() == 1) { // 滑鼠左鍵
						GameUtil.MOUSE_X = e.getX(); // 抓滑鼠座標
						GameUtil.MOUSE_Y = e.getY();
						GameUtil.LEFT = true; // 修改滑鼠狀態
					}
					if (e.getButton() == 3) { // 滑鼠右鍵
						GameUtil.MOUSE_X = e.getX();
						GameUtil.MOUSE_Y = e.getY();
						GameUtil.RIGHT = true;
					}
				case 1:
				case 2:
					if (e.getButton() == 1) {
						if (e.getX() > GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2)
								&& e.getX() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2)
										+ GameUtil.SQUARE_LENGTH
								&& e.getY() > GameUtil.OFFSET && e.getY() < GameUtil.OFFSET + GameUtil.SQUARE_LENGTH) {
							mapBottom.restart();
							mapTop.restart();
							GameUtil.FLAG_NUM = 0;
							GameUtil.START_TIME = System.currentTimeMillis();
							GameUtil.state = 0;
						}
					}
					// 滑鼠點選滾輪開始重新選擇難度
					if(e.getButton()==2) {
						GameUtil.state=3;
						begin=true;
					}
					break;
				case 3:
					if (e.getButton() == 1) { // 滑鼠左鍵
						GameUtil.MOUSE_X = e.getX();
						GameUtil.MOUSE_Y = e.getY();
						begin = gameSelect.hard();
					}
					break;
				default:

				}

			}
		});
		// 無窮迴圈保持持續繪製
		while (true) {
			repaint();
			begin();
			try {
				Thread.sleep(40); // 每40秒更新
			} catch (

			InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	void begin() {
		if (begin) {
			begin = false;
			gameSelect.hard(GameUtil.level);
			dispose();
			GameWin gameWin = new GameWin();
			GameUtil.START_TIME = System.currentTimeMillis();
			GameUtil.FLAG_NUM = 0;
			mapBottom.restart();
			mapTop.restart();
			gameWin.launch();

		}
	}

	@Override // 畫地雷位置
	public void paint(Graphics g) {
		if (GameUtil.state == 3) {
			g.setColor(new Color(205,201,201));
			g.fillRect(0, 0, 500, 500);
			gameSelect.paintSelf(g);
		} else {
			offScreenImage = this.createImage(wigth, height);
			Graphics gImage = offScreenImage.getGraphics();
			// 設置背景顏色
			gImage.setColor(new Color(205,201,201));
			gImage.fillRect(0, 0, wigth, height);

			mapBottom.paintSelf(gImage);
			mapTop.paintSelf(gImage);
			g.drawImage(offScreenImage, 0, 0, null);
		}

	}

	public static void main(String[] args) {
		GameWin gameWin = new GameWin();
		gameWin.launch();
	}

}