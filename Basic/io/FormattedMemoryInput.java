package io;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * TODO 格式化的内存输入
 * @date 2017/4/10
 * @author Cui.GaoWei
 */
public class FormattedMemoryInput {
	public static void main(String[] args) throws IOException{
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(BufferedInputFile.read("E:\\@Mine\\@github\\notes\\Basic\\io\\BufferedInputFile.java").getBytes()));
			while (true) {
				System.out.print((char) in.readByte());
			}
		} catch (EOFException e) {
			e.printStackTrace();
		}

	}
}
