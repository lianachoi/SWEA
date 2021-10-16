import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

/* 벌꿀채취
 * 1. 전체 순회 하면서 첫 번째 일꾼의 꿀 채취 경우 찾음
 * 2. 조합을 통해서 최대 채취값을 찾는다.
 * 3. 첫 번째 일꾼의 채취지역 이후에서 두 번째 일꾼이 선택할 수 있는 모든 선택확인
 * 4. 조합을 통해서 최대 채취값을 찾은 후 첫번째 일꾼과의 최댓값을 저장한다.
 * */
public class SWEA2115 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[][] map;
	static int M;
	static int C;
	
	static int[] limitedMax;
	static int max;

	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			max = 0;
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());

			map = new int [N][N];
			
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine());
				for (int x = 0; x < N; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			
			for (int y1 = 0; y1 < N; y1++) {
				for (int x1 = 0; x1 <= N-M; x1++) {
					int[] workArea1 = Arrays.copyOfRange(map[y1], x1, x1+M);
					int[] workArea2;
					int[] visit1 = new int[M];
					int[] visit2 = new int[M];

					limitedMax = new int [3];
					
					for (int m = 1; m <= M; m++) {
						Cal(workArea1, visit1, 0, m, 1);
					}
					
					for (int y2 = y1; y2 < N; y2++) {
						int nextX = 0;
						if (y2 == y1) {
							nextX = x1+M;
						}
						for (int x2 = nextX; x2 <= N-M; x2++) {
							workArea2 = Arrays.copyOfRange(map[y2], x2, x2+M);
							for (int m = 1; m <= M; m++) {
								Cal(workArea2, visit2, 0, m, 2);
							}
						}
					}
					
				}
			}
			bw.write("#"+t+" "+max+"\n");
			
		}
		br.close();
		bw.close();

	}


	private static void Cal(int[] arr, int[] visit, int start, int r, int worker) {
		if (r == 0) {
			findLimitedMax(arr, visit, worker);
		}else {
			for (int i = start; i < arr.length; i++) {
				visit[i] = 1;
				Cal(arr, visit, i+1, r-1, worker);
				visit[i] = 0;
				
			}
		}
	}


	private static void findLimitedMax(int[] arr, int[] visit, int worker) {
		int now = 0;
		int profit = 0;
		for (int i = 0; i < visit.length; i++) {
			if (visit[i] == 1) {
				now += arr[i];
				profit += arr[i] *arr[i];
			}
		}
		if (profit > limitedMax[worker] && now <= C) {
			limitedMax[worker] = profit;
		}
		if (worker == 2) {
			int checkMax = limitedMax[1]+limitedMax[2];
			if (max < checkMax) {
				max = checkMax;
			}
		}
		
	}

}
