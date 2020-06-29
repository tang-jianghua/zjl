package com.mfangsoft.zhuangjialong.app.brand.model;

import java.util.List;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月12日
* 
*/

public class RegionWithLetter {
        private String letter;
        private List<RegionModel> regionModels;
		/**
		 * @return the letter
		 */
		public String getLetter() {
			return letter;
		}
		/**
		 * @param letter the letter to set
		 */
		public void setLetter(String letter) {
			this.letter = letter;
		}
		/**
		 * @return the regionModels
		 */
		public List<RegionModel> getRegionModels() {
			return regionModels;
		}
		/**
		 * @param regionModels the regionModels to set
		 */
		public void setRegionModels(List<RegionModel> regionModels) {
			this.regionModels = regionModels;
		}
        
        
        
}
