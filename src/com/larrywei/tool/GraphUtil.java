package com.larrywei.tool;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.NodeList;

import com.sun.imageio.plugins.png.PNGMetadata;
/**
 * @description read and write png metadata
 * @author Larry.Wei(weilaiman@gmail.com)
 * @createTime 18/08/2016
 * */
public class GraphUtil {
	public static void writeCustomPngMetadata(String imgFilePath, String key, String value) throws Exception {
		BufferedImage buffImg = ImageIO.read(new File(imgFilePath));
		ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

	    ImageWriteParam writeParam = writer.getDefaultWriteParam();
	    ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);

	    //adding metadata
	    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
	    
	    IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
	    textEntry.setAttribute("keyword", key);
	    
	    //node did not support utf-8,so here we need convert normal string to ascii code and store
	    textEntry.setAttribute("value", Utility.normalString2ASCIIString(value));

	    IIOMetadataNode text = new IIOMetadataNode("tEXt");
	    text.appendChild(textEntry);

	    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
	    root.appendChild(text);

	    metadata.mergeTree("javax_imageio_png_1.0", root);
		//javax_imageio_1.0
		//javax_imageio_png_1.0
	    //writing the data
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
	    writer.setOutput(stream);
	    writer.write(metadata, new IIOImage(buffImg, null, metadata), writeParam);
	    stream.close();

	    FileOutputStream fStream = new FileOutputStream(new File(imgFilePath));
	    fStream.write(baos.toByteArray());
	    fStream.flush();
	    fStream.close();
	}
		
	public static String readCustomPngMetadata(String imgFilePath, String key) throws IOException{
	    FileInputStream inputStream = new FileInputStream(new File(imgFilePath));
	    ImageInputStream imgInputStream = ImageIO.createImageInputStream(inputStream);
		ImageReader imageReader = ImageIO.getImageReadersByFormatName("png").next();

		imageReader.setInput(imgInputStream);
//	    imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageData)), true);

	    // read metadata of image
	    IIOMetadata metadata = imageReader.getImageMetadata(0);

	    //this cast helps getting the contents
	    PNGMetadata pngmeta = (PNGMetadata) metadata; 
	    if (pngmeta != null) {
	    	String[] fms = pngmeta.getMetadataFormatNames();
	    	for (int i = 0; i < fms.length; i++) {
				System.out.println(fms[i]);
			}
//	    	org.w3c.dom.Node n = pngmeta.getAsTree("javax_imageio_png_1.0");
//	    	IIOMetadataNode n = pngmeta.getStandardTextNode();
//	    	if (n != null) {
//	    		NodeList childNodes = n.getChildNodes();
//			    for (int i = 0; i < childNodes.getLength(); i++) {
//			        org.w3c.dom.Node node = childNodes.item(i);
//			        System.out.println(node.getNodeName() + " : " + node.getNodeValue());
//			    }
//			}
	    	
		    NodeList childNodes = pngmeta.getStandardTextNode().getChildNodes();
		  
		    for (int i = 0; i < childNodes.getLength(); i++) {
		        org.w3c.dom.Node node = childNodes.item(i);
		        String keyword = node.getAttributes().getNamedItem("keyword").getNodeValue();
		        String value = node.getAttributes().getNamedItem("value").getNodeValue();
		        if(key.equals(keyword)){
		    	    //node did not support utf-8,so here we need convert ascii code to normal string
		            return Utility.ascii2String(value);
		        }
		    }
		}

	    return null;
	}
}
