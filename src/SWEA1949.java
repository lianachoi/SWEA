import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 
 * 1. �Է¹��� �� �߿� �ִ� ã��
 * 2. ť�� �ִ� ��ǥ ����
 * 3. �ϳ� ������ �����¿� dfs ������. - ������� �� �ִ� ����
 * 4. �����ٰ� ������ �ش� �������� �ִ� -K (���簪-1 �� �� �ִ�) �ؼ� �� �� �ִ��� Ȯ��
 * 5. �� �� ������ �÷��� 0���� �ؼ� ������ �����ϰ� ������ �÷��� 1
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
					// 2. ť�� �ִ� ��ǥ ����
					if (highest == map[i][j]) {
						peaks.add(new Peak(i, j));
					}
					// 1. �Է¹��� �� �߿� �ִ� ã��
					if (highest < map[i][j]) {
						highest = map[i][j];
						// 2. ť�� �ִ� ��ǥ ����
						peaks.clear();
						peaks.add(new Peak(i, j));
					}
				}
			}
			// 3. �ϳ� ������ �����¿� dfs ������. - ������� �� �ִ� ����
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
					// 4. �����ٰ� ������ �ش� �������� �ִ� -K (���簪-1 �� �� �ִ�) �ؼ� �� �� �ִ��� Ȯ��
					// 5. �� �� ������ �÷��� 0���� �ؼ� ������ �����ϰ� ������ �÷��� 1
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
