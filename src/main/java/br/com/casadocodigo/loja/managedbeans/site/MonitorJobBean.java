package br.com.casadocodigo.loja.managedbeans.site;

import java.util.List;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;
import javax.enterprise.inject.Model;

@Model
public class MonitorJobBean {
	private Long jobExecutionId;
	private JobExecution job;
	private List<StepExecution> steps;
	
	public void loadJob() {
		JobOperator operator = BatchRuntime.getJobOperator();
		job = operator.getJobExecution(jobExecutionId);
		steps = operator.getStepExecutions(jobExecutionId);
		for (StepExecution step : steps) {
			System.out.println(step.getStepName()+": "+step.getBatchStatus());
		}
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public JobExecution getJob() {
		return job;
	}

	public List<StepExecution> getSteps() {
		return steps;
	}

	public void setSteps(List<StepExecution> steps) {
		this.steps = steps;
	}
	
	
}
