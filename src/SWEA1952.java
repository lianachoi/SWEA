import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
		1	2	3		4		5		6		7
1일		0	0	[20]	100		[70]	120		0
1달		0	0	40		[60]	100		[110]	0
3달(1)	0	0	100		120		160		170		0
3달(2)	0	0	100		100		120		160		0
3달(3)	0	0	100		100		100		120		0
1년		0	0	300		300		300		300		0
*/
public class SWEA1952 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			int [] prices = new int[4];
			int [] plans = new int[12];
			int [][] table = new int[12][6];
			StringTokenizer pr = new StringTokenizer(br.readLine());
			StringTokenizer pl = new StringTokenizer(br.readLine());
			for (int i = 0; i < plans.length; i++) {
				if (i<prices.length) {
					prices[i] = Integer.parseInt(pr.nextToken());
				}
				plans[i] = Integer.parseInt(pl.nextToken());
			}
			
				table[0][0] = prices[0] * plans[0];
				table[0][1] = prices[1];
				table[0][2] = prices[2];
				table[0][3] = prices[2];
				table[0][4] = prices[2];
				table[0][5] = prices[3];				
			
			
			for (int i = 1; i < plans.length; i++) {
				int [] last_month = table[i-1].clone();
				Arrays.sort(last_month);
					table[i][0] = last_month[0] + prices[0] * plans[i];
					table[i][1] = last_month[0] + prices[1];
					table[i][2] = last_month[0] + prices[2];
					table[i][3] = table[i-1][2];
					table[i][4] = table[i-1][3];
					table[i][5] = prices[3];				
			}
			int [] december = table[11].clone();
			Arrays.sort(december);
			
			bw.write("#"+t+" "+december[0]+"\n");
		}
		br.close();
		bw.close();
	}

}
