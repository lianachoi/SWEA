import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA2477 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static class Customer {
		int num, reception, repair, arrived, startReception, endReception, startRepair;
		Customer(int num, int arrived) {
			this.num = num;
			this.arrived = arrived;
		}
	}
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int reception_num = Integer.parseInt(st.nextToken());
			int repair_num = Integer.parseInt(st.nextToken());
			int customer_num = Integer.parseInt(st.nextToken());
			int find_reception = Integer.parseInt(st.nextToken());
			int find_repair = Integer.parseInt(st.nextToken());

			int [] receptions = new int[reception_num+1];
			Customer [] receptionStatus = new Customer[reception_num+1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i < receptions.length; i++) {
				receptions[i] = Integer.parseInt(st.nextToken());
			}
			int [] repairs = new int[repair_num+1];
			Customer [] repairStatus = new Customer[repair_num+1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i < repairs.length; i++) {
				repairs[i] = Integer.parseInt(st.nextToken());
			}
			
			PriorityQueue<Customer> receptionCustomers = new PriorityQueue<Customer>(new Comparator<Customer>() {
				@Override
				public int compare(Customer o1, Customer o2) {
					return o1.num - o2.num ;
				}
			});

			PriorityQueue<Customer> repairCustomers = new PriorityQueue<Customer>(new Comparator<Customer>() {
				@Override
				public int compare(Customer o1, Customer o2) {
					if (o1.endReception == o2.endReception) {
						return o1.reception - o2.reception ;
					}else {
						return o1.endReception - o2.endReception ;
					}
				}
			});
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= customer_num; i++) {
				receptionCustomers.add(new Customer(i, Integer.parseInt(st.nextToken())));
			}
			int finish = 0;
			int time = 0;
			int ans = 0;
			while (finish != customer_num) {
				for (int i = 1; i < receptionStatus.length; i++) {
					if (receptionStatus[i] != null) {
						if(receptionStatus[i].startReception+receptions[i] <= time) {
							receptionStatus[i].endReception = time;
							repairCustomers.offer(receptionStatus[i]);
							receptionStatus[i] = null;
						}
					}
				}
				for (int i = 1; i < receptionStatus.length; i++) {
					if (receptionStatus[i] == null) {
						if (!receptionCustomers.isEmpty()) {
							if (receptionCustomers.peek().arrived <= time) {
								receptionStatus[i] = receptionCustomers.poll();
								receptionStatus[i].reception = i;
								receptionStatus[i].startReception = time;
							}
						}
					}
				}
				for (int i = 1; i < repairStatus.length; i++) {
					if (repairStatus[i] != null) {
						if(repairStatus[i].startRepair+repairs[i] <= time) {
							if (repairStatus[i].reception == find_reception 
									&& repairStatus[i].repair == find_repair) {
								ans += repairStatus[i].num;
							}
							repairStatus[i] = null;
							finish++;
						}
					}
				}
				for (int i = 1; i < repairStatus.length; i++) {
					if (repairStatus[i] == null) {
						if (!repairCustomers.isEmpty()) {
							if (repairCustomers.peek().endReception <= time) {
								repairStatus[i] = repairCustomers.poll();
								repairStatus[i].repair = i;
								repairStatus[i].startRepair = time;
							}
						}
					}
				}
				time++;
			}
			if (ans == 0) ans =-1;
			bw.write("#"+t+" "+ans+"\n");
		}
		br.close();
		bw.close();
	}

}
