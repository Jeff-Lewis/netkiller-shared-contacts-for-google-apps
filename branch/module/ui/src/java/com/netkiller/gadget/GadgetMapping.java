package com.netkiller.gadget;

import java.util.List;

public class GadgetMapping {
	// it can be student, parent, teacher, etc
	private String role;
	// All gadgets available to this role.
	private List<Gadget> gadgets;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Gadget> getGadgets() {
		return gadgets;
	}

	public void setGadgets(List<Gadget> gadgets) {
		this.gadgets = gadgets;
	}

}
