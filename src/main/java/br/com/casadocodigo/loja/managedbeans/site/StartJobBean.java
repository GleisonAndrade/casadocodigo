package br.com.casadocodigo.loja.managedbeans.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;
import javax.enterprise.inject.Model;

@Model
public class StartJobBean {
	private Long jobExecutionId;
	private JobExecution job;
	private List<StepExecution> steps = new ArrayList<>();;
	
	public void executar() {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties conf = new Properties();

		Long jobExecutionId = jobOperator.start("import-job", conf);
		
		job = jobOperator.getJobExecution(jobExecutionId);
		steps = jobOperator.getStepExecutions(jobExecutionId);
		
		for (StepExecution step : steps) {
			System.out.println(step.getStepName()+": "+step.getBatchStatus());
		}
		
		System.out.println("*********************** ID: "+jobExecutionId);
		
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
	
	public boolean hasJob() {
		return job!=null;
	}
	
}
