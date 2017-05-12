package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * TODO 缓冲输入文件
 * @date 2017/4/10
 * @author Cui.GaoWei
 */
public class BufferedInputFile {

	public static void main(String[] args) throws IOException{
		System.out.println(read("E:\\@Mine\\@github\\notes\\Basic\\io\\BufferedInputFile.java"));
	}

	public static String read(String filename) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}
}
