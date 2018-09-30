package generation;

public class StubOrder implements Order{

	private MazeConfiguration mazeCon;
	private Builder builder;
	private int skill;
	private boolean perf;
	private int progress;
	
	public StubOrder(Builder build, int level, boolean per)
	{
		this.builder = build;
		this.skill = level;
		this.perf = per;
	}
	
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skill;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perf;
	}

	@Override //Method delivers a mazeConfig (as parameter) to the stuborder object (i.e. set method)
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeCon = mazeConfig;
		// TODO Auto-generated method stub
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		this.progress = percentage;
	}
	
	public MazeConfiguration getMazeConfiguration()//Method to return maze configuration (i.e. get method)
	{
		return mazeCon;
	}

}
