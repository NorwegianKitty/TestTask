package filesOperation;


public class FileStatistics {

	private int iCount;
	private int iSum;
	private int iMin;
	private int iMax;
	
	private int fCount;
	private float fSum;
	private float fMin;
	private float fMax;
	
	private int sCount;
	private int minStrLength;
	private int maxStrLength;
	
	public void updateInt(int i) {
		iCount++;
		iSum += i;
		
		if (i < iMin || iCount == 1) {
			iMin = i;
		}
		
		if(i > iMax || iCount == 1) {
			iMax = i;
		}		
	}
	
	public void updateFloat(float f) {
		fCount++;
		fSum += f;
		
		if (f < fMin || fCount == 1) {
			fMin = f;
		}
		
		if(f > fMax || fCount == 1) {
			fMax = f;
		}		
	}
	
	public void updateString(String str) {
		sCount++;
		int length = str.length();
		
		if(length < minStrLength || sCount == 1) {
			minStrLength = length;
		}
		
		if(length > minStrLength || sCount == 1) {
			maxStrLength = length;
		}
	}
	
	public int[] shortStat() {
		int[] iArr = {iCount, fCount, sCount};
		return iArr;
	}

	
	public int[] iReturnStat() {
		int[] iArr = {iMin, iMax, iSum};
		return iArr;		
	}
	
	public float[] fReturnStat() {
		float[] fArr = {fMin, fMax, fSum};
		return fArr;		
	}
	
	public int[] strReturnStat() {
		int[] iArr = {minStrLength, maxStrLength};
		return iArr;		
	}
	
}
