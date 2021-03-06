package com.takipi.api.client.data.transaction;

import java.util.List;

public class TransactionGraph {
	public String name;
	public String class_name;
	public String method_name;
	public String method_desc;

	public String machine_name;
	public String application_name;
	public String deployment_name;
	public List<GraphPoint> points;

	public static class GraphPoint {
		public String time;
		public Stats stats;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || (!(obj instanceof TransactionGraph))) {
			return false;
		}

		TransactionGraph other = (TransactionGraph) obj;

		return ((this.name != null) && (other.name != null) && (name.equals(other.name)));
	}

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : super.hashCode());
	}
}
