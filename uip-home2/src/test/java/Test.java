import java.io.File;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File("F:\\doc\\tupian\\图标");
		File diro = new File("F:\\doc\\tupian\\图标2");
		diro.mkdirs();
		File[] list = dir.listFiles();
		Hanyu hanyu = new Hanyu();
		for (int i = 0; i < list.length; i++) {
			String name=list[i].getName().replaceAll("\\.png", "");
			System.out.print("\"" + name
					+ "\"" + ",");
			String strPinyin = hanyu.getStringPinYin(name);
			list[i].renameTo(new File(diro, strPinyin+".png"));
		}

	}

}
