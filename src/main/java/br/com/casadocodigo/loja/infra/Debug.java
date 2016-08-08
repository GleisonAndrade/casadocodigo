package br.com.casadocodigo.loja.infra;

import java.text.Annotation;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class Debug implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		//new ObsEvent(phaseEvent.getPhaseId()).after().fire();
		
	}

	@Override
	public void beforePhase(PhaseEvent phaseEvent) {
		System.out.println("Antes de "+phaseEvent.getPhaseId());
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}

class ObsEvent {
	private BeanManager bm = CDI.current().getBeanManager();
	private Annotation n;
	private Annotation ph;
	
	/*public ObsEvent(PhaseId id) {
		if (PhaseId.APPLY_REQUEST_VALUES.equals(id)) 
			ph = new AnnotationLiteral<After>() {
				return this;
			};
	}
	public ObsEvent after() {
		
	}
	public void fire(PhaseEvent e) {
		//new FineE 
	}*/
}
