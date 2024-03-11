package com.sxt;

import java.awt.*;

// 底圖、畫遊戲相關組件
public class MapBottom {
	BottomBomb bottombomb = new BottomBomb();
	BottomNum bottomnum = new BottomNum();
	{
		bottombomb.newBomb();
		bottomnum.newNum();
	}

	// 重新開始遊戲方法
	void restart() {
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				GameUtil.DATA_BOTTOM[i][j] = 0;
			}
		}
		bottombomb.newBomb();
		bottomnum.newNum();

	}

	// 繪製方法
	void paintSelf(Graphics g) {
		g.setColor(new Color(128,138,135)); // 框線

		// 畫直線
		for (int i = 0; i <= GameUtil.MAP_W; i++) {
			g.drawLine(GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH, 3 * GameUtil.OFFSET,
					GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
					3 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH);
		}
		// 畫橫線
		for (int i = 0; i <= GameUtil.MAP_H; i++) {
			g.drawLine(GameUtil.OFFSET, 3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
					GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH,
					3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH);
		}
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				if (GameUtil.DATA_BOTTOM[i][j] == -1) {// 判斷如果databottom=地雷(-1)
					g.drawImage(GameUtil.bomb, GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子X軸
							GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子Y軸
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片寬(格子是50*50所以圖片48*48可露出隔線)
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片高
							null);
				}
				// 數字
				if (GameUtil.DATA_BOTTOM[i][j] >= 0) {
					g.drawImage(GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
							GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1, // 數字放置位子X軸
							GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1, // 數字放置位子Y軸
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片寬(格子是50*50所以圖片48*48可露出隔線)
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片高
							null);
				}
			}
		}
		
		// 繪製剩餘地雷數.計時器
		GameUtil.drawWord(g,""+(GameUtil.Bomb_Max-GameUtil.FLAG_NUM), 
				GameUtil.OFFSET, 
				2*GameUtil.OFFSET,30,Color.red);
		GameUtil.drawWord(g,""+(GameUtil.END_TIME-GameUtil.START_TIME)/1000, 
				GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W-1), 
				2*GameUtil.OFFSET,30,Color.red);
		
		switch (GameUtil.state) {
		case 0:
			GameUtil.END_TIME=System.currentTimeMillis();
			g.drawImage(GameUtil.face, GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2), GameUtil.OFFSET,
					null);
			break;
		case 1:
			g.drawImage(GameUtil.win, GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2), GameUtil.OFFSET,
					null);
			break;
		case 2:
			g.drawImage(GameUtil.over, GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W / 2), GameUtil.OFFSET,
					null);
			break;
		default:
		}
	}
}
