package com.sxt;

// 底層數字類別
public class BottomNum {
	void newNum(){ 
		// 循環整個陣列
		for (int i = 1; i <= GameUtil.MAP_W; i++) {
			for (int j = 1; j <= GameUtil.MAP_H; j++) {
				// 判斷此位置是否是地雷
				if (GameUtil.DATA_BOTTOM[i][j] == -1) {
					// 若是雷，搜尋雷周圍3*3區域
					for (int k = i - 1; k <= i + 1; k++) {
						for (int l = j - 1; l <= j + 1; l++) {
							// 如果區域不是地雷(>=0)，則+1
							if (GameUtil.DATA_BOTTOM[k][l] >= 0) {
								GameUtil.DATA_BOTTOM[k][l]++;
							}
						}
					}
				}
			}
		}

	}
}
