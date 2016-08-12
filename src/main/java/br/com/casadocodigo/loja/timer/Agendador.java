package org.casadocodigo.timer;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@Startup
public class Agendador {

	@Resource
	private TimerService timerService;
	
	@Timeout
	private void agendado(Timer timer){
		System.out.println("Executado:");
		System.out.println(timer.getInfo());
	}
	
	@PostConstruct
	public void setup(){
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour(13);
		expression.minute(32);
		
		TimerConfig config = new TimerConfig();
		config.setInfo(expression.toString());
		config.setPersistent(true);
		
		timerService.createCalendarTimer(expression,config);
	}
	
	/*
	@Schedule(minute="32", hour="13")
	public void meuAgendamento(){
		JobOperator op = BatchRuntime.getJobOperator();
		long id = op.start("nomedojob", new Properties());
	}
	*/
}
