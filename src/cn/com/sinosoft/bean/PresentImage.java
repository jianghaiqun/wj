package cn.com.sinosoft.bean;


/**
 * Bean类 - 商品图片
 * ============================================================================
 *  
 *  
 *  
 *  
 *  
 * KEY: SHOPXXD7D506B0E8767709FDAC4269652F8E43
 * ============================================================================
 */

public class PresentImage {
	
	public static final String PRESENT_IMAGE_FILE_EXTENSION = "jpg";// 商品图片文件名扩展名
	public static final String BIG_PRESENT_IMAGE_FILE_NAME_SUFFIX = "_big";// 商品图片（大）文件名后缀
	public static final String SMALL_PRESENT_IMAGE_FILE_NAME_SUFFIX = "_small";// 商品图片（小）文件名后缀
	public static final String THUMBNAIL_PRESENT_IMAGE_FILE_NAME_SUFFIX = "_thumbnail";// 商品缩略图文件名后缀
	
	private String id;// ID
	private String sourcePresentImagePath;// 商品图片（原）路径
	private String bigPresentImagePath;// 商品图片（大）路径
	private String smallPresentImagePath;// 商品图片（小）路径
	private String thumbnailPresentImagePath;// 商品图片（缩略图）路径
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSourcePresentImagePath() {
		return sourcePresentImagePath;
	}
	
	public void setSourcePresentImagePath(String sourcePresentImagePath) {
		this.sourcePresentImagePath = sourcePresentImagePath;
	}

	public String getBigPresentImagePath() {
		return bigPresentImagePath;
	}
	
	public void setBigPresentImagePath(String bigPresentImagePath) {
		this.bigPresentImagePath = bigPresentImagePath;
	}
	
	public String getSmallPresentImagePath() {
		return smallPresentImagePath;
	}
	
	public void setSmallPresentImagePath(String smallPresentImagePath) {
		this.smallPresentImagePath = smallPresentImagePath;
	}
	
	public String getThumbnailPresentImagePath() {
		return thumbnailPresentImagePath;
	}
	
	public void setThumbnailPresentImagePath(String thumbnailPresentImagePath) {
		this.thumbnailPresentImagePath = thumbnailPresentImagePath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PresentImage other = (PresentImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
