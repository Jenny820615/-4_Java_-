package com.sxt;

import java.awt.*;

/*
 * 頂層地圖
 * 繪製頂層組件
 * 判斷
 */
public class MapTop {
	// 格子位置
	int temp_x;
	int temp_y;

	// 重新開始遊戲方法
	void restart() {
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				GameUtil.DATA_TOP[i][j] = 0;
			}
		}

	}

	// 判斷邏輯
	void logic() {
		temp_x = 0;
		temp_y = 0;
		if (GameUtil.MOUSE_X > GameUtil.OFFSET && GameUtil.MOUSE_Y > 3 * GameUtil.OFFSET) {
			temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
			temp_y = (GameUtil.MOUSE_Y - GameUtil.OFFSET * 3) / GameUtil.SQUARE_LENGTH + 1;
		}
		if (temp_x >= 1 && temp_x <= GameUtil.MAP_W && temp_y >= 1 && temp_y <= GameUtil.MAP_H) {
			if (GameUtil.LEFT) {
				// 如果左鍵點擊的位子是被覆蓋的(0)，-1打開
				if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
					GameUtil.DATA_TOP[temp_x][temp_y] = -1;
				}
				
				spaceOpen(temp_x, temp_y);
				GameUtil.LEFT = false;
			}
			if (GameUtil.RIGHT) {
				// 如果右鍵點擊的位子是被覆蓋的(0)，1插旗
				if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
					GameUtil.DATA_TOP[temp_x][temp_y] = 1;
					GameUtil.FLAG_NUM++;
				}
				// 如果右鍵點擊的位子是被插旗，取消插旗恢復覆蓋0
				else if (GameUtil.DATA_TOP[temp_x][temp_y] == 1) {
					GameUtil.DATA_TOP[temp_x][temp_y] = 0;
					GameUtil.FLAG_NUM--;
				}
				// 如果區域被打開，呼叫方法
				else if (GameUtil.DATA_TOP[temp_x][temp_y] == -1) {
					numOpen(temp_x, temp_y);
				}
				GameUtil.RIGHT = false;
			}
		}
		boom();
		victory();
	}

	// 翻開數字
	void numOpen(int x, int y) {
		// 計算旗子數量
		int count = 0;
		if (GameUtil.DATA_BOTTOM[x][y] > 0) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (GameUtil.DATA_TOP[i][j] == 1) {
						count++;
					}
				}
			}
			if (count == GameUtil.DATA_BOTTOM[x][y]) {
				for (int i = x - 1; i <= x + 1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {
						if (GameUtil.DATA_TOP[i][j] != 1) {
							GameUtil.DATA_TOP[i][j] = -1;
						}
						// 必須在地雷區當中
						if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
							spaceOpen(i, j);
						}
					}
				}
			}
		}
	}

	// 失敗判定 true:失敗 false:未失敗
	boolean boom() {
		// 如果旗子數量＝炸彈總數，檢查整個表格。如果頂部是覆蓋，則底部變地雷。這樣會導致地雷數不當的增加。
//		if (GameUtil.FLAG_NUM == GameUtil.Bomb_Max) {
//			for (int i = 1; i <= GameUtil.MAP_W; i++) {
//				for (int j = 1; j <= GameUtil.MAP_H; j++) {
//					if (GameUtil.DATA_TOP[i][j]==0) {
//						GameUtil.DATA_BOTTOM[i][j]=-1;
//					}
//				}
//			}
//		}
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] == -1) {
					GameUtil.state = 2;
					seeBoom();
					return true;
				}
			}
		}
		return false;
	}

	// 失敗後顯示所有地雷
	void seeBoom() {
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				// 如果底層是地雷，且頂層未插旗子，翻開顯示
				if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] != 1) {
					GameUtil.DATA_TOP[i][j] = -1;
				}
				// 如果底層不是地雷，且頂層以插旗子，翻開顯示插錯旗子
				if (GameUtil.DATA_BOTTOM[i][j] != -1 && GameUtil.DATA_TOP[i][j] == 1) {
					GameUtil.DATA_TOP[i][j] = 2;
				}
			}
		}

	}

	// 判斷勝利的方法 true:勝利 false:未勝利
	boolean victory() {
		// 統計插隊旗子的旗子數
		int count = 0;
		//計算旗子數量
		int flag_num = 0;
		for (int i = 1; i <= GameUtil.MAP_W; i++) {  // 當頂部是旗子的時候
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				if (GameUtil.DATA_TOP[i][j] == 1) {
					flag_num++;
				}
			}
		}

		// 當底部是炸彈&&頂部是旗子的時候，count++。這會導致多放的旗子(底不是地雷)所以不會計算到。
//		for (int i = 1; i <= GameUtil.MAP_W; i++) {
//			for (int j = 1; j <= GameUtil.MAP_H; j++) {
//				if (GameUtil.DATA_BOTTOM[i][j]==-1 && GameUtil.DATA_TOP[i][j] == 1) {
//					count++;
//				}  
//			}
//		}
		
		if (flag_num == GameUtil.Bomb_Max) {
//			GameUtil.state = 1;  旗子數=炸彈數，若放錯炸彈不能算勝利，故尚不更改遊戲狀態。
			// count:如果底部是地雷&&頂部是旗子，count才++。
			for (int i = 1; i <= GameUtil.MAP_W; i++) {
				for (int j = 1; j <= GameUtil.MAP_H; j++) {
					if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] ==1) {
						count++;
					}
				}
			}
			// 若插隊旗子的旗子數=地雷總數，代表地雷全部找完，故結束遊戲。
			if (count==GameUtil.Bomb_Max) {
				GameUtil.state = 1;
				for (int i = 1; i <= GameUtil.MAP_W; i++) {
					for (int j = 1; j <= GameUtil.MAP_H; j++) {
						//勝利後將未翻開的翻開
						if (GameUtil.DATA_TOP[i][j] ==0) { 
							GameUtil.DATA_TOP[i][j] = -1;
						}}}
				return true;
				
			}
			
		}
		return false;
	}

	// 打開空格的方法
	void spaceOpen(int x, int y) {
		if (GameUtil.DATA_BOTTOM[x][y] == 0) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					// 如果格子未被打開，再打開格子並且再呼叫自己
					if (GameUtil.DATA_TOP[i][j] != -1) {
						if (GameUtil.DATA_TOP[i][j] == 1) {
							GameUtil.FLAG_NUM--;
						}
						GameUtil.DATA_TOP[i][j] = -1;
						// 必須在地雷區當中
						if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
							spaceOpen(i, j);
						}

					}
				}
			}

		}
	}

	// 繪製方法
	void paintSelf(Graphics g) {
		logic();
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				// 覆蓋
				if (GameUtil.DATA_TOP[i][j] == 0) {// 判斷如果dataTop=0
					g.drawImage(GameUtil.top, GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子X軸
							GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子Y軸
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片寬(格子是50*50所以圖片48*48可露出隔線)
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片高
							null);
				}
				// 插旗
				if (GameUtil.DATA_TOP[i][j] == 1) {// 判斷如果dataTop=0
					g.drawImage(GameUtil.flag, GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子X軸
							GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子Y軸
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片寬(格子是50*50所以圖片48*48可露出隔線)
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片高
							null);
				}
				// 覆蓋
				if (GameUtil.DATA_TOP[i][j] == 2) {// 判斷如果dataTop=0
					g.drawImage(GameUtil.noflag, GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子X軸
							GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1, // 圖片放置位子Y軸
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片寬(格子是50*50所以圖片48*48可露出隔線)
							GameUtil.SQUARE_LENGTH - 2, // 設定圖片高
							null);
				}
			}
		}
	}

}
