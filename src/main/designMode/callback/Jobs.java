package main.designMode.callback;


public class Jobs implements Boss{

	private Worker worker;
	
	public Jobs(Worker worker) {
		super();
		this.worker = worker;
	}

	public void assignTask() {
		worker.deal(this);
	}

	public void knowTheAnswer(String str) {
		System.out.println(str);
	}

}
