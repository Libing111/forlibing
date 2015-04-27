/* <p>文件名称: GMailEngine.java </p>
 * <p>文件描述: 无</p>
 * <p>版权所有: 版权所有(C)2012-2022</p>
 * <p>公    司:   沈阳工大普日软件技术有限公司</p>
 * <p>内容摘要: 无</p>
 * <p>其他说明: 无</p>
 * <p>创建日期：2014-1-13</p>
 * <p>完成日期：2014-1-13</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：2014-1-13下午1:16:35
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author	zhanghuafeng
 */
package com.proper.uip.cas.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class GMailEngine extends ListImageCaptchaEngine{
	protected void buildInitialFactories(){
	    int minWordLength = 4;
	    int maxWordLength = 4;
	    int fontSize = 28;
	    int imageWidth = 190;
	    int imageHeight = 45;

	    WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));

	    TextPaster randomPaster = new DecoratedRandomTextPaster(Integer.valueOf(minWordLength), Integer.valueOf(maxWordLength), 
	      new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), 
	      new Color(23, 67, 172) }), new TextDecorator[0]);
	    BackgroundGenerator background = new UniColorBackgroundGenerator(Integer.valueOf(imageWidth), Integer.valueOf(imageHeight), Color.white);
	    FontGenerator font = new RandomFontGenerator(Integer.valueOf(fontSize), Integer.valueOf(fontSize), new Font[] { 
	      new Font("nyala", 1, fontSize), new Font("Bell MT", 0, fontSize), 
	      new Font("Credit valley", 1, fontSize) });

	    ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[0]);
	    ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[0]);
	    ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[0]);

	    WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, 
	      postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	  }
	}