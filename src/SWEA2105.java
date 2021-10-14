import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SWEA2105 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N;
	static int [][]map;
	static int [] Dessert;
	static int startx;
	static int starty;
	static int maxMove;
	static int []y = {-1, 1, 1,-1};
	static int []x = { 1, 1,-1,-1};
	
	static int max;
	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {

			Dessert = new int[101];
			maxMove = 0;
			
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			max = -1;
			for (int i = 1; i < N-1; i++) {
				for (int j = 0; j < N-2; j++) {
					Dessert[map[i][j]] += 1;
					starty = i;
					startx = j;
					Tour(i, j, -1, -1, 0, 0);
					Dessert[map[i][j]] -= 1;
				}
			}
			bw.write("#"+t+" "+max+"\n");
		}
		
		br.close();
		bw.close();
	}
	private static void Tour(int i, int j, int ii, int jj, int num, int dir) {
		for (int k = dir; k <4 ; k++) {
			int nexty = i+y[k];
			int nextx = j+x[k];
			
			if(nexty<0 || nextx<0 || nexty>N-1 || nextx>N-1) continue;
			if(nextx == jj && nexty == ii) continue;

			if(nexty==starty&&nextx==startx) {
				if (max<num+1) {
					max = num+1;
				}
				return ;
			}
			Dessert[map[nexty][nextx]] += 1;
			if(Dessert[map[nexty][nextx]]>1) {
				Dessert[map[nexty][nextx]] -= 1;
				continue;
			}
			Tour(nexty, nextx, i, j, num+1, k);
			Dessert[map[nexty][nextx]] -= 1;
			
		}
	}

}
