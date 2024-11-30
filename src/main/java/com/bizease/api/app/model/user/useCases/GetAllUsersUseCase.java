package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.filters.UserFilter;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.bizease.api.app.model.user.specification.UserSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

@Service
public class GetAllUsersUseCase {

    @Autowired
    private UserRepository userRepository;

    public PageReturn<List<UserResponseDTO>> execute(UserFilter filter) {

        Specification<User> specification = where(commerceUuidEquals(filter.getCommerceUuid())
                .and(idEquals(filter.getId()))
                .and(nameLike(filter.getName()))
                .and(emailLike(filter.getEmail()))
                .and(isActive(filter.getIsActive())));

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Page<User> model = this.userRepository.findAll(specification, pageRequest);

        List<UserResponseDTO> responses = UserResponseDTO.toList(model.getContent());

        return new PageReturn<List<UserResponseDTO>>(responses, model.getTotalElements(), pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }

}
