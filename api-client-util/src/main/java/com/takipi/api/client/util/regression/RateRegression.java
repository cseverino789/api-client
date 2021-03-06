package com.takipi.api.client.util.regression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.takipi.api.client.result.event.EventResult;

public class RateRegression {
	private final Map<String, EventResult> allNewEvents;

	private final Map<String, RegressionResult> allRegressions;
	private final Map<String, RegressionResult> criticalRegressions;

	private final Map<String, EventResult> exceededNewEvents;
	private final Map<String, EventResult> criticalNewEvents;

	private final List<EventResult> nonRegressions;

	private final DateTime activeWndowStart;

	RateRegression(Map<String, EventResult> allNewEvents, Map<String, RegressionResult> allRegressions,
			Map<String, RegressionResult> criticalRegressions, Map<String, EventResult> exceededNewEvents,
			Map<String, EventResult> criticalNewEvents, DateTime activeWndowStart, List<EventResult> nonRegressions) {

		this.activeWndowStart = activeWndowStart;
		this.allRegressions = Collections.unmodifiableMap(allRegressions);
		this.criticalNewEvents = Collections.unmodifiableMap(criticalNewEvents);
		this.exceededNewEvents = Collections.unmodifiableMap(exceededNewEvents);
		this.allNewEvents = Collections.unmodifiableMap(allNewEvents);
		this.criticalRegressions = Collections.unmodifiableMap(criticalRegressions);
		this.nonRegressions = Collections.unmodifiableList(nonRegressions);
	}

	private static List<RegressionResult> getSortedRegressions(Collection<RegressionResult> regressions) {

		List<RegressionResult> result = new ArrayList<>(regressions);

		result.sort(new Comparator<RegressionResult>() {

			@Override
			public int compare(RegressionResult o1, RegressionResult o2) {
				return (int) (o2.getEvent().stats.hits - o1.getEvent().stats.hits);
			}
		});

		return result;

	}

	public static List<EventResult> getSortedNewEvents(Collection<EventResult> events) {

		List<EventResult> result = new ArrayList<>(events);

		result.sort(new Comparator<EventResult>() {

			@Override
			public int compare(EventResult o1, EventResult o2) {
				return (int) (o2.stats.hits - o1.stats.hits);
			}
		});

		return result;

	}

	public DateTime getActiveWndowStart() {
		return activeWndowStart;
	}

	public Collection<EventResult> getSortedNonRegressions() {
		return getSortedNewEvents(nonRegressions);
	}

	public Collection<EventResult> getSortedAllNewEvents() {
		return getSortedNewEvents(allNewEvents.values());
	}

	public Collection<RegressionResult> getSortedAllRegressions() {
		return getSortedRegressions(allRegressions.values());
	}

	public Collection<RegressionResult> getSortedCriticalRegressions() {
		return getSortedRegressions(criticalRegressions.values());
	}

	public Collection<EventResult> getSortedExceededNewEvents() {
		return getSortedNewEvents(exceededNewEvents.values());
	}

	public Collection<EventResult> getSortedCriticalNewEvents() {
		return getSortedNewEvents(criticalNewEvents.values());
	}

	public Collection<EventResult> getNonRegressions() {
		return nonRegressions;
	}

	public Map<String, EventResult> getAllNewEvents() {
		return allNewEvents;
	}

	public Map<String, RegressionResult> getAllRegressions() {
		return allRegressions;
	}

	public Map<String, RegressionResult> getCriticalRegressions() {
		return criticalRegressions;
	}

	public Map<String, EventResult> getExceededNewEvents() {
		return exceededNewEvents;
	}

	public Map<String, EventResult> getCriticalNewEvents() {
		return criticalNewEvents;
	}

	public static class Builder {
		private final Map<String, EventResult> allNewEvents;

		private final Map<String, RegressionResult> allRegressions;
		private final Map<String, RegressionResult> criticalRegressions;

		private final Map<String, EventResult> exceededNewEvents;
		private final Map<String, EventResult> criticalNewEvents;

		private final List<EventResult> nonRegressions;

		private DateTime activeWindowStart;

		Builder() {
			allRegressions = new HashMap<>();
			criticalNewEvents = new HashMap<>();
			exceededNewEvents = new HashMap<>();
			allNewEvents = new HashMap<>();
			criticalRegressions = new HashMap<>();
			nonRegressions = new ArrayList<>();
		}

		public void setActiveWindowStart(DateTime activeWindowStart) {
			this.activeWindowStart = activeWindowStart;
		}

		public void addNonRegressions(EventResult event) {
			nonRegressions.add(event);
		}

		public void addNewEvent(String id, EventResult event) {
			allNewEvents.put(id, event);
		}

		public void addExceededNewEvent(String id, EventResult event) {
			exceededNewEvents.put(id, event);
		}

		public void addCriticalNewEvent(String id, EventResult event) {
			criticalNewEvents.put(id, event);
		}

		public void addRegression(String id, EventResult event, long baselineHits, long baselineInvocations) {
			allRegressions.put(id, RegressionResult.of(event, baselineHits, baselineInvocations));
		}

		public void addCriticalRegression(String id, EventResult event, long baselineHits, long baselineInvocations) {
			criticalRegressions.put(id, RegressionResult.of(event, baselineHits, baselineInvocations));
		}

		public RateRegression build() {
			return new RateRegression(allNewEvents, allRegressions, criticalRegressions, exceededNewEvents,
					criticalNewEvents, activeWindowStart, nonRegressions);
		}
	}
}
