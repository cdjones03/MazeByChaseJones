package generation;

public class StubOrder implements Order{

	MazeConfiguration mazeCon;
	
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return getSkillLevel();
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		mazeCon = mazeConfig;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}
	
	public MazeConfiguration getMazeConfiguration()
	{
		return mazeCon;
	}

}
