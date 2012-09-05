package com.netkiller.gadget;

import java.util.List;

/**
 * Represent a Single Gadget.
 * 
 * @author abhinav
 * 
 */
public class Gadget {

	// Name for internal api use
	private String name;

	// Name to show on UI
	private String displayName;

	// This can be shown on an info â€œiâ€? icon on the page.
	private String description;

	// absolute or relative URL
	private String url;
	// all configurable items about a gadget should be exposed by preferences,
	// instead hard coding them in the Gadget code. This will give us
	// opportunity to further extend them to user
	List<Preference> preferences;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Preference> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Preference> preferences) {
		this.preferences = preferences;
	}

	/**
	 * Represent a single preference associated with a Gadget.
	 * 
	 * @author abhinav
	 * 
	 */
	public static class Preference {
		// API name of preference
		private String name;
		// Display Name of preference, this will be used to show on UI. Not
		// supports i18n, but seems thats not a requirement as of now.
		private String displayName;
		// Preference value
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
}
