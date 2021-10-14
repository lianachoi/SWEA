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
			// 1. ��Ȧ �Ѳ����� bfs (�� ���°� 1�ð�)
			q.add(new Tunnel(R,C,1));
			visit[R][C] = 1;
			
			while(!q.isEmpty()) {
				Tunnel now = q.poll();
				// ���� �ð��� L �̻��̸� �Ʒ� ���� pass
				if (now.cnt >= L) {
					continue;
				}
				/*
				 * 2. �ͳ� �������� ���� üũ, �湮 üũ
				 *  1 - �����¿� 
				 *  2 - ���� 
				 *  3 -    �¿� 
				 *  4 - ��   �� 
				 *  5 -   �� �� 
				 *  6 -   ���� 
				 *  7 - ��  ��
				 *  
					if( 1, 2, 4, 7 ) //��
					if( 1, 2, 5, 6 ) //��
					if( 1, 3, 6, 7 ) //��
					if( 1, 3, 4, 5 ) //��
				 */
				for (int i = 0; i < y.length; i++) {
					Tunnel next = new Tunnel(now.R+y[i], now.C+x[i], now.cnt+1);
					// ���� �� �������� üũ, �湮�ߴ��� üũ, �������� �ִ��� üũ
					// ���� ���ɼ��̱� ������ �湮������ ������ ī��Ʈ ��
					if ( next.R>=0 && next.C>=0 && next.R<N && next.C<M 
							&& visit[next.R][next.C] == 0
							&& map[next.R][next.C] > 0) {
						int shape = map[now.R][now.C];
						int next_shape = map[next.R][next.C];
						//�� - �������� ���� ����Ǵ°� üũ
						if(i == 0 && (shape == 1 || shape == 2 
								|| shape == 4 || shape == 7)) {
							if(Arrays.asList(1,2,5,6).contains(next_shape)) { 
								visit[next.R][next.C] = 1;
								q.add(next);							
							}
						}
						//��
						if(i == 1 && (shape == 1 || shape == 3 
								|| shape == 4 || shape == 5)) {
							if(Arrays.asList(1,3,6,7).contains(next_shape)) {
								visit[next.R][next.C] = 1;
								q.add(next);
							}
						}
						//��
						if(i == 2 && (shape == 1 || shape == 2 
								|| shape == 5 || shape == 6)) {
							if(Arrays.asList(1,2,4,7).contains(next_shape)) {
								visit[next.R][next.C] = 1;
								q.add(next);
							}
						}
						//��
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
