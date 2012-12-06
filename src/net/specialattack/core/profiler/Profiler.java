package net.specialattack.core.profiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.specialattack.core.SpACore;

public class Profiler {
	public final static int OUTPUT_CONSOLE = 0;
	public final static int OUTPUT_FILE = 1;
	private static final String FILE_NAME = "SpACoreProfile";
	private static final File FOLDER_PATH = SpACore.instance.getDataFolder();

	private final boolean enabled = true;
	private final List<String> profilingSections = new ArrayList<String>();
	private final List<Long> timeStampList = new ArrayList<Long>();
	private String currentSection = "";
	private final Map<String, ProfileSection> profilingMap = new HashMap<String, ProfileSection>();

	public void startSection(String section) {
		if (this.enabled) {
			if (this.currentSection.length() > 0) {
				this.currentSection = this.currentSection + ".";
			}
			this.currentSection = this.currentSection + section;
			this.profilingSections.add(this.currentSection);
			this.timeStampList.add(System.nanoTime());
		}
	}

	public void startEndSection(String section) {
		endSection();
		startSection(section);
	}

	public void endSection() {
		if (this.enabled) {
			if (currentSection.length() == 0) throw new UnsupportedOperationException("Can't end a section that hasn't been started");
			long endTime = System.nanoTime();
			long startTime = this.timeStampList.remove(this.timeStampList.size() - 1);
			long totalTime = endTime - startTime;

			this.profilingSections.remove(this.profilingSections.size() - 1);
			String section = this.currentSection;

			if (this.profilingMap.containsKey(section)) {
				this.profilingMap.get(section).update(totalTime);
			} else {
				ProfileSection ps = new ProfileSection(section);
				ps.update(totalTime);
				this.profilingMap.put(section, ps);
			}

			if (totalTime > 100000000L) {
				SpACore.log(Level.WARNING, "Something\'s taking too long! \'" + section + "\' took aprox " + (double) totalTime / 1000000.0D + " ms");
			}

			currentSection = this.profilingSections.isEmpty() ? "" : this.profilingSections.get(this.profilingSections.size() - 1);

		}
	}

	public void generateReport() {
		generateReport(OUTPUT_FILE);
	}

	public void generateReport(int outputType) {
		System.out.println(profilingMap.size());
		if (outputType == OUTPUT_CONSOLE) {
			for (ProfileSection ps : profilingMap.values()) {
				System.out.println(ps.getProfileReport());
			}
		} else if (outputType == OUTPUT_FILE) {
			File file = new File(FOLDER_PATH, FILE_NAME + ".txt");
			File filePath = FOLDER_PATH;
			try {

				if (!filePath.exists() && !filePath.isDirectory()) {
					filePath.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
				}

				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for (ProfileSection ps : profilingMap.values()) {
					bw.write(ps.getProfileReport());
				}
				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
