package gameEngine.ramses.assetsManagement;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Assets {
	
	
	private static Map<String,Image> _imageList = new HashMap<String, Image>();
	private static Map<String,ArrayList<Image>> _spriteSheetList = new HashMap<String,ArrayList<Image>>();
	
	
	public static void load() throws IOException{
		createImage("Test","tetris_tutorial.png");
		//createSpriteSheet("Test2","tetris_tutorial.png",new FrameInfo[]{new FrameInfo(0,0,25,25), new FrameInfo(25,0,25,25),new FrameInfo(50,0,50,25)});
		createSpriteSheetWithXml("Test2","walk.png");
	}
	
	public static Image getImage(String name){
		return _imageList.get(name);
	}
	
	public static ArrayList<Image> getSpriteSheet(String name){
		return _spriteSheetList.get(name);
	}
	
	private static void createImage(String name, String path) throws IOException{
		BufferedImage loadedImage = ImageIO.read(Assets.class.getResource("/"+path));
		createImage(name,path,0,0,loadedImage.getWidth(),loadedImage.getHeight());
		
	}
	
	private static void createImage(String name, String path,int cutX,int cutY,int cutWidth,int cutHeight) throws IOException{
		BufferedImage loadedImage = ImageIO.read(Assets.class.getResource("/"+path));
		Image image = loadedImage.getSubimage(cutX, cutY, cutWidth, cutHeight);
		_imageList.put(name,image);
	}
	
	private static void createSpriteSheetWithXml(String name,String pathImage){
		try{
			//Assets.class.getResource("/xmlData/"+pathImage).getFile().replaceFirst("[.][^.]+$", ".xml")
			String pathStuf = "/xmlData/" + pathImage.replaceFirst("[.][^.]+$", ".xml");
			File xmlFile = new File(Assets.class.getResource(pathStuf).toURI());
			System.out.println(xmlFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList allFrames = doc.getElementsByTagName("frame");
			
			FrameInfo currentFrameInfo;
			
			FrameInfo[] allFrameInfos = new FrameInfo[allFrames.getLength()];
			
			
			for(int i = 0; i < allFrames.getLength(); i++){
				
				Node frame = allFrames.item(i);
				
				if(frame.getNodeType() == Node.ELEMENT_NODE){
				
					Element fInfoData = (Element)frame;
					
					int xCut = Integer.parseInt(fInfoData.getElementsByTagName("xPos").item(0).getTextContent());
					int yCut = Integer.parseInt(fInfoData.getElementsByTagName("yPos").item(0).getTextContent());;
					int widthCut = Integer.parseInt(fInfoData.getElementsByTagName("width").item(0).getTextContent());;
					int heightCut = Integer.parseInt(fInfoData.getElementsByTagName("height").item(0).getTextContent());;
				
					currentFrameInfo = new FrameInfo(xCut,yCut,widthCut,heightCut);
					
					allFrameInfos[i] = currentFrameInfo;
				}
			}
			
			createSpriteSheet(name,pathImage,allFrameInfos);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void createSpriteSheet(String name,String path,FrameInfo[] allInfoFrames) throws IOException{
		BufferedImage loadedImage = ImageIO.read(Assets.class.getResource("/"+path));
		
		ArrayList<Image> spriteSheet = new ArrayList<Image>();
		Image currentImage;
		
		for(int i = 0; i < allInfoFrames.length; i++){
			FrameInfo cFInfo = allInfoFrames[i];
			currentImage = loadedImage.getSubimage(cFInfo.x(), cFInfo.y(), cFInfo.width(), cFInfo.height());
			spriteSheet.add(currentImage);
		}
		
		_spriteSheetList.put(name, spriteSheet);
	}
	
	
	
}
