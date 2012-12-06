package net.specialattack.core.profiler;

public class ProfileSection {

	public String name;
	public long time;
	public int iterations = 0;

	public ProfileSection(String name) {
		this.name = name;
	}

	public void update(long time) {
		this.time += time;
		iterations++;
	}

	public String getProfileReport() {
		return name + " total time " + (time / 1000000.0D) + " ms" + "\r\n    Iterations: " + iterations + "\r\n    " + "Average Time: " + (time/iterations)/ 1000000.0D + " ms" + "\r\n";
	}

}
