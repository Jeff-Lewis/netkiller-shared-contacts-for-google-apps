package com.netkiller.gadget;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents state of dashboard with bunch of gadgets
 * 
 * @author abhinav
 * 
 */
public class GadgetDashboardModel {
	private List<Gadget> col1Gadgets = new ArrayList<Gadget>();
	private List<Gadget> col2Gadgets = new ArrayList<Gadget>();

	public GadgetDashboardModel(List<Gadget> gadgets) {
		Integer idx = 0;
		for (Gadget gadget : gadgets) {
			if (idx++ % 2 == 0)
				col1Gadgets.add(gadget);
			else
				col2Gadgets.add(gadget);
		}
	}

	public List<Gadget> getCol1Gadgets() {
		return col1Gadgets;
	}

	public List<Gadget> getCol2Gadgets() {
		return col2Gadgets;
	}

}
