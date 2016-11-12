package com.finance.dennis.loader;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class StockDayDataReader {

	public static void main(String[] args) {
		List<StockDayDataRecord> records = StockDayDataFileParser.parse(
				"E:\\new_zx_allin1\\vipdoc\\sz\\lday\\sz002463.day");

		if (records == null) {
			System.out.println("Error happened.");
			return;
		}
		
		int count = records.size();
		for (int i = 0; i < count; i++ ) {
			System.out.printf("%4d: %s\n", i, records.get(i).toString());
		}
	}
}

class StockDayDataFileParser {

	public static final int BYTES_PER_RECORD = 32; // 32 byte per record
	public static final int BYTES_PERF_INT = 4; // 4 bytes per integer

	public static List<StockDayDataRecord> parse(String fileName) {
		List<StockDayDataRecord> result = null;
		FileInputStream input = null;

		File srcFile = new File(fileName);
		long byteCount = srcFile.length();
		int recordCount = (int) byteCount
				/ StockDayDataFileParser.BYTES_PER_RECORD;

		byte byteForInteger[] = new byte[StockDayDataFileParser.BYTES_PERF_INT];
		ByteBuffer buffer = ByteBuffer.wrap(byteForInteger);
		buffer.order(ByteOrder.LITTLE_ENDIAN); // Pay attention to the order

		try {
			input = new FileInputStream(srcFile);
			result = new ArrayList<StockDayDataRecord>(recordCount);
			int date = 0;
			float open = 0;
			float high = 0;
			float low = 0;
			float close = 0;
			float amount = 0;
			int volumn = 0;
			int reserved = 0;
			for (int i = 0; i < recordCount; i++) {
				buffer.clear();
				input.read(byteForInteger);
				date = buffer.getInt();

				buffer.clear();
				input.read(byteForInteger);
				open = buffer.getInt() / 100f;

				buffer.clear();
				input.read(byteForInteger);
				high = buffer.getInt() / 100f;

				buffer.clear();
				input.read(byteForInteger);
				low = buffer.getInt() / 100f;

				buffer.clear();
				input.read(byteForInteger);
				close = buffer.getInt() / 100f;

				buffer.clear();
				input.read(byteForInteger);
				amount = buffer.getFloat();

				buffer.clear();
				input.read(byteForInteger);
				volumn = buffer.getInt();

				buffer.clear();
				input.read(byteForInteger);
				reserved = buffer.getInt();

				result.add(new StockDayDataRecord(date, open, high, low, close,
						amount, volumn, reserved));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (Exception e) {
			}
		}

		return result;
	}
}

class StockDayDataRecord {
	private int date;
	private float open;
	private float high;
	private float low;
	private float close;
	private float amount;
	private int volumn;
	private int reserved;

	public StockDayDataRecord(int date, float open, float high, float low,
			float close, float amount, int volumn, int reserved) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.amount = amount;
		this.volumn = volumn;
		this.reserved = reserved;
	}

	public int getDate() {
		return date;
	}

	public float getOpen() {
		return open;
	}

	public float getHigh() {
		return high;
	}

	public float getLow() {
		return low;
	}

	public float getClose() {
		return close;
	}

	public float getAmount() {
		return amount;
	}

	public int getVolumn() {
		return volumn;
	}

	public int getReserved() {
		return reserved;
	}

	@Override
	public String toString() {
		return String
				.format("date=%8d, open=%f, high=%f, low=%f, close=%f, amount=%f, volumn=%d, reserved=%d",
						date, open, high, low, close, amount, volumn, reserved);
	}
}