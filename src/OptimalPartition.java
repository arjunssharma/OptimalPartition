import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class OptimalPartition {
	
	private final static Logger LOGGER = Logger.getLogger(OptimalPartition.class.getName()); 
	
	public static void main(String[] args) throws java.lang.Exception {
		if(args.length != 2) {
			LOGGER.info("Invalid input parameters");
			System.exit(1);
		}
		
		//final String dir = System.getProperty("user.dir");
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String line = null;
		List<Integer> list = new ArrayList<>(); //read all values from file
		while((line = br.readLine()) != null)
			list.add(Integer.parseInt(line));
		
		br.close();
		
		int numberOfBins = list.remove(0);
		Integer input[] = list.toArray(new Integer[list.size()]);	
		Arrays.sort(input);
		List<Integer> lst = new ArrayList<>();

		int current = Integer.MIN_VALUE;
		int count = 0;
		for(int i = 0; i < input.length; i++) {
			if (input[i] != current) {
				lst.add(count);
				current = input[i];
				count = 1;
			} else {
				count++;
			}
		}
		
		lst.add(count);
		lst.remove(0);

		int maxSize = findMaxBinSize(lst, numberOfBins);

		int bin_sum = 0;
		int index = 0;
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
		for (int i : lst) {
			if (bin_sum + i > maxSize) {
				bin_sum = i;
				bw.newLine();
			}
			else
				bin_sum += i;
			int t = i;
			while (t-- > 0)
				bw.write(input[index++] + " ");
			
		}
		
		bw.close();
	}

	static int findMaxBinSize(List<Integer> list, int k) {
		long high = 0, low = 0;
		for (int i : list) {
			high += i;
			low = Math.max(i, low);
		}

		while(low < high) {
			long mid = (low + high) / 2;
			long bin_sum = 0;
			int bins = 1;
			for(int i : list) {
				if (bin_sum + i > mid) {
					bin_sum = i;
					bins++;
				} else {
					bin_sum += i;
				}
			}

			if (bins <= k)
				high = mid;
			else
				low = mid + 1;
		}
		return (int)low;
	}
}