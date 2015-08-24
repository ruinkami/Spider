import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviantArtSpider {

	public static void main(String[] args) {
		try{
			System.out.println("input DeviantArt url (e.g. http://sheron1030.deviantart.com/gallery/):");
			Scanner sc = new Scanner(System.in);
			String content;
			String urlString = sc.nextLine();
			URL url;
			int currentSize=0, totalSize=0, count=0;
			
			while(currentSize!=0 || totalSize==0){
				if(count ==0){
					url = new URL(urlString);
				}else{
					url = new URL(urlString+"?offset="+count*24);
				}
				
				content = "";
				currentSize=0;
				try {
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					InputStream inputStream = connection.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						content += temp;
					}
					br.close();
					inputStream.close();
					connection.disconnect();
				} catch (Exception e) {
					System.out.println(e);
				}
		
				String searchFullImgReg = "data-super-full-img=\"(.*?)\"";
				Pattern pattern = Pattern.compile(searchFullImgReg);
				Matcher matcher = pattern.matcher(content);
				while (matcher.find()) {
					currentSize++;
					totalSize++;
					System.out.println(matcher.group(1));
				}
				count++;
				System.out.println("url="+url+", currentSize="+currentSize+", totalSize="+totalSize+", count="+count);
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}