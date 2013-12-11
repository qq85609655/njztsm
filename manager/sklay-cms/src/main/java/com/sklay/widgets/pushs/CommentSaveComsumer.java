package com.sklay.widgets.pushs;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.sklay.dao.CommentDao;
import com.sklay.model.Comment;
import com.sklay.core.util.Constants;
import com.sklay.model.ConfigPK;
import com.sklay.service.ConfigService;
import com.sklay.core.io.StaticResourceMappingManager;
import com.sklay.core.io.image.CommonImage;
import com.sklay.core.io.image.ImgConfig;
import com.sklay.core.io.image.ImgSize;
import com.sklay.core.util.JsonUtils;
import com.sklay.core.util.StringUtils;
import com.sklay.event.EventComsumer;

@Component
public class CommentSaveComsumer implements EventComsumer {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CommentSaveComsumer.class);

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private StaticResourceMappingManager resourceMappingManager;

	@Value("#{config['base.url']}")
	private String baseUrl;

	@Autowired
	private ConfigService configService;

	@Override
	public void resolve(String json) throws Exception {
		Comment comment = JsonUtils.toType(json, Comment.class);
		String content = comment.getContent();
		Set<String> imgSrcs = StringUtils.findImgSrcs(content);
		int index = 0;
		if(!org.springframework.util.CollectionUtils.isEmpty(imgSrcs)){
			for (String src : imgSrcs) {
				String fullPathSrc = new String(src);
				//from editor
				if(org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "//")){
					fullPathSrc = "http:"+src;
				}
				else if(org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "/")){
					fullPathSrc =  baseUrl+src;
				}else if (!org.apache.commons.lang.StringUtils.startsWithIgnoreCase(src, "http")){
					fullPathSrc = "http://"+src;
				}

				CommonImage ci = null;
				try{
					ci = new CommonImage(new URI(fullPathSrc));
				} catch (Exception e){
					//ignore
				}
				if(ci!=null){
					Resource parent = resourceMappingManager.getRealPathByBizAndOwner(com.sklay.widgets.pushs.Constants.BIZ_NEWSIMG, String.valueOf(comment.getId()));
					ImgConfig imgConfig = configService.findData(new ConfigPK(com.sklay.widgets.pushs.Constants.BIZ_NEWSTYPE, comment.getOwner()), ImgConfig.class);
					LOGGER.debug("Resource parent exists {} "+parent.exists()) ;
					LOGGER.debug("Resource parent {} "+parent) ; 
					String resouceWithIndex = parent.getFile().getPath()+File.separator+index;
					LOGGER.debug("resouceWithIndex: "+resouceWithIndex) ;
					FileUtils.forceMkdir(new File(resouceWithIndex));
					if(imgConfig == null||(imgConfig.getMaxHeight()<=0||imgConfig.getMaxHeight()<=0)){
						ci.save(resouceWithIndex+File.separator+"orginal.jpg");
					} else{
						ci.resizeWithContainer(imgConfig.getMaxWidth(), imgConfig.getMaxHeight()).save(resouceWithIndex+File.separator+"orginal.jpg");
					}
					if(imgConfig!=null){
						List<ImgSize> imgSizes = imgConfig.getImgSizes();
						if(!CollectionUtils.isEmpty(imgSizes)){
							int sizeIndex=0;
							for (ImgSize imgSize : imgSizes) {
								if(imgSize.getWidth()!=null&&imgSize.getHeight()!=null&&imgSize.getWidth()>0&&imgSize.getHeight()>0){
									ci.resizeWithMaxWidth(imgSize.getWidth()).save(resouceWithIndex+File.separator+'z'+sizeIndex+".jpg");
									ci.cropWithContainer(imgSize.getWidth(), imgSize.getHeight()).save(resouceWithIndex+File.separator+'c'+sizeIndex+".jpg");
									sizeIndex++;
								}
							}
						}
					}
					String httpPath = resourceMappingManager.getHttpPathByBizAndOwner(com.sklay.widgets.pushs.Constants.BIZ_NEWSIMG, String.valueOf(comment.getId()));
					String orginalhttpPath = httpPath+"/"+index+"/orginal.jpg?ver="+comment.getVer();
					content = org.apache.commons.lang.StringUtils.replace(content, src, orginalhttpPath);
				}
				index++;
			}
			comment.setContent(content);
			comment.setImgCount(index);
			commentDao.save(comment);
		}
	}

	@Override
	public boolean accept(String topic) {
		return topic.equals(Constants.EventTopic.COMMENT_SAVE);
	}

	public static void main(String[] args) {
		// <script(\s[^>]*?)?>[\s\S]*?</script>
		// System.out.println(JavaScriptUtils.javaScriptEscape("<div>hello</div>"));;
		String sourceString = "<div >.good{}</div>";
		sourceString = sourceString
				.replaceAll(
						"<style[^>]*?>[\\s\\S]*?</style>|<script[^>]*?>[\\s\\S]*?</script>|<.+?>",
						"");
		// sourceString =
		// sourceString.replaceAll("<style[^>]*?>[\\s\\S]*?</style>","");
		// sourceString =
		// sourceString.replaceAll("<script[^>]*?>[\\s\\S]*?</script>", "");
		System.out.println(sourceString);
		// System.out.println("<style>.good{}</style>".replaceAll("<.+?>", ""));
		// new CommonImage(new
		// URI("http://any123.com/assets/pdt/img/ad-competition.jpg"))
	}
}