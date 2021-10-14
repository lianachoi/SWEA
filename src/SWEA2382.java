import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

class Group implements Cloneable{
	int R, C, num, dir, time;
	Group(int R, int C, int num, int dir, int time) {
		this.R = R;
		this.C = C;
		this.num = num;
		this.dir = dir;
		this.time = time;
	}
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
 }

public class SWEA2382 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static Group [][] map,map2,preMap,nowMap;
	static int []Y = {0, -1, 1, 0, 0};
	static int []X = {0, 0, 0, -1, 1};
	static int N;
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			map = new Group[N][N];
			map2 = new Group[N][N];
			nowMap = new Group[N][N];
			preMap= new Group[N][N];
			// 클래스 배열 초기화
			for (int i = 0; i < N; i++) {
				Arrays.fill(map[i], new Group(-1,-1,-1,-1,-1));
				Arrays.fill(map2[i], new Group(-1,-1,-1,-1,-1));
			}
			int init = 0;
			// 미생물 그룹 표현
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int R = Integer.parseInt(st.nextToken());
				int C = Integer.parseInt(st.nextToken());
				int num = Integer.parseInt(st.nextToken());
				// 상하좌우 1234
				int dir = Integer.parseInt(st.nextToken());
				map[R][C] = new Group(R, C, num, dir, 0);
				init += num;
			}
			// 미생물 수
			int total = 0;
			// 격리 시간만큼 반복
			for (int m = 0; m < M; m++) {
				total = 0;
				if (m % 2 == 0) {
					preMap = map;
					nowMap = map2;
				}else {
					preMap = map2;
					nowMap = map;
				}
				// 모든배열 순회
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						// 현재 회차의 미생물 군집이면
						if (preMap[i][j].time == m) {
							Group now = (Group) preMap[i][j].clone();
							// next는 현재 미생물이 한 칸 움직이는 경우
							Group next = (Group) now.clone();
							next.R = now.R + Y[now.dir];
							next.C = now.C + X[now.dir];
							// 움직여서 군집을 나간게 아니라면 계속
							if (next.R < 0 || next.R >= N || next.C < 0 || next.C >= N )
								continue;
							// 움직이기 전 위치는 초기화
							preMap[i][j] = new Group(-1, -1, -1, -1, -1);
							// 테두리에 왔는지 확인
							boolean bEdge = checkEdge(next.R, next.C, N-1);
							// 테두리에 왔으면 방향이랑 수 변경
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
							if (next.num == 0) continue;
							// 새로운 위치의 값 업데이트
							nowMap[next.R][next.C] = (Group) next.clone();
							// 새로운 위치에서 상하좌우에 합쳐질 군집이 있나 확인
							int [] dataCombine = checkCombine(next);
							// 합쳐지거나 아닐 때 군집의 수와 방향,격리시간 업데이트
							next.num = dataCombine[0];
							next.dir = dataCombine[1];
							next.time = next.time+1;
							// 새로운 위치의 값 업데이트
							nowMap[next.R][next.C] = (Group) next.clone();
							//배열안의 모든 수 더하기 - 매 회 진행하고 마지막 수가 답.
							total += next.num;
						}
					}
				}
			}

			bw.write("#"+t+" "+total+"\n");
		}
		br.close();
		bw.close();
	}

	private static int[] checkCombine(Group next) throws CloneNotSupportedException {
		// 옮겨진 위치인 next
		int max = next.num;
		int dir = next.dir;
		int sum = next.num;
		// 상하좌우를 체크할 next
		Group next2 = (Group) next.clone();
		for (int i = 1; i <= 4; i++) {
			// 옮겨온 위치에서 상하좌우 체크
			next2.R = next.R + Y[i];
			next2.C = next.C + X[i];
			// 해당 위치가 범위를 넘어가면 pass
			if (next2.R < 0 || next2.R >= N|| next2.C < 0 || next2.C >= N ) 
				continue;
			// 범위 안이면 해당 값을 가져옴
			 next2.num = preMap[next2.R][next2.C].num;
			 next2.dir = preMap[next2.R][next2.C].dir;
			 next2.time = preMap[next2.R][next2.C].time;
			 // 군집 수가 0이상이고, 현재 격리시간이어야 함.
			 if (next2.num > 0 && next.time == next2.time) {
				 
				if ((i == 1 && next2.dir == 2) // 위를 체크했는데 아래로 오는 경우
						||(i == 2 && next2.dir == 1) // 아래를 체크했는데 위로 오는 경우 
						||(i == 3 && next2.dir == 4) 
						||(i == 4 && next2.dir == 3)) {
					// 합쳐질 군집이면 초기화 시킴
					preMap[next2.R][next2.C] = new Group(-1, -1, -1, -1, -1);
					
					// 합쳐진 군집 계산
					sum +=next2.num;
					// 합쳐진 것이 max값인지 체크
					if (max < next2.num) {
						max = next2.num;
						dir = next2.dir;
					}
				}
			}
		}
		int [] data = {sum, dir};
		return data;
	}

	private static boolean checkEdge(int next_Y, int next_X, int N) {
		if (next_Y == 0 || next_X == 0 || next_Y == N || next_X == N) {
			return true;
		}
		return false;
	}

}
