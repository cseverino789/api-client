package com.takipi.api.client.util.alert;

import java.util.Collection;

import org.joda.time.DateTime;

import com.takipi.api.client.ApiClient;
import com.takipi.api.client.data.alert.Anomaly;
import com.takipi.api.client.request.alert.AnomalyAlertRequest;
import com.takipi.api.client.result.EmptyResult;
import com.takipi.api.client.result.event.EventResult;
import com.takipi.api.core.url.UrlClient.Response;

public class AlertUtil {

	// Send anomaly message to integrations
	//
	public static void reportAnomaly(ApiClient apiClient, String serviceId, String viewId,
			Collection<EventResult> events, DateTime from, DateTime to, String desc) {

		Anomaly anomaly = Anomaly.create();

		anomaly.addAnomalyPeriod(viewId, from.getMillis(), to.getMillis());

		for (EventResult event : events) {
			if ((event.stats != null) && (event.stats.hits > 0)) {
				anomaly.addContributor(Integer.parseInt(event.id), event.stats.hits);
			}
		}

		AnomalyAlertRequest anomalyAlertRequest = AnomalyAlertRequest.newBuilder().setServiceId(serviceId)
				.setViewId(viewId).setFrom(from.toString()).setTo(to.toString()).setDesc(desc).setAnomaly(anomaly)
				.build();

		Response<EmptyResult> anomalyAlertResponse = apiClient.post(anomalyAlertRequest);

		if (anomalyAlertResponse.isBadResponse()) {
			throw new IllegalStateException("Failed alerting on anomaly for view - " + viewId);
		}
	}
}
