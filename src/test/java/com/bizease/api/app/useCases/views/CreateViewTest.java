package com.bizease.api.app.useCases.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bizease.api.app.infrastructure.persistence.jpa.tenant.TenantRepository;
import com.bizease.api.app.infrastructure.persistence.jpa.view.ViewRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.ActivationsState;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.services.views.CreateView;

@ExtendWith(MockitoExtension.class)
public class CreateViewTest {

  @InjectMocks
  private CreateView createView;

  @Mock
  private ViewRepository viewRepository;
  @Mock
  private TenantRepository tenantRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;

  @Test
  @DisplayName("Should be able to create a new view")
  public void shouldBeAbleToCreateNewView() {
    ViewRequest request = new ViewRequest("ViewName", "Description", "/route", "icon-name", AccessProfile.ADMIN, 1,
        ActivationsState.ACTIVE);

    when(viewRepository.save(any(View.class))).thenAnswer(invocation -> {
      View savedView = invocation.getArgument(0);
      savedView.setId("view-123");
      return savedView;
    });

    CreateResponse response = createView.execute(request);

    assertEquals("view-123", response.id());
  }

}
