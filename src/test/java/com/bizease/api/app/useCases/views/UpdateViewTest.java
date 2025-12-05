package com.bizease.api.app.useCases.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.view.ViewRepository;
import com.bizease.api.app.models.entities.View;
import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.ActivationsState;
import com.bizease.api.app.models.request.ViewRequest;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.services.views.UpdateView;

@ExtendWith(MockitoExtension.class)
public class UpdateViewTest {

  @InjectMocks
  private UpdateView updateView;

  @Mock
  private ViewRepository viewRepository;

  @Test
  @DisplayName("Should be able to create a new view")
  public void shouldBeAbleToCreateNewView() {
    View view = new View();
    view.setId("view-123");

    when(viewRepository.findById("view-123")).thenReturn(Optional.ofNullable(view));

    ViewRequest request = new ViewRequest("ViewName", "Description", "/route", "icon-name", AccessProfile.ADMIN, 1,
        ActivationsState.ACTIVE);

    when(viewRepository.save(any(View.class))).thenAnswer(invocation -> {
      View savedView = invocation.getArgument(0);
      savedView.setId("view-123");
      return savedView;
    });

    CreateResponse response = updateView.execute("view-123", request);

    assertEquals("view-123", response.id());
  }

  @Test
  @DisplayName("Should be able to throw NotFoundException when view does not exist")
  public void shouldBeAbleToThrowNotFoundExceptionWhenViewDoesNotExist() {
    View view = new View();
    view.setId("not-existing-123");

    when(viewRepository.findById("not-existing-123")).thenReturn(Optional.empty());

    ViewRequest request = new ViewRequest("ViewName", "Description", "/route", "icon-name", AccessProfile.ADMIN, 1,
        ActivationsState.ACTIVE);

    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> updateView.execute("not-existing-123", request));

    assertEquals("View not found.", exception.getMessage());
  }

}
