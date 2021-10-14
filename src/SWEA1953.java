import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Tunnel{
	int R, C, cnt;
	Tunnel(int R, int C, int cnt){
		this.R = R;
		this.C = C;
		this.cnt = cnt;
	}
}
public class SWEA1953 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException {
		int []y = {-1, 0, 1, 0};
		int []x = {0, 1, 0, -1};
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int R = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());
			
			int [][] map = new int[N][M];
			int [][] visit = new int[N][M];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			Queue<Tunnel> q = new LinkedList<Tunnel>();
			// 1. 맨홀 뚜껑부터 bfs (들어간 상태가 1시간)
			q.add(new Tunnel(R,C,1));
			visit[R][C] = 1;
			
			while(!q.isEmpty()) {
				Tunnel now = q.poll();
				// 현재 시간이 L 이상이면 아래 연산 pass
				if (now.cnt >= L) {
					continue;
				}
				/*
				 * 2. 터널 구조물에 따라 체크, 방문 체크
				 *  1 - 상하좌우 
				 *  2 - 상하 
				 *  3 -    좌우 
				 *  4 - 상   우 
				 *  5 -   하 우 
				 *  6 -   하좌 
				 *  7 - 상  좌
				 *  
					if( 1, 2, 4, 7 ) //상
					if( 1, 2, 5, 6 ) //하
					if( 1, 3, 6, 7 ) //좌
					if( 1, 3, 4, 5 ) //우
				 */
				for (int i = 0; i < y.length; i++) {
					Tunnel next = new Tunnel(now.R+y[i], now.C+x[i], now.cnt+1);
					// 지도 안 범위인지 체크, 방문했는지 체크, 파이프가 있는지 체크
					// 있을 가능성이기 때문에 방문했으면 무조건 카운트 됨
					if ( next.R>=0 && next.C>=0 && next.R<N && next.C<M 
							&& visit[next.R][next.C] == 0
							&& map[next.R][next.C] > 0) {
						int shape = map[now.R][now.C];
						int next_shape = map[next.R][next.C];
						//상 - 파이프가 서로 연결되는가 체크
						if(i == 0 && (shape == 1 || shape == 2 
								|| shape == 4 || shape == 7)) {
							if(Arrays.asList(1,2,5,6).contains(next_shape)) { 
								visit[next.R][next.C] = 1;
								q.add(next);							
							}
						}
						//우
						if(i == 1 && (shape == 1 || shape == 3 
								|| shape == 4 || shape == 5)) {
							if(Arrays.asList(1,3,6,7).contains(next_shape)) {
								visit[next.R][next.C] = 1;
								q.add(next);
							}
						}
						//하
						if(i == 2 && (shape == 1 || shape == 2 
								|| shape == 5 || shape == 6)) {
							if(Arrays.asList(1,2,4,7).contains(next_shape)) {
								visit[next.R][next.C] = 1;
								q.add(next);
							}
						}
						//좌
						if(i == 3 && (shape == 1 || shape == 3 
								|| shape == 6 || shape == 7)) {
							if(Arrays.asList(1,3,4,5).contains(next_shape)) {
								visit[next.R][next.C] = 1;
								q.add(next);
							}
						}
					}
				}
			}
			int cnt = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					cnt += visit[i][j];
				}
			}
			bw.write("#"+t+" "+cnt+"\n");
		}
		
		br.close();
		bw.close();
	}

}
