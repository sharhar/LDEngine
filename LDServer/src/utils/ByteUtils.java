package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {
	public static final byte[] float2Byte(float[] inData) {
		int j = 0;
		int length = inData.length;
		byte[] outData = new byte[length * 4];
		for (int i = 0; i < length; i++) {
			int data = Float.floatToIntBits(inData[i]);
			outData[j++] = (byte) (data >> 24);
			outData[j++] = (byte) (data >> 16);
			outData[j++] = (byte) (data >> 8);
			outData[j++] = (byte) (data >> 0);
		}
		return outData;
	}
	
	public static final float[] byte2Float(byte[] inData, int num) {
		float[] outData = new float[num];
		byte[] temp = new byte[4];
		for (int i = 0; i < num; i++) {
			System.arraycopy(inData, i*4, temp, 0, 4);
			outData[i] = ByteBuffer.wrap(temp).order(ByteOrder.BIG_ENDIAN).getFloat();
		}
		
		return outData;
	}
}