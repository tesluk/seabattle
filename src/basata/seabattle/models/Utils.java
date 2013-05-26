package basata.seabattle.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	public static boolean containsCyrillics(String str){
		String cyrilic = "ёъыїйцукенгшщзхфівапролджєэячсмитьбю";
		for(int i = 0; i < str.length(); i++){
			if (cyrilic.contains(str.substring(i , i + 1)))
				return true;
		}
		return false;
		
	}
	
	public static boolean isAllowedStr(String str) {
		
		String regex ="(\\w+)";
		Pattern p2 = Pattern.compile(regex);
		Matcher m2 = p2.matcher(str);
		return m2.matches();

		
	}
	
	
	public static void main(String[] args) {
		//System.out.println(Utils.containsCyrillics("test1c"));
		//System.out.println(Utils.containsCyrillics("hello мир"));
	
		System.out.println(Utils.isAllowedStr("1xxx22dd_wwadW"));
		System.out.println(Utils.isAllowedStr("xxxАНТОХАxxx"));
		System.out.println(Utils.isAllowedStr("xxx!ANT0N!xxx"));
		
	}
	
}
