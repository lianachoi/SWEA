import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 
 * 1. 입력받은 수 중에 최댓값 찾기
 * 2. 큐에 최댓값 좌표 저장
 * 3. 하나 꺼내고 상하좌우 dfs 돌리기. - 지형깎기 전 최대 길이
 * 4. 돌리다가 막히면 해당 방향으로 최대 -K (현재값-1 할 수 있는) 해서 갈 수 있는지 확인
 * 5. 갈 수 있으면 플래그 0으로 해서 끝까지 진행하고 나오면 플래그 1
*/
class Peak{
	int y, x;
	Peak(int y, int x){
		this.y = y;
		this.x = x;
	}
}

public class SWEA1949 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int [] y = {-1, 0, 1, 0};
	static int [] x = {0, 1, 0, -1};

	static int N, K;
	static int [][] map;
	static int [][] visit;
	
	static boolean k_flag;
	
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			k_flag = false;
			int highest = 0;
			int longest = 0;
			Queue<Peak> peaks = new LinkedList<Peak>();
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new int[N][N];
			for (int i = 0; i < map.length; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < map.length; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					// 2. 큐에 최댓값 좌표 저장
					if (highest == map[i][j]) {
						peaks.add(new Peak(i, j));
					}
					// 1. 입력받은 수 중에 최댓값 찾기
					if (highest < map[i][j]) {
						highest = map[i][j];
						// 2. 큐에 최댓값 좌표 저장
						peaks.clear();
						peaks.add(new Peak(i, j));
					}
				}
			}
			// 3. 하나 꺼내고 상하좌우 dfs 돌리기. - 지형깎기 전 최대 길이
			while (!peaks.isEmpty()) {
				visit = new int[N][N];
				Peak now = peaks.poll();
				visit[now.y][now.x] = 1;
				int len = dfs(now);
				if (longest < len) {
					longest = len;
				}
			}
			bw.write("#"+t+" "+longest+"\n");
		}
		br.close();
		bw.close();
	}
	
	private static int dfs(Peak now) { 
		int len = visit[now.y][now.x];
		for (int i = 0; i < x.length; i++) {
			Peak next = new Peak(now.y + y[i], now.x + x[i]);
			
			if (next.y >= 0 && next.x >= 0 
					&& next.y < N && next.x < N) {
				if (visit[next.y][next.x] > 0) {
					continue;
				}
				
				if (map[now.y][now.x] > map[next.y][next.x]) {
					visit[next.y][next.x] = visit[now.y][now.x] + 1;
					
					int now_len = dfs(next);
					
					if (len < now_len) {
						len = now_len;
					}
					
					visit[next.y][next.x] = 0;
				}else if(!k_flag && map[next.y][next.x] - map[now.y][now.x] + 1 <= K) {
					// 4. 돌리다가 막히면 해당 방향으로 최대 -K (현재값-1 할 수 있는) 해서 갈 수 있는지 확인
					// 5. 갈 수 있으면 플래그 0으로 해서 끝까지 진행하고 나오면 플래그 1
					k_flag = true;
					int temp = map[next.y][next.x];
					map[next.y][next.x] = map[now.y][now.x] - 1; 
					visit[next.y][next.x] = visit[now.y][now.x] + 1;
					
					int now_len = dfs(next);
					
					if (len < now_len) {
						len = now_len;
					}
					
					map[next.y][next.x] = temp;
					k_flag = false;
					visit[next.y][next.x] = 0;
				}
			}
		}
		return len;
	}
}
