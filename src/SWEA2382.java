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
			// Ŭ���� �迭 �ʱ�ȭ
			for (int i = 0; i < N; i++) {
				Arrays.fill(map[i], new Group(-1,-1,-1,-1,-1));
				Arrays.fill(map2[i], new Group(-1,-1,-1,-1,-1));
			}
			int init = 0;
			// �̻��� �׷� ǥ��
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int R = Integer.parseInt(st.nextToken());
				int C = Integer.parseInt(st.nextToken());
				int num = Integer.parseInt(st.nextToken());
				// �����¿� 1234
				int dir = Integer.parseInt(st.nextToken());
				map[R][C] = new Group(R, C, num, dir, 0);
				init += num;
			}
			// �̻��� ��
			int total = 0;
			// �ݸ� �ð���ŭ �ݺ�
			for (int m = 0; m < M; m++) {
				total = 0;
				if (m % 2 == 0) {
					preMap = map;
					nowMap = map2;
				}else {
					preMap = map2;
					nowMap = map;
				}
				// ���迭 ��ȸ
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						// ���� ȸ���� �̻��� �����̸�
						if (preMap[i][j].time == m) {
							Group now = (Group) preMap[i][j].clone();
							// next�� ���� �̻����� �� ĭ �����̴� ���
							Group next = (Group) now.clone();
							next.R = now.R + Y[now.dir];
							next.C = now.C + X[now.dir];
							// �������� ������ ������ �ƴ϶�� ���
							if (next.R < 0 || next.R >= N || next.C < 0 || next.C >= N )
								continue;
							// �����̱� �� ��ġ�� �ʱ�ȭ
							preMap[i][j] = new Group(-1, -1, -1, -1, -1);
							// �׵θ��� �Դ��� Ȯ��
							boolean bEdge = checkEdge(next.R, next.C, N-1);
							// �׵θ��� ������ �����̶� �� ����
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
							// ���ο� ��ġ�� �� ������Ʈ
							nowMap[next.R][next.C] = (Group) next.clone();
							// ���ο� ��ġ���� �����¿쿡 ������ ������ �ֳ� Ȯ��
							int [] dataCombine = checkCombine(next);
							// �������ų� �ƴ� �� ������ ���� ����,�ݸ��ð� ������Ʈ
							next.num = dataCombine[0];
							next.dir = dataCombine[1];
							next.time = next.time+1;
							// ���ο� ��ġ�� �� ������Ʈ
							nowMap[next.R][next.C] = (Group) next.clone();
							//�迭���� ��� �� ���ϱ� - �� ȸ �����ϰ� ������ ���� ��.
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
		// �Ű��� ��ġ�� next
		int max = next.num;
		int dir = next.dir;
		int sum = next.num;
		// �����¿츦 üũ�� next
		Group next2 = (Group) next.clone();
		for (int i = 1; i <= 4; i++) {
			// �Űܿ� ��ġ���� �����¿� üũ
			next2.R = next.R + Y[i];
			next2.C = next.C + X[i];
			// �ش� ��ġ�� ������ �Ѿ�� pass
			if (next2.R < 0 || next2.R >= N|| next2.C < 0 || next2.C >= N ) 
				continue;
			// ���� ���̸� �ش� ���� ������
			 next2.num = preMap[next2.R][next2.C].num;
			 next2.dir = preMap[next2.R][next2.C].dir;
			 next2.time = preMap[next2.R][next2.C].time;
			 // ���� ���� 0�̻��̰�, ���� �ݸ��ð��̾�� ��.
			 if (next2.num > 0 && next.time == next2.time) {
				 
				if ((i == 1 && next2.dir == 2) // ���� üũ�ߴµ� �Ʒ��� ���� ���
						||(i == 2 && next2.dir == 1) // �Ʒ��� üũ�ߴµ� ���� ���� ��� 
						||(i == 3 && next2.dir == 4) 
						||(i == 4 && next2.dir == 3)) {
					// ������ �����̸� �ʱ�ȭ ��Ŵ
					preMap[next2.R][next2.C] = new Group(-1, -1, -1, -1, -1);
					
					// ������ ���� ���
					sum +=next2.num;
					// ������ ���� max������ üũ
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
