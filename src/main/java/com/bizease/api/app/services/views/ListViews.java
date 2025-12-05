package com.bizease.api.app.services.views;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bizease.api.app.models.commons.PagedResult;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.mappers.ViewMapper;
import com.bizease.api.app.models.filters.ViewFilter;
import com.bizease.api.app.models.response.ViewResponse;
import com.bizease.api.app.ports.ViewQueryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListViews {

        private ViewQueryRepository viewRepository;
        private IJwtAuthContext jwtAuthContext;

        public PagedResult<ViewResponse> execute(ViewFilter filter) {
                filter.setTenantId(jwtAuthContext.getTenantId());

                PagedResult<View> page = viewRepository.find(filter);

                List<ViewResponse> responses = page.getData().stream()
                                .map(ViewMapper::toResponse)
                                .collect(Collectors.toList());

                return new PagedResult<>(responses, page.getTotalCount(), page.getPageIndex(), page.getPageSize());
        }
}
