package io;

import java.io.IOException;
import java.io.StringReader;

/**
 * TODO	 从内存中输入
 * @date 2017/4/10
 * @author Cui.GaoWei
 */
public class MemoryInput {

	public static void main(String[] args) throws IOException{
		StringReader in = new StringReader(BufferedInputFile.read("E:\\@Mine\\@github\\notes\\Basic\\io\\BufferedInputFile.java"));
		int c;
		while ((c = in.read()) != -1) {
			System.out.print((char) c);
		}
	}
}
