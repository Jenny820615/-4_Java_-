package com.sxt;

/*
 * 初始化地雷
 */
public class BottomBomb {
	// 存放座標
	static int[] bombs = new int[GameUtil.Bomb_Max * 2];
	// 地雷座標
	int x, y;
	// 判斷是否放置地雷，true 可放置
	boolean isPlace = true;

	void newBomb() {
		for (int i = 0; i < GameUtil.Bomb_Max * 2; i = i + 2) {
			x = (int) (Math.random() * GameUtil.MAP_W + 1); // 1-12
			y = (int) (Math.random() * GameUtil.MAP_H + 1); // 1-12

			// 判斷地雷是否已存在，才不會重複座標生成地雷導致少於預設地雷數
			for (int j = 0; j < i; j = j + 2) {
				if (x == bombs[j] && y == bombs[j + 1]) {
					i = i - 2;
					isPlace = false;
					break;
				}
			}
			// 將座標放入陣列
			if (isPlace) {
				bombs[i] = x;
				bombs[i + 1] = y;
			}
			isPlace = true;
		}
		for (int i = 0; i < GameUtil.Bomb_Max * 2; i = i + 2) {
			GameUtil.DATA_BOTTOM[bombs[i]][bombs[i + 1]] = -1; // 代表地雷

		}
	}

}
