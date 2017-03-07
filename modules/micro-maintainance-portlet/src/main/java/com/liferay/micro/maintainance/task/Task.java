package com.liferay.micro.maintainance.task;

import java.util.List;

import com.liferay.micro.maintainance.action.Action;
import com.liferay.micro.maintainance.task.model.CandidateMaintenance;

public interface Task {
	public List<Action> analyze(long analysisId);

	public long getTaskId();

	public String getTaskName();

	public void setTaskId(long taskId);

	public boolean isAnalyseReady(CandidateMaintenance canMain);
}
