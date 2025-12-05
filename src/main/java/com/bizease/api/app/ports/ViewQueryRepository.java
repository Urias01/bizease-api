package com.bizease.api.app.ports;

import com.bizease.api.app.models.commons.PagedResult;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.filters.ViewFilter;

public interface ViewQueryRepository {
  PagedResult<View> find(ViewFilter filter);
}
