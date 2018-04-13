package main.designMode.callback;

public class TestDemo {
	public static void main(String[] args) {
		Worker worker = new WorkerMan();
		Boss bos = new Jobs(worker);
		bos.assignTask();
	}
}
