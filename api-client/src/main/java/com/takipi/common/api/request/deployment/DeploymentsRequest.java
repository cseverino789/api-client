package com.takipi.common.api.request.deployment;

import com.takipi.common.api.request.ProcessTagRequest;
import com.takipi.common.api.request.intf.ApiGetRequest;
import com.takipi.common.api.result.deployment.DeploymentsResult;

public class DeploymentsRequest extends ProcessTagRequest implements ApiGetRequest<DeploymentsResult> {
	DeploymentsRequest(String serviceId, boolean active) {
		super(serviceId, active);
	}

	@Override
	public Class<DeploymentsResult> resultClass() {
		return DeploymentsResult.class;
	}

	@Override
	public String urlPath() {
		return baseUrlPath() + "/deployments";
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder extends ProcessTagRequest.Builder {
		Builder() {

		}

		@Override
		public Builder setServiceId(String serviceId) {
			super.setServiceId(serviceId);

			return this;
		}

		@Override
		public Builder setActive(boolean active) {
			this.active = active;

			return this;
		}

		public DeploymentsRequest build() {
			validate();

			return new DeploymentsRequest(serviceId, active);
		}
	}
}
