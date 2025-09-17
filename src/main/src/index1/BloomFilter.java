package index1;

import general_tools.LSH;

import java.util.ArrayList;

public class BloomFilter {

	private ArrayList<Integer> c = new ArrayList<>();
	private int m;
	private LSH lsh;



	
	//initialize bloom filter
	public BloomFilter(double p, int n, LSH lsh) {
		this.m = (int) Math.ceil((n * Math.log(p)) / Math.log(1 / Math.pow(2, Math.log(2))));
		for (int i=0; i<m; i++) {
			c.add(i,0);
		}
		System.out.printf("Bloom filter inicializado\n");
		System.out.printf("Tamanho ideal do index1.BloomFilter calculado: %d\n", m);
		this.lsh = lsh;
	}
	public BloomFilter(int m, LSH lsh) {
		this.m = m;
		for (int i=0; i<m; i++) {
			c.add(i,0);
		}

		this.lsh = lsh;
	}

	//general_tools.LSH
	 public int[] hash(double[] vector){
		int[] res = lsh.h2(vector);

		int[] pos = new int[res.length];
		for (int i=0;i<res.length;i++){
			pos[i] = res[i] % this.m;
		}

		return pos;
	 }

	public static double[] convertIntArrayToDoubleArray(int[] intArray) {
		// 创建一个新的double数组，长度与输入的int数组相同
		double[] doubleArray = new double[intArray.length];

		// 遍历int数组，将每个元素转换为double并存储到新的数组中
		for (int i = 0; i < intArray.length; i++) {
			doubleArray[i] = (double) intArray[i];
		}

		// 返回转换后的double数组
		return doubleArray;
	}


	
	//insert items in the filter
	public void insert(int[] vector) {

		int[] key = hash(convertIntArrayToDoubleArray(vector));
		//normal bloom filter

		for (int i=0;i<key.length;i++){
			this.c.set(key[i],1);
		}

		
	}




	//check if element is member of the filter
	public boolean isMember(int[] vector) {
		int[] key = hash(convertIntArrayToDoubleArray(vector));

		for (int i=0;i<key.length;i++){
			if (this.c.get(key[i])==0) {
				return false;
			}
		}

		return true;
	};
	
	//print items
	public void listItems() {
		for (int k=0; k<c.size(); k++) {
			System.out.printf( "%d: %d \n", k, this.c.get(k));
		}
	}


	public int getSize() {
		return m;
	}
	public ArrayList<Integer> getC() {
		return c;
	}

	public LSH getLsh(){
		return lsh;
	}
	
}
