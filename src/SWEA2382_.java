import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;



public class SWEA2382_ {
	private static class Group{
		int R, C, num, dir, time;
		Group(int R, int C, int num, int dir, int time) {
			this.R = R;
			this.C = C;
			this.num = num;
			this.dir = dir;
			this.time = time;
		}
	}
	private static class CheckDup{
		int dir, max, sum;
		CheckDup(int dir, int max, int sum) {
			this.dir = dir;
			this.max = max;
			this.sum = sum;
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static Queue<Group> q = new LinkedList<Group>();
	static int []Y = {0, -1, 1, 0, 0};
	static int []X = {0, 0, 0, -1, 1};
	static int N;
	static int [][] map1,nowMap,preMap,map2;
	static CheckDup [][] dupMap;
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			map1 = new int[N][N];
			map2 = new int[N][N];
			nowMap = new int[N][N];
			preMap = new int[N][N];
			// 미생물 그룹 표현
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int R = Integer.parseInt(st.nextToken());
				int C = Integer.parseInt(st.nextToken());
				int num = Integer.parseInt(st.nextToken());
				// 상하좌우 1234
				int dir = Integer.parseInt(st.nextToken());
				q.add(new Group(R, C, num, dir, 0));
				nowMap[R][C] = 1;
			}
			// 미생물 수
			long total = 0;
			// 격리 시간만큼 반복
			for (int m = 0; m < M; m++) {
				//preMap = Arrays.(X, t);
				nowMap = new int[N][N];
				total = 0;
				dupMap = new CheckDup[N][N];
				for (int i = 0; i < N; i++) {
					Arrays.fill(dupMap[i], new CheckDup(0,0,0));
				}
				//현재 회차의 미생물 군집 모두 이동
				while(q.peek().time == m) {
					Group now = q.poll();
					// 현재 회차가 중복된 경우 패스
					if (preMap[now.R][now.C] > 1) {
						preMap[now.R][now.C]--;
						continue;
					}
					Group next = new Group(now.R + Y[now.dir], now.C + X[now.dir], 
							now.num, now.dir, now.time+1);
					if (next.R < 0 || next.R >= N || next.C < 0 || next.C >= N )
						continue;
					// 테두리에 왔는지 확인
					boolean bEdge = checkEdge(next.R, next.C, N);
					if (bEdge) {
						next.num = next.num/2;
						switch (next.dir) {
						case 1:
							next.dir = 2;
							break;
						case 2:
							next.dir = 1;
							break;
						case 3:
							next.dir = 4;
							break;
						case 4:
							next.dir = 3;
							break;
						default:
							break;
						}
					}
					if (next.num == 0) {
						continue;
					}
					// 중복체크를 위해서 맵에 중복정보 저장
					if (dupMap[next.R][next.C].dir == 0) {
						dupMap[next.R][next.C].dir = next.dir;
						dupMap[next.R][next.C].sum += next.num;
						dupMap[next.R][next.C].max = next.num;		
					} else {
						if (dupMap[next.R][next.C].max < next.num) {
							dupMap[next.R][next.C].dir = next.dir;
							dupMap[next.R][next.C].sum += next.num;
							dupMap[next.R][next.C].max = next.num;
						}else {
							dupMap[next.R][next.C].sum += next.num;
						}
					}
					nowMap[next.R][next.C]++;				
					total += next.num;
					if (next.time <= M) {
						q.add(new Group(next.R, next.C, dupMap[next.R][next.C].sum,
							dupMap[next.R][next.C].dir, next.time));
					}
				}
			}

			bw.write("#"+t+" "+total+"\n");
		}
		br.close();
		bw.close();
	}

	private static boolean checkEdge(int next_Y, int next_X, int N) {
		if (next_Y == 0 || next_X == 0 || next_Y == N || next_X == N) {
			return true;
		}
		return false;
	}

}
